package concurrent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import classification.MyEvaluation;

import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 * Represents one fold of a leave one out cross validation
 * @author Patrick
 *
 */
public class ConcurrentFold extends LoocvComponent {
	
	private String loocId;
	private String patientId;				// Id of the patient used for testing
	private LinkedList<String> antibodies;	// List of antibodies used in this fold. For each fold
											// parameter selection is done separatly because else
											// parameter selection would have be done on the testset
											// as well, as at the beginning records for testing are
											// in the training set
	
	public ConcurrentFold(String foldId, String loocId, String patientId, Evaluation eval) {
		super(foldId);
		this.patientId = patientId;
		antibodies = new LinkedList<String>();
		this.eval = new MyEvaluation(eval);
		this.loocId = loocId;
	}
	
	/**
	 * Reads attributes from instances and fills the list of antibodies
	 * @param instances
	 */
	public void setAntibodies(Instances instances) {
		int classSeen = 0;
		for(int i = 0; i < instances.numAttributes() - 1; i++)
			if(instances.attribute(i).name().equalsIgnoreCase("disease"))
				classSeen++;
			else
				antibodies.add(instances.attribute(i + classSeen).name());
	}

	@Override
 	public List<String> generateInsertStatement() {
		List<String> commands = new ArrayList<String>();
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
				+ ",'" + id  + "'"
				+ ",'" + patientId + "')";
		commands.add(command);
		command = "";
		command += "INSERT INTO studyworks.antibody (foldId,antibodyDbId) VALUES";
		for(String s : antibodies)
			command += "('" + id + "','" + s + "'),";
		commands.add(command.substring(0, command.length() - 1));
		commands.add(getInsertForMeasures(id, eval.getPrecision(), "precision"));
		commands.add(getInsertForMeasures(id, eval.getRecall(), "recall"));
		commands.add(getInsertForMeasures(id, eval.getF1Score(), "f1score"));
		commands.add(getInsertForMeasures(id, eval.getAreaUnderRoc(), "areaunderroc"));
		commands.add(getInsertForConfusionMatrix(id, eval.getFalsepositive(), "falsepositive"));
		commands.add(getInsertForConfusionMatrix(id, eval.getFalsenegative(), "falsenegative"));
		commands.add(getInsertForConfusionMatrix(id, eval.getTruepositive(), "truepositive"));
		commands.add(getInsertForConfusionMatrix(id, eval.getTrueNegatives(), "truenegative"));
		return commands;
	}
}