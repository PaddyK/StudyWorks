package concurrent;

import java.util.List;

import weka.classifiers.Evaluation;

public class MyConcurrentEvaluation implements Comparable<Double>{
	private int correct;
	private int incorrect;
	private double accuracy;
	private double avgRecall;
	private double avgPrecision;
	private double avgF1score;
	private double kappa;
	private double meanAbsoluteError;
	private double rootMeanSquaredError;
	private double relativeAbsoluteError;
	private double rootRelativeSquaredError;
	private double[] recall;
	private double[] precision;
	private double[] f1score;
	private double[] truepositive;
	private double[] falsepositive;
	private double[] falsenegative;
	private double[] truenegative;
	private double[] areaUnderRoc;
	
	private void checkNaN() {
		if(Double.isNaN(meanAbsoluteError))
			meanAbsoluteError = -1;
		
		if(Double.isNaN(relativeAbsoluteError))
			relativeAbsoluteError = -1;
		
		if(Double.isNaN(rootMeanSquaredError))
			rootMeanSquaredError = -1;
		
		if(Double.isNaN(rootRelativeSquaredError))
			rootRelativeSquaredError = -1;
		
		if(Double.isNaN(accuracy))
			accuracy = -1;
		
		if(Double.isNaN(avgRecall))
			avgRecall = -1;
		
		if(Double.isNaN(avgPrecision))
			avgPrecision = -1;
		
		if(Double.isNaN(avgF1score))
			avgF1score = -1;
		
		if(Double.isNaN(kappa))
			kappa = -1;
		
		for(int i=0; i < recall.length; i++) {
			if(Double.isNaN(recall[i]))
				recall[i] = -1;

			if(Double.isNaN(precision[i]))
				precision[i] = -1;

			if(Double.isNaN(f1score[i]))
				f1score[i] = -1;

			if(Double.isNaN(truepositive[i]))
				truepositive[i] = -1;

			if(Double.isNaN(falsepositive[i]))
				falsepositive[i] = -1;

			if(Double.isNaN(falsenegative[i]))
				falsenegative[i] = -1;

			if(Double.isNaN(truenegative[i]))
				truenegative[i] = -1;

			if(Double.isNaN(areaUnderRoc[i]))
				areaUnderRoc[i] = -1;
		}
	}
	
	public MyConcurrentEvaluation() {
		
	}
	
	public MyConcurrentEvaluation(Evaluation eval) {
		recall = new double[eval.confusionMatrix().length];
		precision = new double[eval.confusionMatrix().length];
		f1score = new double[eval.confusionMatrix().length];
		truepositive = new double[eval.confusionMatrix().length];
		falsenegative = new double[eval.confusionMatrix().length];
		falsepositive = new double[eval.confusionMatrix().length];
		truenegative = new double[eval.confusionMatrix().length];
		areaUnderRoc = new double[eval.confusionMatrix().length];
		correct = (int)eval.correct();
		incorrect = (int)eval.incorrect();
		avgRecall = 0;
		avgPrecision = 0;
		avgF1score = 0;

		for(int i = 0; i < recall.length; i++) {
			recall[i] = eval.recall(i);
			precision[i] = eval.precision(i);
			f1score[i] = eval.fMeasure(i);
			truepositive[i] = eval.truePositiveRate(i);
			falsepositive[i] = eval.falsePositiveRate(i);
			falsenegative[i] = eval.falseNegativeRate(i);
			truenegative[i] = eval.trueNegativeRate(i);
			areaUnderRoc[i] = eval.areaUnderROC(i);
		}
		
		avgRecall = mean(recall);
		avgPrecision = mean(precision);
		avgF1score = mean(f1score);
		accuracy = (double)correct/((double)(correct + incorrect));
		kappa = eval.kappa();
		meanAbsoluteError = eval.meanAbsoluteError();
		rootMeanSquaredError = eval.rootMeanSquaredError();
		rootRelativeSquaredError = eval.rootRelativeSquaredError();
		
		try {
			relativeAbsoluteError = eval.relativeAbsoluteError();
		}
		catch(Exception e) {
			e.printStackTrace();
			relativeAbsoluteError = -1;
		}
		checkNaN();
	}
		
	public MyConcurrentEvaluation(List<ConcurrentFold> folds, int classes) {

		recall = new double[classes];
		precision = new double[classes];
		f1score = new double[classes];
		truepositive = new double[classes];
		falsepositive = new double[classes];
		falsenegative = new double[classes];
		areaUnderRoc = new double[classes];
		truenegative = new double[classes];
		
		MyConcurrentEvaluation eval;
		for(ConcurrentFold f : folds) {
			eval = f.getMyConcurrentEvaluation();
			for(int i = 0; i < 5; i++) {
				precision[i] += eval.getClassPrecision(i);
				f1score[i] += eval.getClassF1Score(i);
				recall[i] += eval.getClassRecall(i);
				falsenegative[i] += eval.getClassFalseNegative(i);
				falsepositive[i] += eval.getClassFalsePositive(i);
				truepositive[i] += eval.getClassTruePositive(i);
				truenegative[i] = eval.getClassTrueNegative(i);
				areaUnderRoc[i] += eval.getClassAreaUnderROC(i);
			}
			avgPrecision += eval.getAvgPrecision();
			avgRecall += eval.getAvgRecall();
			avgF1score += eval.getAvgF1score();
			kappa += eval.getKappa();
			meanAbsoluteError += eval.getMeanAbsoluteError();
			relativeAbsoluteError += eval.getRelativeAbsoluteError();
			rootMeanSquaredError += eval.getRootMeanSquaredError();
			rootRelativeSquaredError += eval.getRootRelativeSquaredError();
			correct += (int)eval.getCorrect();
			incorrect += (int)eval.getIncorrect();
		}
		accuracy = (double)(correct)/((double)(correct + incorrect));
		avgPrecision /= (double)folds.size();
		avgRecall /= (double)folds.size();
		avgF1score /= (double)folds.size();
		kappa /= (double)folds.size();
		meanAbsoluteError /= (double)folds.size();
		relativeAbsoluteError /= (double)folds.size();
		rootMeanSquaredError /= (double)folds.size();
		rootRelativeSquaredError /= (double)folds.size();
		
		for(int i = 0; i < 5; i++) {
			precision[i] /= (double)folds.size();
			f1score[i] /= (double)folds.size();
			recall[i] /= (double)folds.size();
			falsenegative[i] /= (double)folds.size();
			falsepositive[i] /= (double)folds.size();
			truepositive[i] /= (double)folds.size();
			areaUnderRoc[i] /= (double)folds.size();
		}
		checkNaN();
	}
	
	private double mean(double[] values) {
		double ret = 0;
		for(int i = 0; i < values.length; i++)
			ret += values[i];
		return ret/(double)values.length;
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
	
	public double getClassRecall(int classIndex) {
		return recall[classIndex];
	}
	
	public double getClassPrecision(int classIndex) {
		return precision[classIndex];
	}

	public double getClassF1Score(int classIndex) {
		return f1score[classIndex];
	}

	public double getClassFalsePositive(int classIndex) {
		return falsepositive[classIndex];
	}

	public double getClassTruePositive(int classIndex) {
		return truepositive[classIndex];
	}

	public double getClassFalseNegative(int classIndex) {
		return falsenegative[classIndex];
	}
	
	public double getClassTrueNegative(int classIndex) {
		return truenegative[classIndex];
	}

	public double getClassAreaUnderROC(int classIndex) {
		return areaUnderRoc[classIndex];
	}
	
	public double getKappa() {
		return kappa;
	}

	public double getMeanAbsoluteError() {
		return meanAbsoluteError;
	}

	public double getRootMeanSquaredError() {
		return rootMeanSquaredError;
	}

	public double getRelativeAbsoluteError() {
		return relativeAbsoluteError;
	}

	public double getRootRelativeSquaredError() {
		return rootRelativeSquaredError;
	}

	public double[] getTruepositive() {
		return truepositive;
	}
	
	public double[] getFalsenegative() {
		return falsenegative;
	}
	
	public double[] getFalsepositive() {
		return falsepositive;
	}
	
	public double[] getTrueNegatives() {
		return truenegative;
	}

	public double[] getAreaUnderRoc() {
		return areaUnderRoc;
	}
	
	public double[] getPrecision() {
		return precision;
	}
	
	public double[] getRecall() {
		return recall;
	}
	
	public double[] getF1Score() {
		return f1score;
	}
}