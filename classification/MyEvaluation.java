package classification;

import java.util.ArrayList;

import weka.classifiers.Evaluation;

public class MyEvaluation implements Comparable<Double>{
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
	private double[] areaUnderRoc;
	
	public MyEvaluation() {
		
	}
	
	public MyEvaluation(Evaluation eval) {
		recall = new double[eval.confusionMatrix().length];
		precision = new double[eval.confusionMatrix().length];
		f1score = new double[eval.confusionMatrix().length];
		truepositive = new double[eval.confusionMatrix().length];
		falsenegative = new double[eval.confusionMatrix().length];
		falsepositive = new double[eval.confusionMatrix().length];
		areaUnderRoc = new double[eval.confusionMatrix().length];
		correct = 0;
		incorrect = 0;
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
			areaUnderRoc[i] = eval.areaUnderROC(i);
		}
		
		avgRecall = mean(recall);
		avgPrecision = mean(precision);
		avgF1score = mean(f1score);
		accuracy = (double)correct/(double)(correct + incorrect);
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
	}
	
	public MyEvaluation(ArrayList<Fold> folds) {
		MyEvaluation eval;
		for(Fold f : folds) {
			eval = f.getMyEvaluation();
			for(int i = 0; i < precision.length; i++) {
				precision[i] += eval.getClassPrecision(i);
				f1score[i] += eval.getClassF1Score(i);
				recall[i] += eval.getClassRecall(i);
				falsenegative[i] += eval.getClassFalseNegative(i);
				falsepositive[i] += eval.getClassFalsePositive(i);
				truepositive[i] += eval.getClassTruePositive(i);
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
			correct += eval.getCorrect();
			incorrect += eval.getIncorrect();
		}
		avgPrecision /= (double)folds.size();
		avgRecall /= (double)folds.size();
		avgF1score /= (double)folds.size();
		kappa /= (double)folds.size();
		meanAbsoluteError /= (double)folds.size();
		relativeAbsoluteError /= (double)folds.size();
		rootMeanSquaredError /= (double)folds.size();
		rootRelativeSquaredError /= (double)folds.size();
		
		for(int i = 0; i < precision.length; i++) {
			precision[i] /= (double)folds.size();
			f1score[i] /= (double)folds.size();
			recall[i] /= (double)folds.size();
			falsenegative[i] /= (double)folds.size();
			falsepositive[i] /= (double)folds.size();
			truepositive[i] /= (double)folds.size();
			areaUnderRoc[i] /= (double)folds.size();
		}
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