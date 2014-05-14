package classification;

import java.util.ArrayList;

import weka.core.Instances;
import weka.classifiers.Classifier;

/**
 * This class represents a result from an classification result
 * @author Patrick
 *
 */
public class ClassificationResult implements Comparable<MyEvaluation>{
	
	private String[] antibodyIds;
	private String[] classifierOptions;
	private String classifier;
	private ArrayList<Fold> folds;
	protected MyEvaluation eval;
	protected String loocId;
	
	//	  a  b  c  d  e   <-- classified as 
	// a 42  1  0  1  1 |  a = AD
	// b  1 41  0  2  4 |  b = BC
	// c  0  3  8  2  1 |  c = MS
	// d  6  0  0 64 10 |  d = NDC
	// e  0  1  0  4 62 |  e = PD
	public ClassificationResult(Classifier cf, String loocId) {
		antibodyIds = null;
		folds = new ArrayList<Fold>();
		classifierOptions = cf.getOptions();
		classifier = cf.toString();
		eval = null;
		this.loocId = loocId;
	}
	
	protected ClassificationResult(String loocId) {
		this.loocId = loocId;
	}
	
	protected double mean(double[] values) {
		double ret = 0;
		for(int i = 0; i < values.length; i++)
			ret += values[i];
		return ret/values.length;
	}

	/**
	 * Returns the antibody ids used in classification for this result
	 * @return	string array containing antibody Ids
	 */
	public String[] getAttributeNames() {
		return antibodyIds;
				
	}
	
	public String toInsertStatemnt() {
		String newline = System.getProperty("line.separator");
		String command = "INSERT INTO studyworks.looc (correct" +
				",incorrect" +
				",accuracy" +
				",avgRecall" +
				",avgPrecision," +
				"avgF1Score" +
				",kappa,meanAbsoluteError" +
				",rootMeanSquaredError" +
				",relativeAbsoluteError" +
				",rootRelativeSquaredError" +
				",loocId" +
				",classifier" +
				",classifierOptions) ";
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
				+ ",'" + loocId +"'"
				+ ",'" + classifier +"'"
				+ ",'" + classifierOptions + "');" + newline;
		command += getInsertForMeasures(loocId, eval.getPrecision(), "precision") + newline;
		command += getInsertForMeasures(loocId, eval.getRecall(), "recall") + newline;
		command += getInsertForMeasures(loocId, eval.getF1Score(), "f1score") + newline;
//		for(Fold f : folds)
//			command += f.toInsertStatemnt() + newline;
//		TODO get things with antibody ids right
//		command += "INSERT INTO studyworks.antibody (loocId,antibodyDbId) VALUES";
//		for(String s : antibodyIds)
//			command += "('" + loocId + "','" + s + "'),";
//		command = command.substring(0, command.length() - 2);
		return command;
	}
	
	protected String getInsertForMeasures(String id, double[] arr, String table) {
		String command = "INSERT INTO studyworks." + table + "(eid,value) VALUES";
		for(double d : arr)
			command += "('" + id +"'," + d + "),";
		command = command.substring(0, command.length() - 1) + ";";
		return command;
	}

	public String[] getClassifierOptions() {
		return classifierOptions;
	}

	public String getClassifier() {
		return classifier;
	}

	public ArrayList<Fold> getFolds() {
		return folds;
	}

	public String getLoocId() {
		return loocId;
	}
	
	public MyEvaluation getMyEvaluation() {
		return eval;
	}
	
	@Override
	public int compareTo(MyEvaluation eval) {
		return this.eval.compareTo(eval.getAvgF1score());
	}

	public void finalizeLooc(int classes) {
		eval = new MyEvaluation(folds, classes);
	}
	
	public void addFold(Fold fold) {
		folds.add(fold);
	}

	public void setInstances(Instances instcs) {
		antibodyIds = new String[instcs.numAttributes() - 1];
		int classSeen = 0;
		for(int i = 0; i < instcs.numAttributes() - 1; i++)
			if(instcs.attribute(i).name().equalsIgnoreCase("disease"))
				classSeen++;
			else
				antibodyIds[i] = instcs.attribute(i + classSeen).name();
	}
	
	public String getMinimal() {
		return loocId + "\t" + eval.getAccuracy() + "\t" + eval.getAvgF1score() + "\t" + eval.getCorrect()
				+"\t" + eval.getIncorrect() + "\t" + classifier;
	}
	
}