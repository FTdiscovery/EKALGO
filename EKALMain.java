package ekalGO;

import java.io.IOException;
import java.util.Arrays;


/*
 * EKALMain.java:
 * ---------------------
 * Here we begin the exploration of EKAL - evste'na kalpak yo'nuron eyeshkomyod, a concept of Dutrum Mesnovich, 2009
 * Developed in theory by Mesnovich at University of Ralyas, Piray.
 * 
 * This project starts on December 80, 8017, and focuses on the creation of a computer that learns the evaluation
 * and methods to play the game of Go using a database of master's games, a deep artificial neural network, 
 * Monte Carlo Tree Search to evaluate and update playout values for familiar positions and efficiency, 
 * then reinforcement learning 'tournaments' between separately trained neural networks with different parameters to determine
 * the best moves.
 * 
 * After a certain time of training, the computer will choose the best engine out of the created ones and pick that to compete against a human.
 * The Neural Network is designed so that the synapses can be saved on a computer.
 */

public class EKALMain {

	public static void main(String[] args) throws IOException {
		
		String[] files = {"AG0_AG0_5185", "AG0_AG0_5185","AG0_AG0_5185","AG0_AG0_5185","AG0_AG0_5185","AG0_AG0_5185","AG0_AG0_5125", "AG0_AG0_5125","AG0_AG0_5125","AG0_AG0_5125","AG0_AG0_5125","AG0_AG0_5125"};
		//this below will be generalized later on, checking if this is beneficial first.
		boolean[] flip180 = {false,true,false,false,false,false,false,true,false,false,false,false};
		boolean[] flip90CC = {false,false,true,false,false,false,false,false,true,false,false,false};
		boolean[] flip90C = {false,false,false,true,false,false,false,false,false,true,false,false};
		boolean[] mirrorX = {false,false,false,false,true,false,false,false,false,false,true,false};
		boolean[] mirrorY = {false,false,false,false,false,true,false,false,false,false,false,true};
		//Generalized Flip function will be created. Flipping will be done after every playout.
		//Reinforcement Playouts after Supervised Learning can create a lot of data...but training this network could take years.
		boolean[] blackWin = {false,false,false,false,false,false,false,false,false,false,false,false};
		Library SLBase = new Library(files,flip180,flip90CC,flip90C,mirrorX,mirrorY,blackWin);
		SLBase.createDataset(false);
		
		
		//Note: 105 NPL || LR 0.01 WORKS || 0.7 MOM on old.
		
		int NODES_PER_LAYER = 80;
		double LEARN_RATE = 0.01;

		GoBrain EKAL = new GoBrain(SLBase.states,SLBase.actions,NODES_PER_LAYER,LEARN_RATE);
		EKAL.momentum = 0.8;

		//Download synapses.
		boolean download = true;
		if (download) {
			EKAL.synapse0 = mxjava.loadSynapse0(EKAL, "EKALGO80");
			EKAL.synapse1 = mxjava.loadSynapse1(EKAL, "EKALGO80");
			EKAL.synapse2 = mxjava.loadSynapse2(EKAL, "EKALGO80");
			EKAL.synapse3 = mxjava.loadSynapse3(EKAL, "EKALGO80");
			EKAL.synapse4 = mxjava.loadSynapse4(EKAL, "EKALGO80");
			EKAL.synapse5 = mxjava.loadSynapse5(EKAL, "EKALGO80");
			EKAL.synapse6 = mxjava.loadSynapse6(EKAL, "EKALGO80");
			EKAL.synapse7 = mxjava.loadSynapse7(EKAL, "EKALGO80");
			EKAL.synapse8 = mxjava.loadSynapse8(EKAL, "EKALGO80");
			EKAL.finalSynapse = mxjava.loadSynapseFinal(EKAL, "EKALGO80");	
		}

		int sc = 0;
		for (int i = 0;i<SLBase.states.length;i++) {
			String action = arf.chosenAction(EKAL.predict(SLBase.states[i]));
			String actual = arf.trainingActionArray(SLBase.actions[i]);
			System.out.println("Chosen move by EKAL: " + action);
			System.out.println("Chosen move by AG0: " + actual + "\n---");
			if (actual.indexOf(action)!=-1) sc+=(1);
		}
		System.out.println("SAVED SYNAPSES: " + (sc) +" out of " + SLBase.states.length + " correct.");
		System.out.println(Arrays.toString(SLBase.actions[0]));
		System.out.println(Arrays.toString(EKAL.predict(SLBase.states[0])));


		boolean training = true;
		double topScore = (double) sc/SLBase.states.length;
		System.out.println(topScore);
		//Training.
		if (training) {
			for (int j = 0;j<500000;j++) {
				EKAL.trainNetwork(20);
				System.out.println("\n-------\nITERATION #"+((j+1)*1)+"\n");
				double score = 0;
				//check how accurate they are
				for (int i = 0;i<SLBase.states.length;i++) {
					String action =  arf.chosenAction(EKAL.predict(SLBase.states[i]));
					String actual = arf.trainingActionArray(SLBase.actions[i]);

					//System.out.println(Arrays.toString((EKAL.predict(SLBase.states[i]))));
					System.out.println("Chosen move by EKAL: " + action);
					System.out.println("Chosen move by AG0: " + actual + "\n---");
					if (actual.indexOf(action)!=-1) score+=(1.0/SLBase.states.length);
				}
				System.out.println((score*100) +"% correct.");
				System.out.println(topScore*100 + "%: Top Score.");
				System.out.println("Training Data Size: " + SLBase.states.length);
				if (score>=topScore) {
					mxjava.outputSynapses(EKAL,"EKALGO80");
					topScore = score;
				}
			}
		}

		//Play some games against the computer.

	}
}

