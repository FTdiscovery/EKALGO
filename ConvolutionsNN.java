package ekalGO;

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
	
	static double[][] KERNEL1 = {{1.4,1.6,1.9},{2.1,5.1,1.3},{0.6,1.87,2.31}};;
	static double[][] KERNEL2 = {{2.5,4.4,3.5},{3.3,1.7,0.6},{1.8,2.11,4.05}};;
	
	public static double[][] createRandomKernel() {
		double[][] randomKernel = new double[3][3];
		for (int i = 0;i<3;i++) {
			for (int j = 0;j<3;j++) {
				randomKernel[i][j]= Math.random()*10;
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
		a.applyConvolution(KERNEL1);

		FeatureMap b = new FeatureMap(WHITE_STONE_MAP); 
		b.applyConvolution(KERNEL2);

		FeatureMap c = new FeatureMap(WHITE_STONE_MAP); 
		c.applyConvolution(ENHANCE);

		FeatureMap d = new FeatureMap(BLACK_STONE_MAP); 
		d.applyConvolution(KERNEL1);
	
		FeatureMap e = new FeatureMap(BLACK_STONE_MAP); 
		e.applyConvolution(KERNEL2);

		FeatureMap f = new FeatureMap(BLACK_STONE_MAP); 
		f.applyConvolution(ENHANCE);
		
		//SECOND LAYER: POOL EACH ONE
		int poolingWidth = 19;
		int poolingHeight = 19;

		FeatureMap a1 = new FeatureMap(a.processedPhoto);
		a1.averagePooling(poolingWidth, poolingHeight); //width * height

		FeatureMap b1 = new FeatureMap(b.processedPhoto);
		b1.averagePooling(poolingWidth, poolingHeight);

		FeatureMap c1 = new FeatureMap(c.processedPhoto);
		c1.averagePooling(poolingWidth, poolingHeight);
		
		FeatureMap d1 = new FeatureMap(d.processedPhoto);
		d1.averagePooling(poolingWidth, poolingHeight);
		
		FeatureMap e1 = new FeatureMap(e.processedPhoto);
		e1.averagePooling(poolingWidth, poolingHeight);
		
		FeatureMap f1 = new FeatureMap(f.processedPhoto);
		f1.averagePooling(poolingWidth, poolingHeight);

		double[] imageProcessed = mxjava.connectArrays(mxjava.connectArrays(mxjava.twoDtoOne(a1),mxjava.twoDtoOne(b1)),mxjava.connectArrays(mxjava.connectArrays(mxjava.connectArrays(mxjava.twoDtoOne(a1),mxjava.twoDtoOne(b1)), mxjava.connectArrays(mxjava.twoDtoOne(c1),mxjava.twoDtoOne(d1))), mxjava.connectArrays(mxjava.twoDtoOne(e1),mxjava.twoDtoOne(f1))));
		
		//System.out.println(Arrays.toString(imageProcessed)+"\n-----");

		return imageProcessed;
	}


}
