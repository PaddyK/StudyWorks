package classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.converters.CSVLoader;
import weka.core.Instances;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

public class WekaController {
	
	/***
	 * Read weka instances from a csvFile into object variable. Assumes first attribute in csv
	 * file is patientId which will be removed and second attribute to be the class label (yes/no)
	 * This depends on functionality in DataController method writeRawDataMiniToFile
	 * {@link}DataController.java
	 * @param csvFile path to the csv file to read from
	 */
	public Instances readInstancesFromCSV(String csvFile) {
		CSVLoader loader = new CSVLoader();
		File src = new File(csvFile);
		Remove remove = new Remove();
		Instances tmp, instances = null;
		
		if(!src.exists())
			System.err.println("CSV File " + csvFile + " to load data from does not exist!");
		else {
			try {
				loader.setSource(src);
				tmp = loader.getDataSet();
								
				remove.setOptions(new String[]{"-R", "1"});
				remove.setInputFormat(tmp);
				instances = Filter.useFilter(tmp, remove);
				instances.setClassIndex(0);
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
		return instances;
	}
	
	/***
	 * Read Instances from a given arff file
	 * @param arffFile	Path to the arff file to read from
	 */
	public Instances readInstancesFromARFF(String arffFile) {
		BufferedReader reader = null;
		File src = new File(arffFile);
		Instances instances = null;
		
		if(!src.exists()) 
			System.err.println("File " + arffFile + " to load data from does not exist!");
		else{
			try {
				reader = new BufferedReader(new FileReader(src));
				instances = new Instances(reader);
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
		return instances;
		
	}

	/**
	 * Runs ten fold cross validation on previously read instances for a given classifier
	 * @param classifierName		Classname of the classifier experiments should be run for
	 * @param classifierOptions		Options for the classifier
	 */
	public void runTenFoldCrossValidation(String classifierName, String[] classifierOptions
			,Instances instances) {
		Classifier classifier;
		Evaluation eval;
		try {
			classifier = Classifier.forName(classifierName, classifierOptions);

			classifier.buildClassifier(instances);
			eval = new Evaluation(instances);
			eval.crossValidateModel(classifier, instances, 10, 
					instances.getRandomNumberGenerator(1));
			System.out.println(eval.toSummaryString());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Uses InfoGainAttributeEval as attribute evaluator and Ranker as search Method
	 * This Method selects the specified number of features satisfying the specified
	 * information Gain.
	 * If desired, the acquired filter is applied to a test set as well
	 * Default settings are to ranks all attributes and to use no info gain of +- zero.
	 * If you want to use the default settings set featuresToSelect and infoGain to -1
	 * @param featuresToSelect	number of feature to select, -1 for default value
	 * @param infoGain			Information gain for features, -1 for default value
	 * @param train				training set, required
	 * @param test				Testing set, optional
	 * @return Instances		Array of results, Instances[0] contains feature selection for training
	 * 							data, Instances[1] contains selected features for testing data using
	 * 							the same filter setup
	 */
	public Instances[] selectFeatures(int featuresToSelect, double infoGain, Instances train,
			Instances test) {
		/** Variable Declarations
		======================================================================================== **/
		Instances[] resultInstances = new Instances[2];
		AttributeSelection selection = new AttributeSelection();
		// Evaluates the worth of an attribute by measuring the information gain with respect to
		// the class
		InfoGainAttributeEval attributeEvaluator = new InfoGainAttributeEval();
		
		// Ranks attributes by their individual evaluations. Use in conjunction with attribute
		// evaluators (ReliefF, GainRatio, Entropy etc)
		Ranker ranker = new Ranker();
		
		if(infoGain == -1) infoGain = -1.7976931348623157 * Math.pow(10, 308);
		
		/** Set options for Attribute Evaluator i.e. InfoGainAttributeEval
		--------------------------------------------------------------------------------------- **/
		// If this option is set to true, numeric attributes are just binarized instead of properly
		// discretizing them. Default is false
		attributeEvaluator.setBinarizeNumericAttributes(false);
		
		//If this option is set to true, counts for missing values are distributed. Counts are
		//distributed acros other values in proportions to their frequency. Otherwise, missing is
		// treated as a separate value
		attributeEvaluator.setMissingMerge(true);
		
		/** Set options for Search Method i.e. Ranker
		 ----------------------------------------------------------------------------------------**/
		// Ranker is only able of generating attribute rankings. Default value is true
		ranker.setGenerateRanking(true);
		// specify the number of attributes to retain. -1 indicates to retain all attributes.
		// Default value is -1
		ranker.setNumToSelect(featuresToSelect);
		// Set threshold by which attributes can be discarded. Default value results in no
		// attributes being discarded. Default value is -1.7976931348623157E308
		ranker.setThreshold(infoGain);
		
		selection.setEvaluator(attributeEvaluator);
		selection.setSearch(ranker);
		if(train != null) {
			try {
				// This step initializes the filter. If it is reused later, the same setup as for
				// training data is used. This equals batch processing mode on cmd
				selection.setInputFormat(train);
				resultInstances[0] = Filter.useFilter(train, selection);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.err.println("Instances were null in selectFeatures!!");
		}
		
		if(test != null) {
			try {
				resultInstances[1] = Filter.useFilter(test, selection);
			}
			catch(Exception e) {e.printStackTrace();}
		}
		return resultInstances;
	}
	
	public void filterInstances(Instances instances, List<String> antibodyIds) {
		
	}
}