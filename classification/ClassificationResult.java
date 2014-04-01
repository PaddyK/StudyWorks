package classification;

import weka.core.Instances;

public class ClassificationResult implements Comparable<Double>{
	private int correct;
	private int incorrect;
	private double accuracy;
	private double avgRecall;
	private double avgPrecision;
	private double avgF1score;
	private double[] recall;
	private double[] precision;
	private double[] f1score;
	private double[] truepositive;
	private double[] falsepositive;
	private double[] falsenegative;
	Instances instances;
	
	//	  a  b  c  d  e   <-- classified as 
	// a 42  1  0  1  1 |  a = PD
	// b  1 41  0  2  4 |  b = BC
	// c  0  3  8  2  1 |  c = MS
	// d  6  0  0 64 10 |  d = AD
	// e  0  1  0  4 62 |  e = NDC
	public ClassificationResult(double[][] confusionMatrix, Instances instcs) {
		instances = instcs;
		recall = new double[confusionMatrix.length];
		precision = new double[confusionMatrix.length];
		f1score = new double[confusionMatrix.length];
		truepositive = new double[confusionMatrix.length];
		falsenegative = new double[confusionMatrix.length];
		falsepositive = new double[confusionMatrix.length];
		correct = 0;
		incorrect = 0;
		avgRecall = 0;
		avgPrecision = 0;
		avgF1score = 0;
		
		for(int i = 0; i < recall.length; i++) {
			recall[i] = 0;
			precision[i] = 0;
			f1score[i] = 0;
			truepositive[i] = 0;
			falsepositive[i] = 0;
			falsenegative[i] = 0;
		}
		
		for(int i = 0; i < confusionMatrix.length; i++)
			for(int j = 0; j < confusionMatrix.length; j++)
				if(i == j) {
					truepositive[i] += confusionMatrix[i][j];
					correct += confusionMatrix[i][j];
				}
				else {
					falsepositive[i] += confusionMatrix[i][j];
					falsenegative[j] += confusionMatrix[i][j];
					incorrect += confusionMatrix[i][j];
				}
		
		for(int i = 0; i < recall.length; i++) {
			precision[i] = truepositive[i] / (truepositive[i] + falsepositive[i]);
			recall[i] = truepositive[i] / (truepositive[i] + falsenegative[i]);
			f1score[i] = 2 * precision[i] * recall[i] / (precision[i] + recall[i]);
		}
		avgRecall = mean(recall);
		avgPrecision = mean(precision);
		avgF1score = mean(f1score);
		accuracy = (double)correct/(double)(correct + incorrect);
		
	}
	
	private double mean(double[] values) {
		double ret = 0;
		for(int i = 0; i < values.length; i++)
			ret += values[i];
		return ret/values.length;
	}

	@Override
	public int compareTo(Double avgf1) {
		if(avgF1score < avgf1)
			return -1;
		else if(avgF1score == avgf1)
			return 0;
		else
			return 1;
	}

	public double getAvgRecall() {
		return avgRecall;
	}

	public double getAvgPrecision() {
		return avgPrecision;
	}

	public double getAvgF1score() {
		return avgF1score;
	}
	public double getAccuracy() {
		return accuracy;
	}

	public int getCorrect() {
		return correct;
	}

	public int getIncorrect() {
		return incorrect;
	}
	
	/**
	 * Returns the antibody ids used in classification for this result
	 * @return	string array containing antibody Ids
	 */
	public String[] getAttributeNames() {
		String[] names = new String[instances.numAttributes() - 1];
		for(int i = 0; i < instances.numAttributes(); i++)
			if(instances.attribute(i).isNominal())
				i++; // Skip class attribute
			else
				names[i] = instances.attribute(i).name();
		return names;
				
	}
}