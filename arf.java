package ekalGO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class arf {
	/*
	 * arf stands for Array Functions. Refers to a few array functions that will be used in terms of capturing pieces.
	 */
	
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
