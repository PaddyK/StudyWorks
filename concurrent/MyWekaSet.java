package concurrent;

import weka.core.Instances;

public class MyWekaSet {
	private Instances testset;
	private Instances trainingset;
	private String patientId;
	
	public MyWekaSet(){}
	
	/**
	 * 
	 * @param test	Instances for test set
	 * @param train	Instances for training set
	 */
	public MyWekaSet(Instances test, Instances train) {
		testset = test;
		trainingset = train;
		this.patientId = test.relationName().substring(0, test.relationName().indexOf('_'));
	}
	
	public Instances train() { return trainingset; }
	public Instances test() { return testset;}
	public void setTest(Instances test) { testset = test; }
	public void setTrain(Instances train) {trainingset = train; }
	public String getPatientId() { return patientId; }
}