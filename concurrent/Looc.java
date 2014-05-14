package concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one classification useing Leave One Out Cross Validation. This class holds all the
 * folds and summarizes their results by taking the mean of each measure. It also holds the
 * classifier in form of its classstring and the options which should be applied to the classifier
 * @author Patrick
 *
 */
public class Looc extends LoocvComponent{
	private boolean antibodiesSet;
	/**
	 * Holds the folds loocv consists of
	 */
	private ArrayList<ConcurrentFold> folds;
	/**
	 * Options with which classifier model should be build
	 */
	private String[] classifierOptions;
	/**
	 * Holds the class of the string, e.g. weka.classifier.BayesNet
	 */
	private String classifier;
	
	private String options;
	private double infoGain;
	private int features;
	private boolean discretize;
	private int numFolds;
	
	public Looc(String loocId, String classifier, String[] options) {
		super(loocId);
		folds = new ArrayList<ConcurrentFold>();
		numFolds = 0;
		classifierOptions = options;
		this.classifier = classifier;
		if(options == null)
			this.options = "standard options";
		else
			this.options = getOptionString();
		this.discretize = false;
		antibodiesSet = false;
		
	}	
	@Override
	public List<String> generateInsertStatement() {
		List<String> commands = new ArrayList<String>();
		String command = "INSERT INTO studyworks.looc (correct" +
				",incorrect" +
				",accuracy" +
				",avgRecall" +
				",avgPrecision," +
				"avgF1Score" +
				",kappa" +
				",meanAbsoluteError" +
				",rootMeanSquaredError" +
				",relativeAbsoluteError" +
				",rootRelativeSquaredError" +
				",loocId" +
				",classifier" +
				",classifierOptions" +
				",infoGain" +
				",featuresSelected) ";
		command += "Values(" + eval.getCorrect()
				+ "," + eval.getIncorrect() 
				+ "," + eval.getAccuracy() 
				+ "," + eval.getAvgRecall() 
				+ "," + eval.getAvgPrecision() 
				+ "," + eval.getAvgF1score() 
				+ "," + eval.getKappa() 
				+ "," + eval.getMeanAbsoluteError() 
				+ "," + eval.getRootMeanSquaredError() 
				+ "," + eval.getRelativeAbsoluteError() 
				+ "," + eval.getRootRelativeSquaredError() 
				+ ",'" + id +"'"
				+ ",'" + classifier +"'"
				+ ",'" + options 
				+ "'," + infoGain
				+ "," + features + ")";
		commands.add(command);
		command = "";
		commands.add(getInsertForMeasures(id, eval.getPrecision(), "precision"));
		commands.add(getInsertForMeasures(id, eval.getRecall(), "recall"));
		commands.add(getInsertForMeasures(id, eval.getF1Score(), "f1score"));
		commands.add(getInsertForMeasures(id, eval.getAreaUnderRoc(), "areaunderroc"));
		commands.add(getInsertForConfusionMatrix(id, eval.getFalsepositive(), "falsepositive"));
		commands.add(getInsertForConfusionMatrix(id, eval.getFalsenegative(), "falsenegative"));
		commands.add(getInsertForConfusionMatrix(id, eval.getTruepositive(), "truepositive"));
		commands.add(getInsertForConfusionMatrix(id, eval.getTrueNegatives(), "truenegative"));

		for(ConcurrentFold f : folds) {
			commands.addAll(f.generateInsertStatement());
			if(antibodiesSet)
				commands.add(f.generateInsertStatementAntibodies());
		}
		return commands;
	}
		
	/**
	 * Returns the options as String array
	 * @return	String array containing classifier options
	 */
	public synchronized String[] getOptions() {
		if(classifierOptions != null)
			return classifierOptions.clone();
		else
			return null;
	}
	
	/**
	 * Adds a fold to the set of folds
	 * @param fold
	 */
	public synchronized void addFold(ConcurrentFold fold) {
		numFolds++;
		folds.add(fold);
	}
	
	/**
	 * Returns the string representing the classifier
	 * @return
	 */
	public synchronized String getClassifier() {
		return classifier;
	}
	
	/**
	 * Returns the options as one string
	 * @return
	 */
	public synchronized String getOptionString() {
		String ret = "";
		for(String s : classifierOptions) {
			ret += s + " ";
		}
		return ret.trim();
	}
	
	public synchronized int getFoldNumber() {
		return this.numFolds;
	}

	public double getInfoGain() {
		return infoGain;
	}
	public void setInfoGain(double infoGain) {
		this.infoGain = infoGain;
	}
	public int getFeatures() {
		return features;
	}
	public void setFeatures(int features) {
		this.features = features;
	}
	/**
	 * Consolidates results from all folds into one result set by taking the mean of performance
	 * measures. incorrect and correct values are simply added
	 */
	public void consolidateFolds(int numClasses) {
		eval = new MyConcurrentEvaluation(folds, numClasses);
	}
	
	@Override
	public String toString() {
		return eval.getCorrect()
				+ "\t" + eval.getIncorrect() 
				+ "\t" + eval.getAccuracy() 
				+ "\t" + eval.getAvgRecall() 
				+ "\t" + eval.getAvgPrecision() 
				+ "\t" + eval.getAvgF1score() 
				+ "\t" + eval.getKappa() 
				+ "\t" + eval.getMeanAbsoluteError() 
				+ "\t" + eval.getRootMeanSquaredError() 
				+ "\t" + eval.getRelativeAbsoluteError() 
				+ "\t" + eval.getRootRelativeSquaredError() 
				+ "\t'" + id +"'"
				+ "\t'" + classifier +"'"
				+ "\t'" + options 
				+ "'\t" + infoGain
				+ "\t" + features;
	}
	public synchronized boolean isDiscretize() {
		return discretize;
	}
	public void setDiscretize(boolean discretize) {
		this.discretize = discretize;
	}

	public boolean isAntibodiesSet() {
		return antibodiesSet;
	}

	public void setAntibodiesSet(boolean antibodiesSet) {
		this.antibodiesSet = antibodiesSet;
	}
	
}