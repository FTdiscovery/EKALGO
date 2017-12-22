package ekalGO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
	ArrayList<String[]> library = new ArrayList<>();
	ArrayList<double[]> ratingLib = new ArrayList<>();

	public Library(String[] a) {
		books = a;
	}

	public void createDataset() throws IOException {
		for (int i = 0;i<books.length;i++) {
			FileReader book = new FileReader (documents + books[i]+".txt");
			BufferedReader buffer = new BufferedReader(book);
			//Read the file...
			String word;
			ArrayList<String> game = new ArrayList<>();
			while ((word = buffer.readLine()) != null) {
				game.add(word);
			}
			//Game will be downloaded as "game" arraylist, for which the play out should happen immediately.
		}
	}
}
