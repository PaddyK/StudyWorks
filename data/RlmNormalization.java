package data;

public class RlmNormalization implements Normalization {
	private static final String[] controlIds = new String[]{"HumanIgG1",
		"HumanIgG2",
		"HumanIgG3",
		"HumanIgG4",
		"Anti-HumanIgG1",
		"Anti-HumanIgG2",
		"Anti-HumanIgG3",
		"Anti-HumanIgG4",
		"V5control1",
		"V5control2",
		"V5control3",
		"V5control4",
		"V5control5",
		""
	};
	
	private static final int[][] controlPosition = new int[][]{{1,5},
		{1,7},
		{1,9},
		{1,11},
		{11,9},
		{11,11},
		{11,13},
		{11,15},
		{22,7},
		{22,9},
		{22,11},
		{22,13},
		{22,15}
	};
	@Override
	public void normalize(Microarray microarray) {
		// TODO Auto-generated method stub
		
	}
	
	private void createMatrix(Microarray microarray) {
		int blockcount = microarray.getBlockColumns() * microarray.getBlockRows();
		int matrix[][] = new int[7][7];
		for(int i = 0; i < blockcount; i++) {
			for(int j = 0; j < blockcount; j++) {
				for(int k = 0; k < controlPosition.length; k++) {
					
				}
			}
		}
	}
	
}