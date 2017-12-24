package ekalGO;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;


/*
 * This gives us a lot of the main linear algebra/matrix based functions required for our artificial neural network. It's ugly like me, and lazy like me.
 */

public class mxjava {
	
	public static double[] connectArrays(double[] a, double[] b) {
		double[] newArray = new double[a.length+b.length];
		for (int i = 0;i<a.length;i++) {
			newArray[i] = a[i];
		}
		for (int j = 0;j<b.length;j++) {
			newArray[j+a.length] = b[j];
		}
		return newArray;
	}
	
	public static double[] twoDtoOne(FeatureMap a) {
		double[] neuralNetworkInput = new double[a.processedPhoto.length*a.processedPhoto[0].length];
		for (int i = 0;i<a.processedPhoto.length;i++) {
			for (int j = 0;j<a.processedPhoto[0].length;j++) {
				int directory = j+i*a.processedPhoto[0].length;
				neuralNetworkInput[directory] = a.processedPhoto[i][j];
			}
		}
		return neuralNetworkInput;
	}
	
	public static double[][] loadSynapse0(GoBrain brain, String direct) throws IOException {
		double[][] newSyn = new double[brain.synapse0.length][brain.synapse0[0].length];
		String documents = System.getProperty ("user.home") + "/Documents/Ralyas Language Training/" + direct + "Synapses/" + direct + "Synapse0";
		FileReader stock = new FileReader (documents);
		BufferedReader b1 = new BufferedReader(stock);
		String word;
		int count = 0;
		while ((word = b1.readLine()) != null) {
			//if (count<10) System.out.println("["+count/brain.synapse0.length+"]["+ count%brain.synapse0[0].length+"] = " + Double.parseDouble(word));
			int number = brain.synapse0[0].length;
			newSyn[count/number][count%brain.synapse0[0].length] = Double.parseDouble(word);
			//if (count<100) System.out.println("["+count/number+"]["+ count%brain.synapse0[0].length+"] = " + newSyn[count/brain.synapse0[0].length][count%brain.synapse0[0].length]);
			count++;
			
		}
		return newSyn;
	}
	public static double[][] loadSynapse1(GoBrain brain, String direct) throws IOException {
		String documents = System.getProperty ("user.home") + "/Documents/Ralyas Language Training/" + direct + "Synapses/" + direct + "Synapse1";
		FileReader stock = new FileReader (documents);
		BufferedReader b2 = new BufferedReader(stock);
		String word;
		double[][] newSyn = new double[brain.synapse1.length][brain.synapse1[0].length];
		int count = 0;
		while ((word = b2.readLine()) != null) {
			newSyn[count/brain.synapse1.length][count%brain.synapse1.length] = Double.parseDouble(word);
			count++;
		}
		return newSyn;
	}
	public static double[][] loadSynapse2(GoBrain brain, String direct) throws IOException {
		String documents = System.getProperty ("user.home") + "/Documents/Ralyas Language Training/" + direct + "Synapses/" + direct + "Synapse2";
		FileReader stock = new FileReader (documents);
		BufferedReader buffer = new BufferedReader(stock);
		String word;
		double[][] newSyn = new double[brain.synapse2.length][brain.synapse2[0].length];
		int count = 0;
		while ((word = buffer.readLine()) != null) {
			newSyn[count/brain.synapse2.length][count%brain.synapse2.length] = Double.parseDouble(word);
			count++;
		}
		return newSyn;
	}
	public static double[][] loadSynapse3(GoBrain brain, String direct) throws IOException {
		String documents = System.getProperty ("user.home") + "/Documents/Ralyas Language Training/" + direct + "Synapses/" + direct + "Synapse3";
		FileReader stock = new FileReader (documents);
		BufferedReader buffer = new BufferedReader(stock);
		String word;
		double[][] newSyn = new double[brain.synapse3.length][brain.synapse3[0].length];
		int count = 0;
		while ((word = buffer.readLine()) != null) {
			newSyn[count/brain.synapse3.length][count%brain.synapse3.length] = Double.parseDouble(word);
			count++;
		}
		return newSyn;
	}
	public static double[][] loadSynapse4(GoBrain brain, String direct) throws IOException {
		String documents = System.getProperty ("user.home") + "/Documents/Ralyas Language Training/" + direct + "Synapses/" + direct + "Synapse4";
		FileReader stock = new FileReader (documents);
		BufferedReader buffer = new BufferedReader(stock);
		String word;
		double[][] newSyn = new double[brain.synapse4.length][brain.synapse4[0].length];
		int count = 0;
		while ((word = buffer.readLine()) != null) {
			newSyn[count/brain.synapse4.length][count%brain.synapse4.length] = Double.parseDouble(word);
			count++;
		}
		return newSyn;
	}
	public static double[][] loadSynapse5(GoBrain brain, String direct) throws IOException {
		String documents = System.getProperty ("user.home") + "/Documents/Ralyas Language Training/" + direct + "Synapses/" + direct + "Synapse5";
		FileReader stock = new FileReader (documents);
		BufferedReader buffer = new BufferedReader(stock);
		String word;
		double[][] newSyn = new double[brain.synapse5.length][brain.synapse5[0].length];
		int count = 0;
		while ((word = buffer.readLine()) != null) {
			newSyn[count/brain.synapse5.length][count%brain.synapse5.length] = Double.parseDouble(word);
			count++;
		}
		return newSyn;
	}
	public static double[][] loadSynapse6(GoBrain brain, String direct) throws IOException {
		String documents = System.getProperty ("user.home") + "/Documents/Ralyas Language Training/" + direct + "Synapses/" + direct + "Synapse6";
		FileReader stock = new FileReader (documents);
		BufferedReader buffer = new BufferedReader(stock);
		String word;
		double[][] newSyn = new double[brain.synapse6.length][brain.synapse6[0].length];
		int count = 0;
		while ((word = buffer.readLine()) != null) {
			newSyn[count/brain.synapse6.length][count%brain.synapse6.length] = Double.parseDouble(word);
			count++;
		}
		return newSyn;
	}
	public static double[][] loadSynapse7(GoBrain brain, String direct) throws IOException {
		String documents = System.getProperty ("user.home") + "/Documents/Ralyas Language Training/" + direct + "Synapses/" + direct + "Synapse7";
		FileReader stock = new FileReader (documents);
		BufferedReader buffer = new BufferedReader(stock);
		String word;
		double[][] newSyn = new double[brain.synapse7.length][brain.synapse7[0].length];
		int count = 0;
		while ((word = buffer.readLine()) != null) {
			newSyn[count/brain.synapse7.length][count%brain.synapse7.length] = Double.parseDouble(word);
			count++;
		}
		return newSyn;
	}
	public static double[][] loadSynapse8(GoBrain brain, String direct) throws IOException {
		String documents = System.getProperty ("user.home") + "/Documents/Ralyas Language Training/" + direct + "Synapses/" + direct + "Synapse8";
		FileReader stock = new FileReader (documents);
		BufferedReader buffer = new BufferedReader(stock);
		String word;
		double[][] newSyn = new double[brain.synapse8.length][brain.synapse8[0].length];
		int count = 0;
		while ((word = buffer.readLine()) != null) {
			newSyn[count/brain.synapse8.length][count%brain.synapse8.length] = Double.parseDouble(word);
			count++;
		}
		return newSyn;
	}
	public static double[][] loadSynapseFinal(GoBrain brain, String direct) throws IOException {
		String documents = System.getProperty ("user.home") + "/Documents/Ralyas Language Training/" + direct + "Synapses/" + direct + "SynapseFinal";
		FileReader stock = new FileReader (documents);
		BufferedReader buffer = new BufferedReader(stock);
		String word;
		double[][] newSyn = new double[brain.finalSynapse.length][brain.finalSynapse[0].length];
		int count = 0;
		while ((word = buffer.readLine()) != null) {
			newSyn[count%(brain.finalSynapse.length)][count%brain.finalSynapse[0].length] = Double.parseDouble(word);
			count++;
		}
		return newSyn;
	}
	
	
	public static void outputSynapses(GoBrain brain, String direct) throws IOException {
		System.out.println("updating synapses...");
		System.out.println("updating synapses...");
		System.out.println("updating synapses...");
		System.out.println("updating synapses...");
		System.out.println("updating synapses...");
		//Put all the information into a document. Print first layer of synapses. Stock Information not stored here.
		int buffSize = 8192*16;
		String documents = System.getProperty ("user.home") + "/Documents/Ralyas Language Training/" + direct + "Synapses/" + direct + "Synapse";
		//I am so lazy and tired.
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(documents + "0"),buffSize));
		PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter(documents + "1"),buffSize));
		PrintWriter out3 = new PrintWriter(new BufferedWriter(new FileWriter(documents + "2"),buffSize));
		PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(documents + "3"),buffSize));
		PrintWriter out5 = new PrintWriter(new BufferedWriter(new FileWriter(documents + "4"),buffSize));
		PrintWriter out6 = new PrintWriter(new BufferedWriter(new FileWriter(documents + "5"),buffSize));
		PrintWriter out7 = new PrintWriter(new BufferedWriter(new FileWriter(documents + "6"),buffSize));
		PrintWriter out8 = new PrintWriter(new BufferedWriter(new FileWriter(documents + "7"),buffSize));
		PrintWriter out9 = new PrintWriter(new BufferedWriter(new FileWriter(documents + "8"),buffSize));
		PrintWriter out10 = new PrintWriter(new BufferedWriter(new FileWriter(documents + "Final"),buffSize));
		
		//break it down!
		out.write("");
		out2.write("");
		out3.write("");
		out4.write("");
		out5.write("");
		out6.write("");
		out7.write("");
		out8.write("");
		out9.write("");
		out10.write("");
		out.flush();
		out2.flush();
		out3.flush();
		out4.flush();
		out5.flush();
		out6.flush();
		out7.flush();
		out8.flush();
		out9.flush();
		out10.flush();
		
		for (int i = 0;i<brain.returnSynapse0().length;i++) {
			for (int j = 0;j<brain.returnSynapse0()[0].length;j++) {
				out.flush();
				out.println(brain.returnSynapse0()[i][j]);
			}
		}
		for (int i = 0;i<brain.returnSynapse1().length;i++) {
			for (int j = 0;j<brain.returnSynapse1()[0].length;j++) {
				out2.flush();
				out2.println(brain.returnSynapse1()[i][j]);
				
			}	
		}
		for (int i = 0;i<brain.returnSynapse2().length;i++) {
			for (int j = 0;j<brain.returnSynapse2()[0].length;j++) {
				out3.flush();
				out3.println(brain.returnSynapse2()[i][j]);
				
			}
		}
		for (int i = 0;i<brain.returnSynapse3().length;i++) {
			for (int j = 0;j<brain.returnSynapse3()[0].length;j++) {
				out4.flush();
				out4.println(brain.returnSynapse3()[i][j]);
				
			}

		}
		for (int i = 0;i<brain.returnSynapse4().length;i++) {
			for (int j = 0;j<brain.returnSynapse4()[0].length;j++) {
				out5.flush();
				out5.println(brain.returnSynapse4()[i][j]);
				
			}

		}
		for (int i = 0;i<brain.returnSynapse5().length;i++) {
			for (int j = 0;j<brain.returnSynapse5()[0].length;j++) {
				out6.flush();
				out6.println(brain.returnSynapse5()[i][j]);
				
			}

		}
		for (int i = 0;i<brain.returnSynapse6().length;i++) {
			for (int j = 0;j<brain.returnSynapse6()[0].length;j++) {
				out7.flush();
				out7.println(brain.returnSynapse6()[i][j]);
				
			}
		}
		for (int i = 0;i<brain.returnSynapse7().length;i++) {
			for (int j = 0;j<brain.returnSynapse7()[0].length;j++) {
				out8.flush();
				out8.println(brain.returnSynapse7()[i][j]);
				
			}

		}
		for (int i = 0;i<brain.returnSynapse8().length;i++) {
			for (int j = 0;j<brain.returnSynapse8()[0].length;j++) {
				out9.flush();
				out9.println(brain.returnSynapse8()[i][j]);
				
			}

		}
		for (int i = 0;i<brain.returnFinalSynapse().length;i++) {
			for (int j = 0;j<brain.returnFinalSynapse()[0].length;j++) {
				out10.flush();
				out10.println(brain.returnFinalSynapse()[i][j]);
				
			}

		}
		out.close();
		out2.close();
		out3.close();
		out4.close();
		out5.close();
		out6.close();
		out7.close();
		out8.close();
		out9.close();
		out10.close();
	}

	public static double sigmoidPackage(double x, boolean deriv) {
		if (deriv) {
			return x*(1-x);
		}
		return 1/(1+Math.pow(Math.E,-x));
	}
	
	//CNN SPECIFIC
	public static int convolution(double[][] imagePiece,double[][] filter) {
		int newVal = 0;
		for (int i = 0; i < filter.length; i++) { 
			for (int j = 0; j < filter[0].length; j++) { 
				newVal += imagePiece[i][j]*filter[filter.length-i-1][filter[0].length-j-1];
				//newVal += rawPhoto[i][j]*filter[i][j];
				
			}
		}
		return newVal;
	}
	
	public static int[][] randomFilter() {
		int[][] filter = new int[3][3];
		for (int i = 0;i<filter.length;i++) {
			for (int j = 0; j<filter[0].length;j++) {
				filter[i][j] = (int) (Math.random()*4-2); //NUMBERS ARE TOO BIG, GIVE NO FLEXIBILITY
			}
		}
		return filter;
	}

	public static double[][] synapseLayer(int inputs, int outputs) {
		double[][] LAYER = new double[inputs][outputs];
		for (int i = 0;i<inputs;i++) {
			for (int j = 0;j<outputs;j++) {
				LAYER[i][j] = (Math.random()*1.8)-0.9;
			}
		}
		return LAYER;
	}

	public static double[][] matrixMult(double[][] firstMatrix, double[][] secondMatrix) {
		double[][] newMatrix = new double[firstMatrix.length][secondMatrix[0].length];
		for (int i = 0; i < newMatrix.length; i++) { 
			for (int j = 0; j < newMatrix[0].length; j++) { 
				for (int k = 0; k < firstMatrix[0].length; k++) { 
					newMatrix[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
				}
			}
		}
		return newMatrix;
	}
	public static double[][] scalarMult(double[][]firstMatrix,double[][]secondMatrix) {
		double[][] newMatrix = new double[firstMatrix.length][secondMatrix[0].length];
		for (int i = 0; i < newMatrix.length; i++) { 
			for (int j = 0; j < newMatrix[0].length; j++) { 
				newMatrix[i][j] = firstMatrix[i][j]*secondMatrix[i][j];
			}
		}
		return newMatrix;
	}
	public static double[][] subtract(double[][] firstMatrix, double[][] secondMatrix) {
		double[][] result = new double[firstMatrix.length][firstMatrix[0].length];
		for (int i = 0; i < firstMatrix.length; i++) {
			for (int j = 0; j < firstMatrix[0].length; j++) {
				result[i][j] = firstMatrix[i][j] - secondMatrix[i][j];
			}
		}
		return result;
	}
	public static double[][] add(double[][] firstMatrix, double[][] secondMatrix) {
		double[][] result = new double[firstMatrix.length][firstMatrix[0].length];
		for (int i = 0; i < firstMatrix.length; i++) {
			for (int j = 0; j < firstMatrix[0].length; j++) {
				result[i][j] = firstMatrix[i][j] + secondMatrix[i][j];
			}
		}
		return result;
	}

	public static double[][] transpose(double [][] m){
		double[][] temp = new double[m[0].length][m.length];
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m[0].length; j++)
				temp[j][i] = m[i][j];
		return temp;
	}

	public static void print(String x, double[][]m) {
		System.out.println(x);
		for (int i = 0;i<m.length;i++) {
			for (int j = 0;j<m[0].length;j++) {
				System.out.println(m[i][j]);
			}
			System.out.println("-----row done-----");
		}		
		System.out.println("\n");
	}
}
