package ekalGO;

import java.util.ArrayList;
import java.util.Arrays;

public class arf {
	/*
	 * arf stands for Array Functions. Refers to a few array functions that will be used in terms of capturing pieces.
	 */
	
	
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
