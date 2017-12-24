package ekalGO;

import java.util.Arrays;

public class FeatureMap {

	double[][] processedPhoto;
	double[][] oldPhoto;

	public FeatureMap(double[][] oldPhoto) {
		this.oldPhoto = oldPhoto;
	}
	public void applyConvolution(double[][] filter) {

		//ZERO THE BORDERS
		double[][] zeroBorderedPhoto = new double[oldPhoto.length+2][oldPhoto[0].length+2];
		for (int i = 0;i<zeroBorderedPhoto.length;i++) {
			for (int j = 0;j<zeroBorderedPhoto[0].length;j++) {
				if (i>=1 && j>=1 && i<=oldPhoto.length && j<= oldPhoto[0].length) zeroBorderedPhoto[i][j]=oldPhoto[i-1][j-1]; 
				else zeroBorderedPhoto[i][j] = 0;
			}
			//System.out.println(Arrays.toString(zeroBorderedPhoto[i]));
		}
		processedPhoto = new double[oldPhoto.length][oldPhoto[0].length];

		for (int i = 1;i<=oldPhoto.length;i++) {
			for (int j = 1;j<=oldPhoto[0].length;j++) {

				//CREATE IMAGE PIECE
				double[][] imagePiece = new double[3][3];
				for (int k = 0;k<3;k++) {
					for (int l = 0;l<3;l++) {
						imagePiece[k][l] = zeroBorderedPhoto[i-1+k][j-1+l]; 
					}
				}
				//NEGATES EXTREMES
				processedPhoto[i-1][j-1]=Math.min(256,Math.max(0,mxjava.convolution(imagePiece,filter)));

			}
		}
	}

	public void maxPooling(double squareWidth, double squareHeight) {
		if (oldPhoto[0].length%squareWidth==0 && oldPhoto.length%squareHeight==0) {
			processedPhoto = new double[(int) (oldPhoto.length/squareHeight)][(int) (oldPhoto[0].length/squareWidth)];
			//System.out.println(processedPhoto.length+"X"+processedPhoto[0].length);
			for (int i = 0;i<processedPhoto.length;i++) {
				for (int j = 0;j<processedPhoto[0].length;j++) {

					int heightDirectory = (int) (i*squareHeight);
					int widthDirectory = (int) (j*squareWidth);
					//find the numbers needed
					double max = oldPhoto[heightDirectory][widthDirectory];
					for (int k = 0; k< squareHeight;k++) {
						for (int l = 0;l<squareWidth;l++) {
							if (oldPhoto[heightDirectory+k][widthDirectory+l]>max) max = oldPhoto[heightDirectory+k][widthDirectory+l];
						}
					}
					processedPhoto[i][j]=max;
				}
			}
		}
		else {
			System.out.println("INVALID CONVOLUTION: Chosen Pooling Rectangle cannot be done.");
		}
	}

	public void averagePooling(double squareWidth, double squareHeight) {
		if (oldPhoto[0].length%squareWidth==0 && oldPhoto.length%squareHeight==0) {
			processedPhoto = new double[(int) (oldPhoto.length/squareHeight)][(int) (oldPhoto[0].length/squareWidth)];
			//System.out.println(processedPhoto.length+"X"+processedPhoto[0].length);
			for (int i = 0;i<processedPhoto.length;i++) {
				for (int j = 0;j<processedPhoto[0].length;j++) {

					int heightDirectory = (int) (i*squareHeight);
					int widthDirectory = (int) (j*squareWidth);
					//find the numbers needed
					double sum = 0;
					for (int k = 0; k< squareHeight;k++) {
						for (int l = 0;l<squareWidth;l++) {
							sum += oldPhoto[(int) (heightDirectory+k)][(int) (widthDirectory+l)];
						}
					}
					processedPhoto[(int) i][(int) j]= sum/(squareWidth*squareHeight);
				}
			}
		}
		else {
			System.out.println("INVALID CONVOLUTION: Chosen Pooling Rectangle cannot be done.");
		}
	}

	public void print() {
		try {
			for (int i = 0;i<processedPhoto.length;i++) {
				System.out.println(Arrays.toString(processedPhoto[i]));
			}
		}
		catch (Exception NullPodoubleerException) {
			System.out.println("No convolution was applied.");
		}
	}

}
