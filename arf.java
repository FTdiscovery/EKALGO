package ekalGO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class arf {
	/*
	 * arf stands for Array Functions. Refers to a few array functions that will be used in terms of capturing pieces.
	 */
	
	public static int stateAlreadySeen(ArrayList<double[]> states, double[] current) {
		for (int i = 0;i<states.size();i++) {
			if (sameArray(states.get(i),current)) return i;
		}
		return -1;
	}
	
	public static boolean chainAlreadySeen(ArrayList<int[]> states, int[] current) {
		for (int i = 0;i<states.size();i++) {
			if (sameIntArray(states.get(i),current)) return true;
		}
		return false;
	}
	
	public static boolean sameArray(double[] a,double[] b) {
		if (a.length!=b.length) return false;
		for (int i = 0;i<a.length;i++) {
			if (a[i]!=b[i]) return false;
		}
		return true;
	}
	
	public static boolean sameIntArray(int[] a,int[] b) {
		if (a.length!=b.length) return false;
		for (int i = 0;i<a.length;i++) {
			if (a[i]!=b[i]) return false;
		}
		return true;
	}
	
	public static double[] compoundActions(int dup, double[] a, double[] b) {
		double[] action = new double[a.length];
		for (int i = 0;i<a.length;i++) {
			if (a[i]==0 || b[i]==0) action[i]=Math.max(a[i], b[i]); 
			//If action exists in both and has different values, then average them out and trust the MCTS.
			//Note: it's probably nice to note how many duplicates a state has to make this more accurate.
			else {
				double kye = a[i]*((double)dup);
				action[i] = (kye+b[i])/((double)dup+1);
			}
			
		}
		return action;
	}
	
	public static double[] expertAction(String move, double value) {
		String ALPHABET = "ABCDEFGHJKLMNOPQRST";
		double[] action = new double[362]; //Directory 361 is for pass.
		for (int d = 0;d<action.length;d++) {
			action[d]=0; //might change this...
		}
		int i = ALPHABET.indexOf(move.substring(0,1)); //this is column
		int j = 19-Integer.parseInt(move.substring(1)); //this is row
		action[(j*19)+i]=value;
		return action;
	}
	
	public static String trainingActionArray(double[] array) {
		String ALPHABET = "ABCDEFGHJKLMNOPQRST";
		String answers="";
		int max = 0;
		for (int i = 1;i<array.length;i++) {
			if (array[i]>array[max]) {
				max=i;
				int col = max%19;
				int row = 19-(max/19);
				answers = ALPHABET.split("")[col]+row;
			}
			else if (array[i]==array[max]) {
				max=i;
				int col = max%19;
				int row = 19-(max/19);
				answers += ALPHABET.split("")[col]+row;
			}
		}
		System.out.println(array[max]); //just for check.
		if (max==361) return "PASS";
		return answers;
	} 
	
	
	public static String chosenAction(double[] array) {
		String ALPHABET = "ABCDEFGHJKLMNOPQRST";
		int max = 0;
		for (int i = 1;i<array.length;i++) {
			if (array[i]>array[max]) {
				max=i;
			}
		}
		int col = max%19;
		int row = 19-(max/19);
		System.out.println(array[max]);
		if (max==361) return "PASS";
		return ALPHABET.split("")[col]+row;
	}
	
	public static int[] connectArrays(int[] a, int[] b) {
		int[] newArray = new int[a.length+b.length];
		for (int i = 0;i<a.length;i++) {
			newArray[i] = a[i];
		}
		for (int j = 0;j<b.length;j++) {
			newArray[j+a.length] = b[j];
		}
		return newArray;
	}
			
	public static int[] inaccStones(int[] stones, int[] wrongSurround) {
		ArrayList<Integer> ky = new ArrayList<Integer>();
		for (int i = 0;i<wrongSurround.length;i++) {
			if (!inArray(wrongSurround[i],stones)) ky.add(wrongSurround[i]);
		}
		int[] retArr = new int[ky.size()];
		for (int i = 0;i<ky.size();i++) {
			retArr[i]=ky.get(i);
		}
		return retArr;
	}
	
	public static boolean inArray(int a, int[] b) {
		for (int i = 0;i<b.length;i++) {
			if (a==b[i]) return true;
		}
		return false;
	}
	
	public static int[] removeDuplicates(int[] arr) {
		  Set<Integer> alreadyPresent = new HashSet<Integer>();
		  int[] whitelist = new int[0];

		  for (int nextElem : arr) {
		    if (!alreadyPresent.contains(nextElem)) {
		      whitelist = Arrays.copyOf(whitelist, whitelist.length + 1);
		      whitelist[whitelist.length - 1] = nextElem;
		      alreadyPresent.add(nextElem);
		    }
		  }

		  return whitelist;
		}
	
	public static int[] appendArrays(int[] a, int[] b) {
		int[] newArr = new int[a.length+b.length];
		for (int i = 0;i<a.length;i++) {
			newArr[i]=a[i];
		}
		for (int i=0;i<b.length;i++) {
			newArr[a.length+i]=b[i];
		}
		return newArr;
	}

}
