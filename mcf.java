package ekalGO;

public class mcf {
	
	//This UCT1 Array is designed to return an array that balances exploration with exploitation.
	public static double[] UCT1Array(double[] w, double[] n, double t, double constant, double scale) {
		double[] newArray = new double[w.length];
		for (int i = 0;i<newArray.length;i++) {
			newArray[i] = UCT1(w[i],n[i],t,constant,scale);
		}
		return newArray;
	}

	public static double UCT1(double w, double n, double t, double constant, double c2) {
		if (n!=0) {
			double winRatio = w/n;
			double underSquareRoot = Math.log(t)/n;
			double secondPart = constant * (Math.sqrt(underSquareRoot));
			return (winRatio+secondPart);
		}
		else {
			//means move has not been explored.
			return (w*100)*c2; 
			/* This just returns the value as estimated by the neural network, but the neural network is trained to treat
			 * everything not seen as an invalid move. 
			 * We augment the value of what the neural network says, then multiply this by another given constant. 
			 * For best results, this constant should be high for the first 10-20 moves of the game,
			 * and near zero at the end (computer has probably never seen the actions before).
			 */

		}

	}

}
