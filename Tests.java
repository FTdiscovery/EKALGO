package ekalGO;

import java.io.IOException;

public class Tests {

	public static void main(String[] args) throws IOException {
		String[] files = {"AG0_AGM_001","AG0_AGM_002","AG0_AGM_006","AG0_AG0_5185"};
		boolean[] flip180 = {false,true,true,false};
		boolean[] flip90CC = {false,false,false,false};
		boolean[] flip90C = {false,false,false,false};
		boolean[] mirrorX = {false, false, false, false};
		boolean[] mirrorY = {false, false, false, false};
		Library SLBase = new Library(files,flip180,flip90CC,flip90C,mirrorX,mirrorY);
		SLBase.createDataset(false);
		System.out.println(SLBase.states.length);
	}
}
