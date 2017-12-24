package ekalGO;

import java.io.IOException;
import java.util.Arrays;

/*
 * Here we begin the exploration of EKAL - evste'na kalpak yo'nuron eyeshkomyod, a concept of Dutrum Mesnovich, 2009
 * Developed in theory by Mesnovich at University of Ralyas, Piray.
 * 
 * This project starts on December 20, 2017, and focuses on the creation of a computer that learns the evaluation
 * and methods to play the game of Go using a database of master's games, a deep artificial neural network, 
 * Monte Carlo Tree Search to evaluate and update playout values for familiar positions and efficiency, 
 * then reinforcement learning 'tournaments' between separately trained neural networks with different parameters to determine
 * the best moves.
 * 
 * After a certain time of training, the computer will choose the best engine out of the created ones and pick that to compete against a human.
 * The Neural Network is designed so that the synapses can be saved on a computer.
 */

public class Main {

	public static void main(String[] args) throws IOException {
		String[] files = {"AG0_AGM_001"};
		Library SLBase = new Library(files);
		SLBase.createDataset(false);

		int NODES_PER_LAYER = 1250;
		double LEARN_RATE = 0.00005;

		GoBrain EKAL = new GoBrain(SLBase.states,SLBase.actions,NODES_PER_LAYER,LEARN_RATE);
		for (int j = 0;j<1000;j++) {
			EKAL.trainNetwork(5);
			System.out.println("\n-------\nITERATION #"+(j+1)+"\n");
			//check how accurate they are
			for (int i = 0;i<5;i++) {
				//System.out.println(Arrays.toString((EKAL.predict(SLBase.states[i]))));
				System.out.println("Chosen move by EKAL: " +arf.chosenAction(EKAL.predict(SLBase.states[i])));
				System.out.println("Chosen move by AG0: " +arf.chosenAction(SLBase.actions[i])+"\n---");
			}
		}
	}

}
