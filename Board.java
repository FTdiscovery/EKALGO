package ekalGO;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class Board {

	/*
	 * Representation of the board! This is a 3D array, where each square on the board holds two values.
	 * Black stone is [x][y][0], White stone is [x][y][1]
	 * 
	 * Actually, the neural network input SHOULD have history. The last two moves can dictate the best action at a certain time
	 * (i.e. a ko capture could theoretically be the best move, but due to the circumstances, is not exercised.)
	 */
	
	double[][][] GO_BOARD;
	
	//These will actually be important...hmm...update them after each move.
	double[][][][] PLIES_BEFORE_BOARD = new double[3][19][19][2];
	//This is a 4D array - the ply before is [0], 2 plies before is [1]...etc. Google uses 7, let's use 3.
	
	//Apart from illegal Ko, there must be a rule that prohibits a user from moving a stone where it will instantly die.
	
	String[][] PRINTED;
	String ALPHABET = "ABCDEFGHJKLMNOPQRST";
	int turns = 0;

	ArrayList <int[]> wStones = new ArrayList<int[]>();
	ArrayList <int[]> wSurStones = new ArrayList<int[]>();
	ArrayList <int[]> bStones = new ArrayList<int[]>();
	ArrayList <int[]> bSurStones = new ArrayList<int[]>();
	int[][] wChains;
	int[][] wSurChains;
	int[][] bChains;
	int[][] bSurChains;
	int illegalKo;
	int turnOfIllegalKo;

	public Board() {
		GO_BOARD = new double[19][19][2];
		PRINTED = new String[19][19];
	}

	public String output(double[] a) {
		if (a[0]==1) return "●";
		if (a[1]==1) return "o";
		return "+";
	}

	public String flipAction180(String move) {
		int directory = ALPHABET.indexOf(move.substring(0,1)); //this is column		
		return ALPHABET.split("")[18-directory]+(20-Integer.parseInt(move.substring(1)));
	}
	
	public String flipAction90CC(String move) {
		int column = ALPHABET.indexOf(move.substring(0,1))-10; //this is column
		int row = Integer.parseInt(move.substring(1))-10; // this is row
		int newCol=-row+10; 
		int newRow=column+10;
		return ALPHABET.split("")[newCol-1]+(newRow+1);	
	}
	
	public String flipAction90C(String move) {
		int column = ALPHABET.indexOf(move.substring(0,1))-10; //this is column
		int row = Integer.parseInt(move.substring(1))-10; // this is row
		int newCol=row+10; 
		int newRow=-column+10;
		return ALPHABET.split("")[newCol-1]+(newRow-1);	
	}
	
	public String mirrorY(String move) {
		String ALPHABET = "ABCDEFGHJKLMNOPQRST";
		int directory = ALPHABET.indexOf(move.substring(0,1)); //this is column
		return ALPHABET.split("")[18-directory]+move.substring(1);
	}
	
	public String mirrorX(String move) {
		String ALPHABET = "ABCDEFGHJKLMNOPQRST";
		int directory = ALPHABET.indexOf(move.substring(0,1)); //this is column
		return ALPHABET.split("")[directory]+(20-Integer.parseInt(move.substring(1)));
	}

	public void printBoard() {
		System.out.println("CURRENT STATE:\n\n   A B C D E F G H J K L M N O P Q R S T");
		for (int i = 0;i<19;i++){
			if (i>9) System.out.print(19-i+ "  ");
			else System.out.print(19-i+ " ");
			for (int j = 0;j<19;j++) {
				System.out.print(output(GO_BOARD[i][j])+" ");
			}
			System.out.println();
		}
	}

	public void customMove(String move, String colour) {
		int[] directory = new int[2];
		directory[0] = ALPHABET.indexOf(move.substring(0,1)); //this is column
		directory[1] = 19-Integer.parseInt(move.substring(1)); //this is row
		int turn = (colour=="B")? 0:1;
		GO_BOARD[directory[1]][directory[0]][turn]=1;
	}

	public void makeMove(String move) {
		int[] directory = new int[2];
		directory[0] = ALPHABET.indexOf(move.substring(0,1)); //this is column
		directory[1] = 19-Integer.parseInt(move.substring(1)); //this is row
		int whom = turns%2;
		if (GO_BOARD[directory[1]][directory[0]][whom]==1) System.out.println("ERROR");
		else if ((directory[1]*19+directory[0])==illegalKo && (turns-1)==turnOfIllegalKo) System.out.println("illegal ko error");
		
		else {
			//The move is legal, thus we can update the boards.
			//the board on [0] needs to go to [1], the board on [1] needs to go to [2], meanwhile the current board is [0].
			
			PLIES_BEFORE_BOARD[0]=GO_BOARD; //The last configuration of the board after the move will be the current state.
			for (int i = 1;i<PLIES_BEFORE_BOARD.length;i++) {
				PLIES_BEFORE_BOARD[i]=PLIES_BEFORE_BOARD[i-1];
			}
			//make the move, change the board.
			GO_BOARD[directory[1]][directory[0]][whom]=1;
			turns++;
			//if the turn has no capture, then remove the illegalKo.
		}
	}

	
	public double[] BoardToState() {
		double[] state = OneBoardToState(GO_BOARD);
		for (int i = 0;i<PLIES_BEFORE_BOARD.length;i++) {
			state = mxjava.connectArrays(state, OneBoardToState(PLIES_BEFORE_BOARD[i]));
		}
		return state;
	}
	//do OneBoardToState() to the current board, then all the boards of previous plies.
	
	public double[] OneBoardToState(double[][][] BOARD) {

		double[] output = new double[361*3+1];
		for (int i = 0;i<19;i++) {
			for (int j = 0;j<19;j++) {
				output[57*i+3*j] = BOARD[i][j][0];
				output[57*i+3*j+1] = BOARD[i][j][1];
				if (output[57*i+3*j]==0 && output[57*i+3*j+1]==0) {
					output[57*1+3*j+2]=1;
				}
			}
		}
		output[361*3]=turns%2;
		return mxjava.connectArrays(output,ConvolutionsNN.processed(BOARD));
	}

	//Set the rules of Go, Captures for single stones done.
	public void updateBoard() {
		turnOfIllegalKo=999;
		illegalKo=999; //reset after each move, so that a new illegal ko is found once the move is made.
		wStones.clear();
		wSurStones.clear();
		bStones.clear();
		bSurStones.clear();
		locateChains();
		if (turns%2==0) captureDeadStonesB();
		else captureDeadStonesW();
	}

	public static int[] surroundReq(int i, int j) {
		List<Integer> ba = new ArrayList<Integer>();
		if(i!=0) ba.add(((i-1)*19+j));
		if(j!=0) ba.add((i)*19+j-1);
		if(j!=18) ba.add((i)*19+j+1);
		if(i!=18) ba.add((i+1)*19+j);
		int[] newArray = new int[ba.size()];
		for (int k = 0;k<newArray.length;k++) newArray[k]=ba.get(k);
		return newArray;
	}


	public void searchSurroundings(int i, int j, int k) {
		//System.out.println("searching" + k);
		int required = 4;
		int stoneSurround = 0;
		if (i==0) {
			required--;
			//check top
		}
		else {
			if(GO_BOARD[i-1][j][k]==1 && GO_BOARD[i-1][j][1-k]==0) stoneSurround++;
		}
		if (j==0){
			required--;
			//check left
		}
		else {
			if(GO_BOARD[i][j-1][k]==1 && GO_BOARD[i][j-1][1-k]==0) stoneSurround++;
		}
		if (i==18) {
			required--;
			//check bottom
		}
		else {
			if(GO_BOARD[i+1][j][k]==1 && GO_BOARD[i+1][j][1-k]==0) { stoneSurround++; }
		}
		if (j==18){
			required--;
			//check right
		}
		else {
			if(GO_BOARD[i][j+1][k]==1 && GO_BOARD[i][j+1][1-k]==0) stoneSurround++;
		}
		if (stoneSurround==required) { illegalKo = (i*19)+j; turnOfIllegalKo=turns; GO_BOARD[i][j][1-k]=0; GO_BOARD[i][j][k]=0; System.out.println("Captured stone " + ((i*19)+j));}
	}

	/*
	 * Capture logic should be similar: If the number of the stones differ by -19,-1,1,19, then they are connected.
	 * Then check the coordinates, if all of them are surrounded, 
	 * 
	 * to check score, count # of black stones, then use search surroundings to find if empty spaces are completely surrounded by black stones.
	 */

	//Analyzing stones and checking if they are connected.
	public void locateChains() {
		//this finds all the individual stones, will be used to capture dead single stones.
		for (int i = 0;i<19;i++) {
			for (int j = 0;j<19;j++) {
				int[] array = {19*i+j};
				if(GO_BOARD[i][j][1]==1) {
					wStones.add(array);
					wSurStones.add(surroundReq(i,j));
				}
				if(GO_BOARD[i][j][0]==1) {
					bStones.add(array);
					bSurStones.add(surroundReq(i,j));
				}
			}
		}

		//Create temporary White Stone and Black Stone maps.
		int[][] WHITE_STONE_MAP=new int[19][19];
		int[][] BLACK_STONE_MAP=new int[19][19];
		for (int i=0;i<19;i++) {
			for (int j=0;j<19;j++) {
				BLACK_STONE_MAP[i][j] = (int) GO_BOARD[i][j][0];
				WHITE_STONE_MAP[i][j] = (int) GO_BOARD[i][j][1];	
			}
		}
		//Once these are created, scan for chains.

		wChains = findChains(WHITE_STONE_MAP);
		wSurChains = surArray(wChains);
		bChains = findChains(BLACK_STONE_MAP);
		bSurChains = surArray(bChains);
	}

	//courtesy of Aivant Goyal
	public static int[] followChain(int[][] BOARD, int i, int j, int[] c) {
		int[] chain = c;
		if (i>=BOARD.length || i<0 || j>=BOARD[0].length || j<0) return chain;
		if (BOARD[i][j]==0 || arf.inArray(i*19+j, chain)) return chain;
		int[] directory = {i*19+j};
		chain = arf.connectArrays(chain, directory);
		int[] chain1 = followChain(BOARD,i+1,j,chain);
		int[] chain2 = followChain(BOARD,i,j+1,chain1);
		int[] chain3 = followChain(BOARD,i-1,j,chain2);
		int[] chain4 = followChain(BOARD,i,j-1,chain3);
		return chain4;
	}

	public static int[][] findChains(int[][] BOARD) {
		ArrayList<int[]> tChains = new ArrayList<int[]>();
		int[] seen; //not used yet.
		for (int i = 0;i<19;i++) {
			for (int j = 0;j<19;j++) {
				int elem = BOARD[i][j];
				if (elem==1) {
					if ((j!=0 && BOARD[i][j-1]==1) || (i!=0 && BOARD[i-1][j]==1)) continue;
					int[] empty = {};
					int[] chain = followChain(BOARD,i,j,empty);
					QuickSort sorter = new QuickSort();
					sorter.sort(chain);
					if (chain.length>1 && !arf.chainAlreadySeen(tChains,chain)) tChains.add(chain);
				}
			}
		}
		int[][] chains = new int[tChains.size()][2];
		for (int i = 0;i<chains.length;i++) {
			chains[i]=tChains.get(i);
		}
		return chains;
	} 

	public static int[][] surArray(int[][] chains) {
		int[][] output = new int[chains.length][1];
		for (int i = 0;i<chains.length;i++) {
			output[i]=surroundings(chains[i]);
		}
		return output;
	}

	public static int[] surroundings(int[] chain) {
		int[] surroundings;
		surroundings = surroundReq(chain[0]/19,chain[0]%19);
		for (int i = 1;i<chain.length;i++) {
			surroundings = arf.inaccStones(chain, arf.removeDuplicates(arf.connectArrays(surroundings, surroundReq(chain[i]/19,chain[i]%19))));
		}
		return surroundings;
	}

	public boolean ifSurroundedByW(int[] require) {
		//checks if a chain is surrounded
		int covered = 0;
		for (int i = 0;i<require.length;i++) {
			if (GO_BOARD[require[i]/19][require[i]%19][1]==1)covered++;
		}
		return covered==require.length;
	}
	public boolean ifSurroundedByB(int[] require) {
		//checks if a chain is surrounded
		int covered = 0;
		for (int i = 0;i<require.length;i++) {
			if (GO_BOARD[require[i]/19][require[i]%19][0]==1)covered++;
		}
		return covered==require.length;
	}

	public void removeStones(int[] stones) {
		for (int i=0;i<stones.length;i++) {
			GO_BOARD[stones[i]/19][stones[i]%19][0]=0;
			GO_BOARD[stones[i]/19][stones[i]%19][1]=0;
		}
	}

	public void captureDeadStonesW() {
		for (int i = 0;i<wChains.length;i++) {
			if (ifSurroundedByB(wSurChains[i])) {removeStones(wChains[i]);
			System.out.println("Captured chain " + Arrays.toString(wChains[i]));
			}
		}
		//capture individuals
		for (int i = 0;i<wStones.size();i++) {
			searchSurroundings(wStones.get(i)[0]/19,wStones.get(i)[0]%19,0);
		}

	}
	public void captureDeadStonesB() {
		for (int i = 0;i<bChains.length;i++) {
			if (ifSurroundedByW(bSurChains[i])) {removeStones(bChains[i]); 
			System.out.println("Captured chain " + Arrays.toString(bChains[i]));
			}
		}
		//capture individuals
		for (int i = 0;i<bStones.size();i++) {
			searchSurroundings(bStones.get(i)[0]/19,bStones.get(i)[0]%19,1);

		}
	}

	public void printAllChains() {
		System.out.println("\n----\nFOR BLACK:\n---- ");
		for (int i = 0;i<bChains.length;i++) {
			System.out.println(Arrays.toString(bChains[i]));
			System.out.println("Surrounding Stones: " + Arrays.toString(bSurChains[i]));
		}
		System.out.println("\n-----\nFOR WHITE:\n---- ");
		for (int i = 0;i<wChains.length;i++) {
			System.out.println(Arrays.toString(wChains[i]));
			System.out.println("Surrounding Stones: " + Arrays.toString(wSurChains[i]));
		}
	}

	public static void main(String[] args) {
		Board go = new Board();
		go.customMove("A19","B");
		go.customMove("B19", "B");
		go.customMove("A18", "W");
		go.customMove("B18", "W");
		go.customMove("C19", "W");

		go.customMove("E18", "W");
		go.customMove("E19", "B");
		go.customMove("E17", "B");
		go.customMove("F18", "B");
		go.customMove("D18", "B");
		go.customMove("D17", "B");
		go.customMove("D16", "B");
		go.customMove("D15", "B");
		go.customMove("E15", "B");
		go.customMove("F15", "B");
		go.customMove("G15", "B");
		go.customMove("C15", "B");
		go.customMove("B15", "B");
		go.customMove("B14", "B");

		go.updateBoard();
		go.printBoard();
		go.makeMove("D3");
		
		go.printAllChains();
		go.updateBoard();
		go.printBoard();
		System.out.println(go.mirrorX("Q4"));
		System.out.println(go.mirrorY(go.mirrorY("Q4")));
		

	}
}