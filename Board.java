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

	ArrayList <int[]> wChains = new ArrayList<int[]>();
	ArrayList <int[]> wSurroundChains = new ArrayList<int[]>();
	ArrayList <int[]> bChains = new ArrayList<int[]>();
	ArrayList <int[]> bSurroundChains = new ArrayList<int[]>();

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

	public void makeMove(String move, String colour) {
		int[] directory = new int[2];
		directory[0] = ALPHABET.indexOf(move.substring(0,1)); //this is column
		directory[1] = 19-Integer.parseInt(move.substring(1)); //this is row
		int turn = (colour=="B")? 0:1;
		GO_BOARD[directory[1]][directory[0]][turn]=1;
	}

	public double[] BoardToState() {
		double[] output = new double[722];
		for (int i = 0;i<19;i++) {
			for (int j = 0;j<19;j++) {
				output[38*i+2*j] = GO_BOARD[i][j][0];
				output[38*i+2*j+1] = GO_BOARD[i][j][1];
			}
		}
		return output;
	}

	//Set the rules of Go, Captures for single stones done.
	public void updateGoCaptures() {
		for (int i = 0;i<19;i++) {
			for (int j = 0;j<19;j++) {
				int[] array = {19*i+j};
				if(GO_BOARD[i][j][1]==1) {searchSurroundings(i,j,0); //this captures single stones
				wChains.add(array);
				wSurroundChains.add(surroundReq(i,j));
				}
				if(GO_BOARD[i][j][0]==1) {searchSurroundings(i,j,1); //this capture single stones
				bChains.add(array);
				bSurroundChains.add(surroundReq(i,j));
				}
			}
		}
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
			if(GO_BOARD[i-1][j][k]==1) stoneSurround++;
		}
		if (j==0){
			required--;
			//check left
		}
		else {
			if(GO_BOARD[i][j-1][k]==1) stoneSurround++;
		}
		if (i==18) {
			required--;
			//check bottom
		}
		else {
			if(GO_BOARD[i+1][j][k]==1) stoneSurround++;
		}
		if (j==18){
			required--;
			//check right
		}
		else {
			if(GO_BOARD[i][j+1][k]==1) stoneSurround++;
		}
		if (stoneSurround==required) GO_BOARD[i][j][1-k]=0;
	}

	/*
	 * Capture logic should be similar: If the number of the stones differ by -19,-1,1,19, then they are connected.
	 * Then check the coordinates, if all of them are surrounded, 
	 * 
	 * to check score, count # of black stones, then use search surroundings to find if empty spaces are completely surrounded by black stones.
	 */

	//Analyzing stones and checking if they are connected.
	public void findChains() {
		for (int i=0;i<wChains.size();i++) {
			int n = wChains.get(i)[0];
			for (int j = 0;j<wChains.size();j++) {
				int n2 = wChains.get(j)[0];
				if(Math.abs(n2-n)==19) {
					wChains.set(i, arf.appendArrays(wChains.get(i),wChains.get(j)));
					wChains.remove(j);
					wSurroundChains.set(i, arf.appendArrays(wSurroundChains.get(i),wSurroundChains.get(j)));
					wSurroundChains.remove(j);
					wSurroundChains.set(i, arf.fixSurroundReq(wChains.get(i),wSurroundChains.get(i)));
				} //check up and down
				if(n%19!=0) {
					if ((n-n2)==1) {
						wChains.set(i, arf.appendArrays(wChains.get(i),wChains.get(j)));
						wChains.remove(j);
						wSurroundChains.set(i, arf.appendArrays(wSurroundChains.get(i),wSurroundChains.get(j)));
						wSurroundChains.remove(j);
						wSurroundChains.set(i, arf.fixSurroundReq(wChains.get(i),wSurroundChains.get(i)));
					}
				}//can check left
				if(n%19!=18) {
					if ((n2-n)==1) {
						wChains.set(i, arf.appendArrays(wChains.get(i),wChains.get(j)));
						wChains.remove(j);
						wSurroundChains.set(i, arf.appendArrays(wSurroundChains.get(i),wSurroundChains.get(j)));
						wSurroundChains.remove(j);
						wSurroundChains.set(i, arf.fixSurroundReq(wChains.get(i),wSurroundChains.get(i)));
					}
				} //can check right
			}
		}
		for (int i=0;i<bChains.size();i++) {
			int n = bChains.get(i)[0];
			for (int j = 0;j<bChains.size();j++) {
				int n2 = bChains.get(j)[0];
				System.out.println(n2 + " vs. " + n);
				if(Math.abs(n2-n)==19) {
					bChains.set(i, arf.appendArrays(bChains.get(i),bChains.get(j)));
					bChains.remove(j);
					bSurroundChains.set(i, arf.appendArrays(bSurroundChains.get(i),bSurroundChains.get(j)));
					bSurroundChains.remove(j);
					bSurroundChains.set(i, arf.fixSurroundReq(bChains.get(i),bSurroundChains.get(i)));
				} //check up and down
				if(n%19!=0) {
					if ((n-n2)==1) {
						bChains.set(i, arf.appendArrays(bChains.get(i),bChains.get(j)));
						bChains.remove(j);
						bSurroundChains.set(i, arf.appendArrays(bSurroundChains.get(i),bSurroundChains.get(j)));
						bSurroundChains.remove(j);
						bSurroundChains.set(i, arf.fixSurroundReq(bChains.get(i),bSurroundChains.get(i)));
					}
				}//can check left
				if(n%19!=18) {
					if ((n2-n)==1) {
						bChains.set(i, arf.appendArrays(bChains.get(i),bChains.get(j)));
						bChains.remove(j);
						bSurroundChains.set(i, arf.appendArrays(bSurroundChains.get(i),bSurroundChains.get(j)));
						bSurroundChains.remove(j);
						bSurroundChains.set(i, arf.fixSurroundReq(bChains.get(i),bSurroundChains.get(i)));
					}
				} //can check right
			}
		}

	}


	public static void main(String[] args) {
		Board go = new Board();
		go.makeMove("A19","B");
		go.makeMove("B19","B");
		go.makeMove("C19","B");
		go.printBoard();
		go.updateGoCaptures();
		go.printBoard();
		go.findChains();
		for (int i = 0;i<go.bChains.size();i++) {
			System.out.println(Arrays.toString(go.bChains.get(i)));
			System.out.println(Arrays.toString(go.bSurroundChains.get(i)));
		}
	}
}
