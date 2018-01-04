package ekalGO;

import java.util.Arrays;

public class MoveSort {
    
    private double array[];
    private String moves[];
    private int length;
 
    public void sort(double[] inputArr, String[] moves) {
         
        if (inputArr == null || inputArr.length == 0) {
            return;
        }
        this.array = inputArr;
        this.moves = moves;
        length = inputArr.length;
        quickSort(0, length - 1);
    }
 
    private void quickSort(int lowerIndex, int higherIndex) {
         
        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        double pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
        // Divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which 
             * is greater then the pivot value, and also we will identify a number 
             * from right side which is less then the pivot value. Once the search 
             * is done, then we exchange both numbers.
             */
            while (array[i] > pivot) {
                i++;
            }
            while (array[j] < pivot) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                exchangeMoves(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }
 
    private void exchangeNumbers(int i, int j) {
        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    private void exchangeMoves(int i, int j) {
        String temp = moves[i];
        moves[i] = moves[j];
        moves[j] = temp;
    }
     
    public static void main(String a[]){
         
        MoveSort sorter = new MoveSort();
        double[] input = {0.24,0.2,0.45,0.20,0.56,0.75,0.2,0.56,0.99,0.53,0.12};
        String[] moves = {"24A","2A","45A","20A","56A","75A","2B","56A","99A","53A","12A"};
        sorter.sort(input,moves);
        System.out.println(Arrays.toString(input));
        System.out.println(Arrays.toString(moves));
    }
}
