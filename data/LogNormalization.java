package data;

public class LogNormalization implements Normalization {
	
	/**
	 * Performs simple normalization by applying log_2(signal)
	 */
	@Override
	public void normalize(Microarray microarray) {
		int blockRows = microarray.getBlockRows();
		int blockColumns = microarray.getBlockColumns();
		int featureRows = microarray.getFeatureRows();
		int featureColumns = microarray.getFeatureColumns();
		
		for(int i = 0; i < blockRows; i++)
			for(int j = 0; j < blockColumns; j++)
				for(int k = 0; k < featureRows; k ++)
					for(int h = 0; h < featureColumns; h++) {
						microarray.getBlock(i, j).getRecord(k, h).setNormalizedSignal(
								Math.log(microarray.getBlock(i, j).getRecord(k, h).getSignal()));
					}
						
	}
	
}