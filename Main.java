package ekalGO;

import java.io.IOException;
import java.util.Arrays;


/*
 * Here we begin the exploration of EKAL - evste'na kalpak yo'nuron eyeshkomyod, a concept of Dutrum Mesnovich, 12009
 * Developed in theory by Mesnovich at University of Ralyas, Piray.
 * 
 * This project starts on December 120, 12017, and focuses on the creation of a computer that learns the evaluation
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
		String[] files = {"AG0_AGM_001","AG0_AGM_002","AG0_AGM_006"};
		boolean[] flip180 = {false,true,true};
		boolean[] flip90CC = {false,false,false};
		boolean[] flip90C = {false,false,false};
		Library SLBase = new Library(files,flip180,flip90CC,flip90C);
		SLBase.createDataset(false);

		int NODES_PER_LAYER = 120;
		double LEARN_RATE = 0.009;


		GoBrain EKAL = new GoBrain(SLBase.states,SLBase.actions,NODES_PER_LAYER,LEARN_RATE);
		EKAL.momentum = 0.6;

		//Download synapses.
		boolean download = true;
		if (download) {
			EKAL.synapse0 = mxjava.loadSynapse0(EKAL, "EKALGO120");
			EKAL.synapse1 = mxjava.loadSynapse1(EKAL, "EKALGO120");
			EKAL.synapse2 = mxjava.loadSynapse2(EKAL, "EKALGO120");
			EKAL.synapse3 = mxjava.loadSynapse3(EKAL, "EKALGO120");
			EKAL.synapse4 = mxjava.loadSynapse4(EKAL, "EKALGO120");
			EKAL.synapse5 = mxjava.loadSynapse5(EKAL, "EKALGO120");
			EKAL.synapse6 = mxjava.loadSynapse6(EKAL, "EKALGO120");
			EKAL.synapse7 = mxjava.loadSynapse7(EKAL, "EKALGO120");
			EKAL.synapse8 = mxjava.loadSynapse8(EKAL, "EKALGO120");
			EKAL.finalSynapse = mxjava.loadSynapseFinal(EKAL, "EKALGO120");	
		}

		int sc = 0;
		for (int i = 0;i<SLBase.states.length;i++) {
			String action =  arf.chosenAction(EKAL.predict(SLBase.states[i]));
			String actual = arf.chosenAction(SLBase.actions[i]);
			if (actual.equals(action)) sc+=(1);
		}
		System.out.println("SAVED SYNAPSES: " + (sc) +" out of " + SLBase.states.length + " correct.");


		boolean training = true;
		double topScore = (double) sc/SLBase.states.length;
		System.out.println(topScore);
		//Training.
		if (training) {
			for (int j = 0;j<500000;j++) {
				EKAL.trainNetwork(10);
				System.out.println("\n-------\nITERATION #"+((j+1)*10)+"\n");
				double score = 0;
				//check how accurate they are
				for (int i = 0;i<SLBase.states.length;i++) {
					String action =  arf.chosenAction(EKAL.predict(SLBase.states[i]));
					String actual = arf.chosenAction(SLBase.actions[i]);

					//System.out.println(Arrays.toString((EKAL.predict(SLBase.states[i]))));
					System.out.println("Chosen move by EKAL: " + action);
					System.out.println("Chosen move by AG0: " + actual + "\n---");
					if (actual.equals(action)) score+=(1.0/SLBase.states.length);
				}
				System.out.println((score*100) +"% correct.");
				if (score>=topScore) {
					mxjava.outputSynapses(EKAL,"EKALGO120");
					topScore = score;
				}
			}
		}

		//Play some games against the computer.



	}
}
