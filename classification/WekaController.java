package classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.converters.CSVLoader;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

public class WekaController {
	/***
	 * Contains feature instances to experiment with
	 */
	private Instances instances;
	
	/***
	 * Read weka instances from a csvFile into object variable. Assumes first attribute in csv
	 * file is patientId which will be removed and second attribute to be the class label (yes/no)
	 * This depends on functionality in DataController method writeRawDataMiniToFile
	 * {@link}DataController.java
	 * @param csvFile path to the csv file to read from
	 */
	public void readInstancesFromCSV(String csvFile) {
		CSVLoader loader = new CSVLoader();
		File src = new File(csvFile);
		Remove remove = new Remove();
		Instances tmp;
		
		if(!src.exists())
			System.err.println("CSV File " + csvFile + " to load data from does not exist!");
		else {
			try {
				loader.setSource(src);
				tmp = loader.getDataSet();
								
				remove.setOptions(new String[]{"-R", "1"});
				remove.setInputFormat(tmp);
				this.instances = Filter.useFilter(tmp, remove);
				this.instances.setClassIndex(0);
			} 
			catch (IOException e) {
				System.out.println("Failed read instances from " + csvFile);
				e.printStackTrace();
			}
			catch (Exception e) {
				System.err.println("Some unexpected error occured");
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * Read Instances from a given arff file
	 * @param arffFile	Path to the arff file to read from
	 */
	public void readInstancesFromARFF(String arffFile, int classindex) {
		BufferedReader reader = null;
		File src = new File(arffFile);
		
		if(!src.exists()) 
			System.err.println("CSV File " + arffFile + " to load data from does not exist!");
		else{
			try {
				reader = new BufferedReader(new FileReader(src));
				this.instances = new Instances(reader);
				this.instances.setClassIndex(classindex);
			} 
			catch (IOException e) {
				System.out.println("Failed to create instances from " + arffFile);
				e.printStackTrace();
			} 
			finally {
				try {
					reader.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * Runs ten fold cross validation on previously read instances for a given classifier
	 * @param classifierName		Classname of the classifier experiments should be run for
	 * @param classifierOptions		Options for the classifier
	 */
	public void runTenFoldCrossValidation(String classifierName, String[] classifierOptions) {
		Classifier classifier;
		Evaluation eval;
		try {
			classifier = Classifier.forName(classifierName, classifierOptions);

			classifier.buildClassifier(this.instances);
			eval = new Evaluation(this.instances);
			eval.crossValidateModel(classifier, this.instances, 10, 
					this.instances.getRandomNumberGenerator(1));
			System.out.println(eval.toSummaryString());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Instances getInstances() {
		return this.instances;
	}
}