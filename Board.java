package ekalGO;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class Board {

	/*
	 * Representation of the board! This is a 3D array, where each square on the board holds two values.
	 * Black stone is [x][y][0], White stone is [x][y][1]
	 */

	double[][][] GO_BOARD;
	int[] CAPTURES;
	String[][] PRINTED;
	String ALPHABET = "ABCDEFGHJKLMNOPQRST";
	int turns = 0;

	ArrayList <int[]> wStones = new ArrayList<int[]>();
	ArrayList <int[]> wSurStones = new ArrayList<int[]>();
	ArrayList <int[]> bStones = new ArrayList<int[]>();
	ArrayList <int[]> bSurStones = new ArrayList<int[]>();

	//Connections for each stone (that are right or beneath it) are ordered.
	ArrayList<int[]> bChains = new ArrayList<int[]>();
	ArrayList<int[]> bSurChains = new ArrayList<int[]>();
	ArrayList<int[]> wChains = new ArrayList<int[]>();
	ArrayList<int[]> wSurChains = new ArrayList<int[]>();

	int[][] quickBChains;
	int[][] quickWChains;
	int[][] quickBSurChains;
	int[][] quickWSurChains;


	public Board() {
		GO_BOARD = new double[19][19][2];
		PRINTED = new String[19][19];
		CAPTURES = new int[2];
	}

	public String output(double[] a) {
		if (a[0]==1) return "●";
		if (a[1]==1) return "o";
		return "+";
	}

	public void captureScore() {
		System.out.println("Black Captures: " + CAPTURES[0] + "\nWhite Captures: " + CAPTURES[1]);
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
		GO_BOARD[directory[1]][directory[0]][whom]=1;
		turns++;
	}

	public double[] BoardToState() {
		double[] output = new double[723];
		for (int i = 0;i<19;i++) {
			for (int j = 0;j<19;j++) {
				output[38*i+2*j] = GO_BOARD[i][j][0];
				output[38*i+2*j+1] = GO_BOARD[i][j][1];
			}
		}
		output[722]=turns%2;
		return output;
	}

	//Set the rules of Go, Captures for single stones done.
	public void updateBoard() {
		findChains();
		if (turns%2==0) captureDeadStonesB();
		else captureDeadStonesW();
		wStones.clear();
		wSurStones.clear();
		bStones.clear();
		bSurStones.clear();
		bChains.clear();
		wChains.clear();
		bSurChains.clear();
		wSurChains.clear();
	}

	public int[] surroundReq(int i, int j) {
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
		if (stoneSurround==required) { GO_BOARD[i][j][1-k]=0; GO_BOARD[i][j][k]=0;}

		if (k==1 && stoneSurround==required && GO_BOARD[i][j][1-k]==0) { 
			CAPTURES[1]++; //System.out.println("Captured Black Stone: [" + (i*19+j)+"]"); 
		}
		if (k==0 && stoneSurround==required && GO_BOARD[i][j][1-k]==0) { 
			CAPTURES[0]++; //System.out.println("Captured White Stone: [" + (i*19+j)+"]"); 
		}
	}

	/*
	 * Capture logic should be similar: If the number of the stones differ by -19,-1,1,19, then they are connected.
	 * Then check the coordinates, if all of them are surrounded, 
	 * 
	 * to check score, count # of black stones, then use search surroundings to find if empty spaces are completely surrounded by black stones.
	 */

	//Analyzing stones and checking if they are connected.
	public void findChains() {
		wStones.clear();
		wSurStones.clear();
		bStones.clear();
		bSurStones.clear();
		bChains.clear();
		wChains.clear();
		bSurChains.clear();
		wSurChains.clear();
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
		//FOR WHITE
		boolean connectionsW = false;
		int[][] wStoneConnects = new int[wStones.size()][];
		for (int i = 0;i<wStones.size();i++) {
			wStoneConnects[i]=wStones.get(i);
		}
		int[][] wStoneSurrounds = new int[wStones.size()][];
		for (int i = 0;i<wStones.size();i++) {
			wStoneSurrounds[i]=wSurStones.get(i);
		}
		for (int i=0;i<wStones.size();i++) {
			for (int b = 0;b<wStones.get(i).length;b++) {
				int n = wStones.get(i)[b];
				for (int j = i;j<wStones.size();j++) {
					if (i!=j) {
						int[] len = wStones.get(j);
						for (int k = 0;k<len.length;k++) {
							int n2 = len[k];
							if(Math.abs(n2-n)==19) {
								//System.out.println(n2 + " is connected to " + n);
								wStoneConnects[i]=arf.appendArrays(wStoneConnects[i], wStones.get(j));
								wStoneSurrounds[i]=arf.appendArrays(wStoneSurrounds[i], wSurStones.get(j));
								wStoneSurrounds[i] = arf.inaccStones(wStoneConnects[i], arf.removeDuplicates(wStoneSurrounds[i]));
								connectionsW=true;
							} //check up and down
							if(n%19!=0) {
								if ((n-n2)==1) {
									//System.out.println(n2 + " is connected to " + n);
									wStoneConnects[i]=arf.appendArrays(wStoneConnects[i], wStones.get(j));
									wStoneSurrounds[i]=arf.appendArrays(wStoneSurrounds[i], wSurStones.get(j));
									wStoneSurrounds[i] = arf.inaccStones(wStoneConnects[i], arf.removeDuplicates(wStoneSurrounds[i]));
									connectionsW=true;
								}
							}//can check left
							if(n%19!=18) {
								if ((n2-n)==1) {
									//System.out.println(n2 + " is connected to " + n);
									wStoneConnects[i]=arf.appendArrays(wStoneConnects[i], wStones.get(j));
									wStoneSurrounds[i]=arf.appendArrays(wStoneSurrounds[i], wSurStones.get(j));
									wStoneSurrounds[i] = arf.inaccStones(wStoneConnects[i], arf.removeDuplicates(wStoneSurrounds[i]));
									connectionsW=true;
								}
							} //can check right
						}	
					}
				}
			}
		}
		while (wStones.size()>wStoneConnects.length) wStones.remove(wStones.size()-1);
		for (int i = 0;i<wStones.size()-1;i++) {
			for (int j = 0;j<wStoneConnects[i].length;j++) {
				while (wStones.size()>wStoneConnects.length) wStones.remove(wStones.size()-1);
				if (arf.inArray(wStoneConnects[i][j],wStoneConnects[Math.min(i+1, wStoneConnects.length-1)])) {
					wChains.add(arf.removeDuplicates(arf.appendArrays(wStoneConnects[i], wStoneConnects[Math.min(i+1, wStoneConnects.length-1)])));
					wSurChains.add(arf.removeDuplicates(arf.appendArrays(wStoneSurrounds[i], wStoneSurrounds[Math.min(i+1, wStoneConnects.length-1)])));
				}
				else if (wStoneConnects[i].length==1){
					wChains.add(wStoneConnects[i]);
					wSurChains.add(wStoneSurrounds[i]);
				}
			}
		}

		//forward check
		for (int b = 1;b<wChains.size()-2;b++) { //b is arbitrary value.
			for (int i = 0;i<wChains.size()-b;i++) {
				for (int j = 0;j<wChains.get(i).length;j++) {

					if (wChains.size()>1 && wChains.size()>i+b){
						//System.out.println("DIRECTORY: " + i + " SEARCHING " + (i+b) + " OUT OF " + wChains.size());
						//System.out.println(Arrays.toString(wChains.get(i)) + " in " + Arrays.toString(wChains.get(i+b))+ "?");
						if (arf.inArray(wChains.get(i)[j],wChains.get(i+b))) {
							//System.out.print(" Yes.\n");
							wChains.set(i,arf.removeDuplicates(arf.appendArrays(wChains.get(i), wChains.get(i+b))));
							wSurChains.set(i,arf.removeDuplicates(arf.appendArrays(wSurChains.get(i), wSurChains.get(i+b))));
							wChains.remove(i+b);
							wSurChains.remove(i+b);
						}
					}

				}
			}
		}

		//backward check
		for (int b = 1;b<wChains.size()-2;b++) { //b is arbitrary value.
			for (int i = wChains.size()-1;i>=0;i--) {
				for (int j = 0;j<wChains.get(i).length;j++) {

					if (wChains.size()>1 && i-b>=0){
						//System.out.println("DIRECTORY: " + i + " SEARCHING " + (i-b) + " OUT OF " + wChains.size());
						//System.out.println(Arrays.toString(wChains.get(i)) + " in " + Arrays.toString(wChains.get(i-b))+ "?");
						if (arf.inArray(wChains.get(i)[j],wChains.get(i-b))) {
							//System.out.print(" Yes.\n");
							wChains.set(i,arf.removeDuplicates(arf.appendArrays(wChains.get(i), wChains.get(i-b))));
							wSurChains.set(i,arf.removeDuplicates(arf.appendArrays(wSurChains.get(i), wSurChains.get(i-b))));
							wChains.remove(i-b);
							wSurChains.remove(i-b);
						}
					}
				}
			}
		}


		//FOR BLACK
		boolean connectionsB = false;
		int[][] bStoneConnects = new int[bStones.size()][];
		for (int i = 0;i<bStones.size();i++) {
			bStoneConnects[i]=bStones.get(i);
		}
		int[][] bStoneSurrounds = new int[bStones.size()][];
		for (int i = 0;i<bStones.size();i++) {
			bStoneSurrounds[i]=bSurStones.get(i);
		}
		for (int i=0;i<bStones.size();i++) {
			for (int b = 0;b<bStones.get(i).length;b++) {
				int n = bStones.get(i)[b];
				for (int j = i;j<bStones.size();j++) {
					if (i!=j) {
						int[] len = bStones.get(j);
						for (int k = 0;k<len.length;k++) {
							int n2 = len[k];
							if(Math.abs(n2-n)==19) {
								//System.out.println(n2 + " is connected to " + n);
								bStoneConnects[i]=arf.appendArrays(bStoneConnects[i], bStones.get(j));
								bStoneSurrounds[i]=arf.appendArrays(bStoneSurrounds[i], bSurStones.get(j));
								bStoneSurrounds[i] = arf.inaccStones(bStoneConnects[i], arf.removeDuplicates(bStoneSurrounds[i]));
								connectionsB=true;
							} //check up and down
							if(n%19!=0) {
								if ((n-n2)==1) {
									//System.out.println(n2 + " is connected to " + n);
									bStoneConnects[i]=arf.appendArrays(bStoneConnects[i], bStones.get(j));
									bStoneSurrounds[i]=arf.appendArrays(bStoneSurrounds[i], bSurStones.get(j));
									bStoneSurrounds[i] = arf.inaccStones(bStoneConnects[i], arf.removeDuplicates(bStoneSurrounds[i]));
									connectionsB=true;
								}
							}//can check left
							if(n%19!=18) {
								if ((n2-n)==1) {
									//System.out.println(n2 + " is connected to " + n);
									bStoneConnects[i]=arf.appendArrays(bStoneConnects[i], bStones.get(j));
									bStoneSurrounds[i]=arf.appendArrays(bStoneSurrounds[i], bSurStones.get(j));
									bStoneSurrounds[i] = arf.inaccStones(bStoneConnects[i], arf.removeDuplicates(bStoneSurrounds[i]));
									connectionsB=true;
								}
							} //can check right
						}	
					}
				}
			}
		}
		quickBChains = bStoneConnects;
		quickBSurChains = bStoneSurrounds;
		quickWChains = wStoneConnects;
		quickWSurChains = wStoneSurrounds;

		while (bStones.size()>bStoneConnects.length) bStones.remove(bStones.size()-1);
		for (int i = 0;i<bStones.size()-1;i++) {
			for (int j = 0;j<bStoneConnects[i].length;j++) {
				while (bStones.size()>bStoneConnects.length) bStones.remove(bStones.size()-1);
				if (arf.inArray(bStoneConnects[i][j],bStoneConnects[i+1])) {
					bChains.add((arf.appendArrays(bStoneConnects[i], bStoneConnects[i+1])));
					bSurChains.add((arf.appendArrays(bStoneSurrounds[i], bStoneSurrounds[i+1])));
				}
				else if (bStoneConnects[i].length==1){
					bChains.add(bStoneConnects[i]);
					bSurChains.add(bStoneSurrounds[i]);
				}
			}
		}
		//while (bChains.size()>bStones.size()) bChains.remove(bChains.size()-1);

		//forward check
		for (int b = 1;b<bChains.size()-2;b++) { //b is arbitrary value.
			for (int i = 0;i<bChains.size()-b;i++) {
				for (int j = 0;j<bChains.get(i).length;j++) {

					if (bChains.size()>1 && bChains.size()>i+b){
						//System.out.println("DIRECTORY: " + i + " SEARCHING " + (i+b) + " OUT OF " + bChains.size());
						//System.out.println(Arrays.toString(bChains.get(i)) + " in " + Arrays.toString(bChains.get(i+b))+ "?");
						if (arf.inArray(bChains.get(i)[j],bChains.get(i+b))) {
							//System.out.print(" Yes.\n");
							bChains.set(i,arf.removeDuplicates(arf.appendArrays(bChains.get(i), bChains.get(i+b))));
							bSurChains.set(i,arf.removeDuplicates(arf.appendArrays(bSurChains.get(i), bSurChains.get(i+b))));
							bChains.remove(i+b);
							bSurChains.remove(i+b);
						}
					}

				}
			}
		}

		//backward check
		for (int b = 1;b<bChains.size()-2;b++) { //b is arbitrary value.
			for (int i = bChains.size()-1;i>=0;i--) {
				for (int j = 0;j<bChains.get(i).length;j++) {
					if (bChains.size()>1 && i-b>=0){
						//System.out.println("DIRECTORY: " + i + " SEARCHING " + (i-b) + " OUT OF " + bChains.size());
						//System.out.println(Arrays.toString(bChains.get(i)) + " in " + Arrays.toString(bChains.get(i-b))+ "?");
						if (arf.inArray(bChains.get(i)[j],bChains.get(i-b))) {
							//System.out.print(" Yes.\n");
							bChains.set(i,arf.removeDuplicates(arf.appendArrays(bChains.get(i), bChains.get(i-b))));
							bSurChains.set(i,arf.removeDuplicates(arf.appendArrays(bSurChains.get(i), bSurChains.get(i-b))));
							bChains.remove(i-b);
							bSurChains.remove(i-b);
						}
					}
				}
			}
		}

		//get rid of wrong stones
		for (int i = 0;i<wChains.size();i++) {
			wSurChains.set(i, arf.inaccStones(wChains.get(i), wSurChains.get(i)));
		}
		for (int i = 0;i<bChains.size();i++) {
			bSurChains.set(i, arf.inaccStones(bChains.get(i), bSurChains.get(i)));
		}

	}

	public void printStoneConnections() {
		findChains();
		System.out.println("-----\nFOR WHITE:\n-----");
		for (int i = 0;i<wChains.size();i++) {
			System.out.println("STONES:  "+Arrays.toString(wChains.get(i)));
			System.out.println("SURROUNDINGS:  "+Arrays.toString(wSurChains.get(i)));
		}

		System.out.println("-----\nFOR BLACK:\n-----");
		for (int i = 0;i<bChains.size();i++) {
			System.out.println("STONES:  "+Arrays.toString(bChains.get(i)));
			System.out.println("SURROUNDINGS:  "+Arrays.toString(bSurChains.get(i)));
		}
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
		for (int i = 0; i<wChains.size();i++) {
			if (ifSurroundedByB(wSurChains.get(i))) { //System.out.println("Captured White Chain: " + Arrays.toString(wChains.get(i)));  
			CAPTURES[0]+=wChains.get(i).length;
			removeStones(wChains.get(i)); }
		}
		for (int i = 0;i<wStones.size();i++) {
			searchSurroundings(wStones.get(i)[0]/19,wStones.get(i)[0]%19,0);
		}
		for (int i = 0; i<quickWChains.length;i++) {
			if (ifSurroundedByB(quickWSurChains[i])) { //System.out.println("Captured White Chain: " + Arrays.toString(quickWChains[i])); 
			CAPTURES[1]+=quickWChains[i].length;
			removeStones(quickWChains[i]);} 
		}
	}
	public void captureDeadStonesB() {

		//capture dead chains
		for (int i = 0; i<bChains.size();i++) {
			if (ifSurroundedByW(bSurChains.get(i))) { //System.out.println("Captured Black Chain: " + Arrays.toString(bChains.get(i))); 
			CAPTURES[1]+=bChains.get(i).length;
			removeStones(bChains.get(i));} 
		}

		//capture dead,smaller chains
		for (int i = 0; i<quickBChains.length;i++) {
			if (ifSurroundedByW(quickBSurChains[i])) { //System.out.println("Captured Black Chain: " + Arrays.toString(quickBChains[i])); 
			CAPTURES[1]+=quickBChains[i].length;
			removeStones(quickBChains[i]);} 
		}
		//capture individuals
		for (int i = 0;i<bStones.size();i++) {
			searchSurroundings(bStones.get(i)[0]/19,bStones.get(i)[0]%19,1);
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
		go.printStoneConnections();
		go.printBoard();

	}
}