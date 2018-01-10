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
 * 
 * The value is scaled from 0 to 1. 1 is probability for a black win, 0 suggests a white win.
 */

public class Library {

	//Go Books!!!
	String[] books;
	boolean[] flipAction180;
	boolean[] flipAction90CC;
	boolean[] flipAction90C;
	boolean[] mirrorX;
	boolean[] mirrorY;
	boolean[] blackWin;

	String documents = System.getProperty ("user.home") + "/Documents/Go Books/";
	ArrayList<double[]> tStates = new ArrayList<>();
	ArrayList<double[]> tActions = new ArrayList<>();
	ArrayList<Integer> duplicateCount = new ArrayList<>();
	

	double[][] states;
	double[][] actions;

	public Library(String[] a, boolean[] b,boolean[] c, boolean[] d,boolean[] e, boolean[] f, boolean[] g) {
		books = a;
		flipAction180 = b;
		flipAction90CC = c;
		flipAction90C = d;
		mirrorX = e;
		mirrorY = f;
		blackWin = g;
	}

	//Eventually, changes with the arf action has to be done such that it is able to reward the winner of games.
	public void createDataset(boolean watchGame) throws IOException {
		for (int i = 0;i<books.length;i++) {
			FileReader book = new FileReader (documents + books[i]+".txt");
			BufferedReader buffer = new BufferedReader(book);
			//Read the file...
			String word;
			ArrayList<String> moves = new ArrayList<>();
			while ((word = buffer.readLine()) != null) {
				if (flipAction180[i]) {
					moves.add(mxjava.flipAction180(word));
				}
				else if (flipAction90CC[i]) {
					moves.add(mxjava.flipAction90CC(word));
				}
				else if (flipAction90C[i]) {
					moves.add(mxjava.flipAction90C(word));
				}
				else if (mirrorX[i]) {
					moves.add(mxjava.mirrorX(word));
				}
				else if (mirrorY[i]) {
					moves.add(mxjava.mirrorY(word));
				}
				else {
					moves.add(word);
				}

			}

			//Game will be downloaded as "moves" arraylist, for which the play out should happen immediately.
			Board temp = new Board();
			for (int k = 0;k<130;k++) {
				int stateSeen = arf.stateAlreadySeen(tStates,temp.BoardToState());
				double value = (blackWin[i])? (0.5+(((double)(k+1)/moves.size())*0.4)):(0.5-(((double)(k+1)/moves.size())*0.4));
				if (stateSeen<0) {
					if (k%2==0) { //Even denotes black's turn, Odd denotes white's turn
						tStates.add(temp.BoardToState());
						tActions.add(arf.expertAction(moves.get(k),value));
						duplicateCount.add(0);
					}
					if (k%2==1) { //Even denotes black's turn, Odd denotes white's turn
						tStates.add(temp.BoardToState());
						tActions.add(arf.expertAction(moves.get(k),value));
						duplicateCount.add(0);
					}
				}
				else {
					if (k%2==0) {
						System.out.println(stateSeen);
						System.out.println("DUPLICATE");
						duplicateCount.set(stateSeen, duplicateCount.get(stateSeen)+1);
						tActions.set(stateSeen, arf.compoundActions(duplicateCount.get(stateSeen),tActions.get(stateSeen),arf.expertAction(moves.get(k),value)));
					}
					if (k%2==1) {
						System.out.println(stateSeen);
						System.out.println("DUPLICATE");
						duplicateCount.set(stateSeen, duplicateCount.get(stateSeen)+1);
						tActions.set(stateSeen, arf.compoundActions(duplicateCount.get(stateSeen),tActions.get(stateSeen),arf.expertAction(moves.get(k),value)));
					}
				}

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

	}
}
