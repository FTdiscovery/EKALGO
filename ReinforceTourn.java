package ekalGO;

import java.util.Arrays;

/*
 * Here is the arena for the neural network go computers to fight against each other! 
 * 
 * Doo doo doo...
 * 
 * ?? And they arrive now?? Do they have names??
 * 
 * *hisses* yes they do...here they are!
 */
public class ReinforceTourn {

	GoBrain[] engines;
	String[] engineNames;
	String[] name1 = {"kye","mi","du","ele","da","gor","a","kash","bre","al","kom","su","laz","a","pyo","pe"};
	String[] name2 = {"lan","na","trum","nel","don","leks","yan","kan","ban","van","yar","min","lo","nus","tra"};
	String[] suffix = {"08","KA","OCO","神","777","09","112","D","+","Z"};

	public ReinforceTourn(GoBrain[] a) {
		engines = a;
		engineNames = new String[engines.length];
	}

	public void generateNames() {
		for (int i = 0;i<engineNames.length;i++) {
			int a = (int) (Math.random()*name1.length);
			int b = (int) (Math.random()*name2.length);
			int c = (int) (Math.random()*suffix.length);
			engineNames[i]=name1[a]+name2[b]+suffix[c];
		}
	}
	
	//Here is the display of standard protocol
	public static void main(String[] args) {
		GoBrain[] engines = new GoBrain[5];
		ReinforceTourn ZERO_TRAINING_BOTS = new ReinforceTourn(engines);
		ZERO_TRAINING_BOTS.generateNames();
		System.out.println(Arrays.toString(ZERO_TRAINING_BOTS.engineNames));
	}
}
