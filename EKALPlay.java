package ekalGO;

import java.io.IOException;
import java.util.Arrays;

public class EKALPlay {

	/*
	 * This is a test to check if we can play the games. :)
	 */
	public static void main(String[] args) throws IOException {

		double[][] input = new double[1][4398];
		double[][] output = new double[1][362];

		int NODES_PER_LAYER = 80;
		double LEARN_RATE = 0.01;

		GoBrain EKAL = new GoBrain(input,output,NODES_PER_LAYER,LEARN_RATE);
		EKAL.momentum = 0.85;

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

		//Play some games against the computer.
		Board a = new Board();
		String gameLog="---------\nEKALGO VS EKALGO:\n";
		for (int i = 0;i<200;i++) {
			String move = MoveChoice.bestMoves(EKAL.predict(a.BoardToState()), a)[0];
			a.makeMove(move);
			System.out.println("MOVE: " + move);
			a.printBoard();
			if (i%2==0)gameLog+= "BLACK: " +move+"\n";
			else gameLog+= "WHITE: " +move+"\n";
		}
		System.out.println(gameLog);


	}
}
