package ekalGO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ConvolutionsNN {

	//define kernels
	static double[][] EDGE_DETECT_3 = {{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
	static double[][] ENHANCE = {{0,0,0},{-1,1,0},{0,0,0}};
	static double[][] EDGE_DETECT_1 = {{1,0,-1},{0,0,0},{-1,0,1}};
	static double[][] EDGE_DETECT_2 = {{0,1,0},{1,-4,1},{0,1,0}};
	static double[][] BOTTOM_SOBEL = {{-1,-2,-1},{0,0,0},{1,2,1}};
	static double[][] TOP_SOBEL = {{1,2,1},{0,0,0},{-1,-2,-1}};
	static double[][] LEFT_SOBEL = {{1,0,-1},{2,0,-2},{1,0,-1}};
	static double[][] RIGHT_SOBEL = {{-1,0,1},{-2,0,2},{-1,0,1}};
	static double[][] COPY = {{0,0,0},{0,1,0},{0,0,0}};
	static double[][] SHIFT_LEFT = {{0,0,0},{1,0,0},{0,0,0}};
	
	public static double[][] createRandomKernel() {
		double[][] randomKernel = new double[3][3];
		for (int i = 0;i<3;i++) {
			for (int j = 0;j<3;j++) {
				randomKernel[i][j]= Math.random();
			}
		}
		return randomKernel;
	}

	//In this area, you can change the construction of the Image
	public static double[] processed(double[][][] BOARD) {

		double[][] WHITE_STONE_MAP=new double[19][19];
		double[][] BLACK_STONE_MAP=new double[19][19];
		for (int i=0;i<19;i++) {
			for (int j=0;j<19;j++) {
				BLACK_STONE_MAP[i][j] = BOARD[i][j][0];
				WHITE_STONE_MAP[i][j] = BOARD[i][j][1];	
			}
		}

		//FIRST LAYER
		FeatureMap a = new FeatureMap(WHITE_STONE_MAP); //YOU CAN DETERMINE THE PARENT OF THE FEATURE MAP
		a.applyConvolution(TOP_SOBEL);

		FeatureMap b = new FeatureMap(WHITE_STONE_MAP); 
		b.applyConvolution(EDGE_DETECT_2);

		FeatureMap c = new FeatureMap(BLACK_STONE_MAP); 
		c.applyConvolution(RIGHT_SOBEL);

		FeatureMap d = new FeatureMap(BLACK_STONE_MAP); 
		d.applyConvolution(EDGE_DETECT_2);

		double[] imageProcessed = mxjava.connectArrays(mxjava.connectArrays(mxjava.connectArrays(mxjava.twoDtoOne(a),mxjava.twoDtoOne(b)), mxjava.connectArrays(mxjava.twoDtoOne(c),mxjava.twoDtoOne(d))), mxjava.connectArrays(mxjava.twoDtoOne(c),mxjava.twoDtoOne(d)));;

		System.out.println(Arrays.toString(imageProcessed)+"\n-----");

		return imageProcessed;
	}


}
