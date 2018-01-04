package ekalGO;

import java.util.ArrayList;
import java.util.Arrays;

public class MoveChoice {

	/*
	 * This sorts the move based on the ratings given by either just the value network,
	 * or by the Monte Carlo Tree Search. All is well...
	 */
	
	public static String[] bestMoves(double[] values,Board a) {
		//values usually is an array of 362 numbers.
		ArrayList<Double> validRatings = new ArrayList<>();
		ArrayList<String> validMoves = new ArrayList<>();
		
		for (int i = 0;i<361;i++) {
			//Check if the move is valid.
			if (a.GO_BOARD[i/19][i%19][0]==0 && a.GO_BOARD[i/19][i%19][1]==0) {
				if (i!=a.illegalKo) { //if not illegal ko.
					validRatings.add(values[i]);
					String[] alphabet = a.ALPHABET.split("");
					String row = Integer.toString(19-(i/19));
					String col = alphabet[i%19];

					validMoves.add(col+row);
				}
			}
		}
		//Add PASS
		validRatings.add(values[361]);
		validMoves.add("PASS");
		
		double[] ratings = new double[validRatings.size()];
		String[] moves = new String[validMoves.size()];
		
		for (int i = 0;i<validRatings.size();i++) {
			ratings[i]=validRatings.get(i);
			moves[i]=validMoves.get(i);
		}
		
		//Sort 
		MoveSort temp = new MoveSort();
		//System.out.println(Arrays.toString(ratings));
		//System.out.println(Arrays.toString(moves));
		temp.sort(ratings, moves);
		/*
		for (int i = 0;i<ratings.length;i++) {
			System.out.println(ratings[i] + ": "+ moves[i]);
		}
		*/
		return moves;
	}
	
	public static double[] randomValues() {
		double[] output = new double[362];
		for (int i = 0;i<362;i++) {
			output[i]=Math.random();
		}
		return output;
	}
	
	public static void main(String[] args) {
		Board a = new Board();
		a.makeMove("C19");
		//a.printBoard();
		double[] values = randomValues();
		System.out.println("BEST MOVE: " + bestMoves(values,a)[0]);
		
	}
}
