package ekalGO;

public class Board {

	/*
	 * Representation of the board! This is a 3D array, where each square on the board holds two values.
	 * Black stone is [x][y][0], White stone is [x][y][1]
	 */
	
	double[][][] GO_BOARD;
	String[][] PRINTED;
	String ALPHABET = "ABCDEFGHJKLMNOPQRST";
			
	public Board() {
		GO_BOARD = new double[19][19][2];
		PRINTED = new String[19][19];
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
	
	public static void main(String[] args) {
		Board go = new Board();
		go.makeMove("A19","B");
		go.makeMove("A15","B");
		go.makeMove("B14","W");
		go.makeMove("K2","W");
		go.printBoard();
	}
}
