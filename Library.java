package ekalGO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * This is a library of games that ekalGO will be able to access. This will also be the games that are given to the computer.
 * The files will be read via BufferedReader. A document folder will have to be created...
 * 
 * Admittedly, this class is rather meta (it's a library for the library). 
 * 
 * Nonetheless, it will play the games and connect each state with the next action.
 */

public class Library {

	//Go Books!!!
	String[] books;
	String documents = System.getProperty ("user.home") + "/Documents/Go Books/";
	ArrayList<double[]> tStates = new ArrayList<>();
	ArrayList<double[]> tActions = new ArrayList<>();
	
	double[][] states;
	double[][] actions;

	public Library(String[] a) {
		books = a;
	}

	public void createDataset(boolean watchGame) throws IOException {
		for (int i = 0;i<books.length;i++) {
			FileReader book = new FileReader (documents + books[i]+".txt");
			BufferedReader buffer = new BufferedReader(book);
			//Read the file...
			String word;
			ArrayList<String> moves = new ArrayList<>();
			while ((word = buffer.readLine()) != null) {
				moves.add(word);
			}

			//Game will be downloaded as "moves" arraylist, for which the play out should happen immediately.
			Board temp = new Board();
			for (int k = 0;k<20;k++) {
				tStates.add(temp.BoardToState());
				tActions.add(arf.expertAction(moves.get(k)));
				temp.makeMove(moves.get(k));
				temp.updateBoard();
				if (watchGame) {
					System.out.println((k%2==0)?"BLACK"+ " MOVE: " + moves.get(k):"WHITE" + " MOVE: " + moves.get(k));
					temp.printBoard();
				}
			}
			temp.updateBoard();
			if (watchGame) {
				System.out.println("FINAL: " );
				temp.printBoard();
			}
			System.out.println("Finished importing \"" + books[i] + "\".");
		}
		//Convert ArrayList into Array.
		states = new double[tStates.size()][723];
		actions = new double[tActions.size()][361];
		for (int i = 0;i<states.length;i++) {
			states[i]=tStates.get(i);
			actions[i]=tActions.get(i);
		}
		
		
		//Clear ArrayList.
		tStates.clear();
		tActions.clear();
	}
}
