package ekalGO;

import java.util.ArrayList;

/*
 * This is a class that goes through the most basic functions and algorithms required for the Monte Carlo Tree Search. 
 * 
 * EkalGO uses this to make decisions for familiar states after training. The values can be used in an Alpha-Beta method, and
 * this library MCTS has a guideline for it. However, it may not be needed.
 */
public class MCTS {

	double[][] states;
	double[][] actions;
	double[][] wins;
	double[][] n;
	double[][] t;
	
	//A MCTS should be paired with a neural network, but a 'root' library of training games at each rollout has to be saved.
	//Similarly, the MCTS should be imported.
	
	public MCTS(ArrayList<double[]> states, ArrayList<double[]> actions) {
		for (int i = 0;i<states.size();i++) { //theoretically states and actions should have the same size.
			this.states[i]=states.get(i);
			this.actions[i]=actions.get(i);
		}
		wins = new double[this.actions.length][361];
	}
}
