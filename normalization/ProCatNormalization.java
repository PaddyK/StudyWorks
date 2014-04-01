package normalization;
import java.util.List;
import java.util.ArrayList;

import data.Microarray;

public class ProCatNormalization implements Normalization {
	
	private int k;
	
	public ProCatNormalization(int blocksize, int windowsize) {
		k = (int) Math.round((double)blocksize * (double)windowsize / (double)10);
	}
	
	@Override
	public void normalize(Microarray microarray) {
		signalNormalization(microarray);
		
	}
	
	private void signalNormalization(Microarray array) {
		int[] vector;
		int idx1, idx2;
		
		double avgMedian;
		double avgMad;
		double border;
		int blockrows = array.getBlockRows();
		int blockcolumns = array.getBlockColumns();
		int featurerows = array.getFeatureRows();
		int featurecolumns = array.getFeatureColumns();
		double[][] medians = new double[blockrows * featurerows][blockcolumns * featurecolumns];
		double[][] mads = new double[blockrows * featurerows][blockcolumns * featurecolumns];
		List<Double> signals = new ArrayList<Double>();
		
		for(int i = 0; i < blockrows; i++) {
			for(int j = 0; j < blockcolumns; j++) {
				for(int m = 0; m < featurerows; m++) {
					for(int r = 0; r < featurecolumns; r++) {
						for(int n = max(0, m - k); n < min(featurerows, m + k); n++) {
							for(int p = max(0, r - k); p < min(featurecolumns, r + k); p++) {
								vector = boundary(i, j, m, r, n, p, blockrows, blockcolumns, featurerows, featurecolumns);
								signals.add((double)array.getBlock(vector[0], vector[1]).getRecord(vector[2], vector[3]).getSignal());
							}
						}
						idx1 = i * blockrows + m;
						idx2 = j * blockcolumns + r;
						medians[idx1][idx2] = calculateMedian(signals);
						for(int q = 0; q < signals.size(); q++)
							signals.set(q, signals.get(q) - medians[idx1][idx2]);
						mads[idx1][idx2] = calculateMedian(signals);
						signals.clear();
					}
				}
			}
		}
		avgMedian = calculateMean(medians);
		avgMad = calculateMean(mads);
		
		for(int i = 0; i < blockrows; i++) {
			for(int j = 0; j < blockcolumns; j++) {
				for(int m = 0; m < featurerows; m++) {
					for(int r = 0; r < featurecolumns; r++) {
						idx1 = i * blockrows + m;
						idx2 = j * blockcolumns + r;
						array.getBlock(i, j).getRecord(m, r).setNormalizedSignal(
								(array.getBlock(i, j).getRecord(m, r).getNormalizedSignal() - medians[idx1][idx2]) 
								* avgMad / mads[idx1][idx2] + avgMedian
						);
					}
				}
			}
		}
	}
	
	private int max(int a, int b) {
		if(a > b)
			return a;
		else
			return b;
	}
	
	private int min(int a, int b) {
		if(a < b)
			return a;
		else
			return b;
	}
	
	private double calculateMean(double[][] values) {
		long mean = 0;
		for(int i=0; i < values.length; i++)
			for(int j = 0; j < values[i].length; j++)
				mean += values[i][j];
		return (double)mean/(double)(values.length * values[0].length);
	}
	
	/**
	 * Performs the background normalization by applying neighborhood background correction
	 * as described for proCAT. A three by three window is defined. The central background value is 
	 * replaced by the median value of those nine values.
	 * a11 a12 a13
	 * a21 a22 a23  --> a22 = median(a11:a33)
	 * a31 a32 a33
	 * @param microarray
	 */
	private void backgroundNormalization(Microarray microarray) {
		double[] window = new double[9];
		int[] koordinates = new int[4];
		for(int i = 0; i < microarray.getBlockRows(); i++) {
			for(int j = 0; j < microarray.getBlockColumns(); j++) {
				for(int h = 0; h < microarray.getFeatureRows(); h++) {
					for(int k = 0; k < microarray.getFeatureColumns(); k++) {
						for(int l = -1; l <= 1; l++) {
							for(int m = -1; m <= 1; m++) {
								koordinates = boundary(i,j,h,k,l,m,microarray.getBlockRows(),
										microarray.getBlockColumns(), microarray.getFeatureRows(),
										microarray.getFeatureColumns());
								
								window[m+1 + (l+1)*3] = microarray.getBlock(koordinates[0], koordinates[1])
										.getRecord(koordinates[2], koordinates[3]).getNormalizedSignal();
							}
						}
						// Update background with median value
						microarray.getBlock(i, j).getRecord(h, k).setBackground((int)Math.round(calculateMedian(window)));
					}
				}
			}
		}
								
	}
	/**
	 * For the usage of windows the microarray becomes a globe, meaning a window can include values
	 * from up to three blocks.
	 * @param brow
	 * @param bcol
	 * @param frow
	 * @param fcol
	 * @param incrow		-1,0,1
	 * @param inccol		-1,0,1
	 * @param btotalRows	12
	 * @param btotalColumns	4
	 * @param ftotalRows	22
	 * @param ftotalColumns	22
	 * @return
	 */
	private int[] boundary(int brow, int bcol, int frow, int fcol, int incrow, int inccol,
			int btotalRows, int btotalColumns, int ftotalRows, int ftotalColumns) {
		int koordinates[] = new int[4];
		koordinates[0] = brow;
		koordinates[1] = bcol;
		koordinates[2] = frow;
		koordinates[3] = fcol;
		
		if(frow + incrow > ftotalRows - 1) {
			// Example: frow = 20, incrow = 5 --> 20 + 5 - 21 = 4
			if(brow + 1 > btotalRows - 1)
				koordinates[0] = 0;
			else
				koordinates[0] = brow + 1;
			koordinates[2] = frow + incrow - ftotalRows;
		}
		else if(frow + incrow < 0) {
			// Example: frow = 2, incrow = -5 --> 2 + (-5) + 21 = -3 + 21 = 18
			if(brow - 1 < 0)
				koordinates[0] = 11;
			else
				koordinates[0] = brow - 1;
			koordinates[2] = frow + incrow + ftotalRows;
		}
		else {
			koordinates[2] = frow + incrow;
		}
		
		if(fcol + inccol > ftotalColumns - 1) {
			if(bcol + 1 > btotalColumns - 1)
				koordinates[1] = 0;
			else
				koordinates[1] = bcol + 1;
			koordinates[3] = fcol + inccol - ftotalColumns;
		}
		else if(fcol + inccol < 0) {
			if(bcol - 1 < 0)
				koordinates[1] = 3;
			else
				koordinates[1] = bcol - 1;
			koordinates[3] = fcol + inccol + ftotalColumns;
		}
		else {
			koordinates[3] = fcol + inccol;
		}
		return koordinates;
	}
	
	/**
	 * Calculates the median for a range of values
	 * @param values	Sequence of numbers
	 * @return			median
	 */
	private double calculateMedian(double[] values) {
		double tmp;
		double median;
		// Bubblesort, order values
		for(int i = 0; i < values.length; i++)
			for(int j = 0; j < values.length - 1 - i; j++) {
				if(values[j] > values[j+1]) {
					tmp = values[j];
					values[j] = values[j+1];
					values[j+1] = tmp;
				}
			}
		// If values has a even number of elements take the median of the two in the middle.
		// else calculate the middel one, (5 - 1) = 2 --> 0 1 [2] 3 4
		if(values.length % 2 == 0) {
			int idx = (int)(values.length / 2);
			median = (double)(values[idx] + values[idx - 1]) / (double)2;
		}
		else {
			int idx = (int)((values.length - 1) / 2);
			median = values[idx];
		}
		return median;
	}
	
	 /** Calculates the median for a range of values
	 * @param values	Sequence of numbers
	 * @return			median
	 */
	private double calculateMedian(List<Double> values) {
		double tmp;
		double median;
		// Bubblesort, order values
		for(int i = 0; i < values.size(); i++)
			for(int j = 0; j < values.size() - 1 - i; j++) {
				if(values.get(j) > values.get(j+1)) {
					tmp = values.get(j);
					values.set(j, values.get(j+1));
					values.set(j+1, tmp);
				}
			}
		// If values has a even number of elements take the median of the two in the middle.
		// else calculate the middel one, (5 - 1) = 2 --> 0 1 [2] 3 4
		if(values.size() % 2 == 0) {
			int idx = (int)(values.size() / 2);
			median = (double)(values.get(idx) + values.get(idx - 1)) / (double)2;
		}
		else {
			int idx = (int)((values.size() - 1) / 2);
			median = values.get(idx);
		}
		return median;
	}
}