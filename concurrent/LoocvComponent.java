package concurrent;

import java.util.List;

import classification.MyEvaluation;

/**
 * Super class for fold and looc.
 * @author Patrick
 *
 */
public abstract class LoocvComponent {
	protected String id;
	protected MyEvaluation eval;
	
	public LoocvComponent(String id) {
		this.id = id;
		eval = null;
	}
	
	/**
	 * Generate a SQL insert statement to persist results in database
	 * @return
	 */
	public abstract List<String> generateInsertStatement();
	
	public String getId() {
		return id;
	}
	
	/**
	 * Generates insert statements for measurements being arrays (precision, recall, ...)
	 * @param id	Either fold or LoocId
	 * @param arr	Attribute for which insert statements should be created
	 * @param table	Table in which values should be persisted
	 * @return		Returns Insert statement as String
	 */
	protected String getInsertForMeasures(String id, double[] arr, String table) {
		String command = "INSERT INTO studyworks." + table + "(eid,value) VALUES";
		for(double d : arr)
			command += "('" + id +"'," + d + "),";
		command = command.substring(0, command.length() - 1) + ";";
		return command;
	}
	
	/**
	 * Generates insert statements for measurements being arrays (truepositive, truenegative, ...)
	 * @param id	Either fold or LoocId
	 * @param arr	Attribute for which insert statements should be created
	 * @param table	Table in which values should be persisted
	 * @return		Returns Insert statement as String
	 */
	protected String getInsertForConfusionMatrix(String id, double[] arr, String table) {
		String command = "INSERT INTO studyworks." + table + "(eid,value,disease) VALUES";
		for(int i = 0; i < arr.length; i++)
			command += "('" + id +"'," + arr[i] + ",'" + diseaseFromIndex(i) + "'),";
		command = command.substring(0, command.length() - 1) + ";";
		return command;
	}
	
	protected String diseaseFromIndex(int index) {
		switch(index) {
		case 0: return "AD";
		case 1: return "BC";
		case 2: return "MS";
		case 3: return "NDC";
		case 4: return "PD";
		default: return "NA";
		}
	}
	
	protected double mean(double[] values) {
		double ret = 0;
		for(int i = 0; i < values.length; i++)
			ret += values[i];
		return ret/values.length;
	}
	
	public MyEvaluation getMyEvaluation() { return eval; }
}