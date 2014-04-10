package classification;

import weka.classifiers.Evaluation;

public class Fold extends ClassificationResult{
	
	private String foldId;
	private String patientId;
	
	public Fold(Evaluation eval, String foldId, String loocId, String patientId) {
		super(loocId);
		this.foldId = foldId;
		this.patientId = patientId;
	}
	
	@Override
	public String toInsertStatemnt() {
		String command = "INSERT INTO fold (correct" +
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
				+ "," + loocId 
				+ "," + foldId 
				+ "," + patientId + ");";
		command += getInsertForMeasures(foldId, eval.getPrecision(), "precision");
		command += getInsertForMeasures(foldId, eval.getRecall(), "recall");
		command += getInsertForMeasures(foldId, eval.getF1Score(), "f1score");
		return command;
	}
	
	public String getFoldId() {
		return foldId;
	}
}