package classification;

import weka.classifiers.Evaluation;

public class Fold extends ClassificationResult{
	
	private String foldId;
	private String patientId;
	
	public Fold(Evaluation eval, String foldId, String loocId, String patientId) {
		super(loocId);
		this.foldId = foldId;
		this.patientId = patientId;
		this.eval = new MyEvaluation(eval);
		
	}
	
	@Override
	public String toInsertStatemnt() {
		String linefeed = System.getProperty("line.separator");
		String command = "INSERT INTO studyworks.fold (correct" +
				",incorrect" +
				",accuracy" +
				",avgRecall" +
				",avgPrecision" +
				",avgF1Score" +
				",kappa" +
				",meanAbsoluteError" +
				",rootMeanSquaredError" +
				",relativeAbsoluteError" +
				",rootRelativeSquaredError" +
				",loocId" +
				",foldId" +
				",testpatient)";
		command += "Values(" + eval.getCorrect() 
				+ "," + eval.getIncorrect() 
				+ "," + eval.getAccuracy() 
				+ "," + eval.getAvgF1score() 
				+ "," + eval.getAvgPrecision() 
				+ "," + eval.getAvgRecall() 
				+ "," + eval.getKappa() 
				+ "," + eval.getMeanAbsoluteError() 
				+ "," + eval.getRootMeanSquaredError() 
				+ "," + eval.getRelativeAbsoluteError() 
				+ "," + eval.getRootRelativeSquaredError() 
				+ ",'" + loocId + "'"
				+ ",'" + foldId  + "'"
				+ ",'" + patientId + "');" + linefeed;
		command += getInsertForMeasures(foldId, eval.getPrecision(), "precision") + linefeed;
		command += getInsertForMeasures(foldId, eval.getRecall(), "recall") + linefeed;
		command += getInsertForMeasures(foldId, eval.getF1Score(), "f1score") + linefeed;
		return command;
	}
	
	public String getFoldId() {
		return foldId;
	}
	
	@Override
	public String getMinimal() {
		return foldId + "\t" + eval.getAccuracy() + "\t" + eval.getAvgF1score() + "\t" + eval.getCorrect()
				+"\t" + eval.getIncorrect();
	}
}