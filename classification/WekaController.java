package classification;

import java.beans.FeatureDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import data.DataController;
import data.Microarray;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.converters.CSVLoader;
import weka.core.Instances;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;

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
	 * @param classifierName	Classname of the classifier experiments should be run for
	 * @param instances			Data to perfrom ten fold cross validation on
	 * @throws Exception 
	 */
	public Evaluation runTenFoldCrossValidation(Classifier classifier ,Instances instances) throws Exception {

		Evaluation eval;
		classifier.buildClassifier(instances);
		eval = new Evaluation(instances);
		eval.crossValidateModel(classifier, instances, 10, instances.getRandomNumberGenerator(1));
		
		return eval;
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
		AttributeSelection selection = getFilterFromRanker(featuresToSelect, infoGain, train);
		
		if(train != null) {
			try {
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
	
	/**
	 * Creates an AttributeSelection which later can be used to filter instances. By doing so
	 * batch processing from command line is achieved
	 * @param featuresToSelect	number of feature to select, -1 for default value (10)
	 * @param infoGain			Information gain for features, -1 for default value (no attributes
	 * 							discarded)
	 * @param train				Instances to create filter from
	 * @return					AttributeSelection to use later to filter instances
	 */
	private AttributeSelection getFilterFromRanker(int featuresToSelect, double infoGain, Instances train) {
		/** Variable Declarations
		======================================================================================== **/
		AttributeSelection selection = new AttributeSelection();
		// Evaluates the worth of an attribute by measuring the information gain with respect to
		// the class
		InfoGainAttributeEval attributeEvaluator = new InfoGainAttributeEval();
		
		// Ranks attributes by their individual evaluations. Use in conjunction with attribute
		// evaluators (ReliefF, GainRatio, Entropy etc)
		Ranker ranker = new Ranker();
		
		if(infoGain == -1) 
			infoGain = -1.7976931348623157 * Math.pow(10, 308);
		
		/** Set options for Attribute Evaluator i.e. InfoGainAttributeEval
		--------------------------------------------------------------------------------------- **/
		// If this option is set to true, numeric attributes are just binarized instead of properly
		// discretizing them. Default is false
		attributeEvaluator.setBinarizeNumericAttributes(false);
		
		//If this option is set to true, counts for missing values are distributed. Counts are
		//distributed across other values in proportions to their frequency. Otherwise, missing is
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
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.err.println("Instances were null in selectFeatures!!");
		}
		return selection;
	}
	
	/**
	 * Performs classification using random forest. This method assumes class attribute to be the
	 * last attribute and to be called "disease"
	 * @param newNumFeatures	Number of features to use in random selection, set to -1 if you want
	 * 							to use default value. Default is 0.
	 * @param newNumTrees		Number of trees to be generated, set to -1 if you want to use
	 * 							default value. Default is 10
	 * @param seed				Random number seed to be used. Default is 1, set to -1 if you want to
	 * 							use default value
	 * @param depth				Maximum depth of the trees. Set to -1 if you want to use default.
	 * 							Default is 0 = unlimited
	 */
	public void classifyWithRandomForest(int newNumFeatures, int newNumTrees, int seed, int depth,
			Instances trainingSet, Instances testSet) {
		
		RandomForest randomforest; 
		Evaluation evaluation;
		Instances trainingSetSelect;
		Instances testSetSelect;
		
		trainingSet.setClass(trainingSet.attribute("disease"));
		testSet.setClass(testSet.attribute("disease"));		
		
		if(newNumFeatures == -1)
			newNumFeatures = 0;		
		if(newNumTrees == -1)
			newNumTrees = 10;		
		if(seed == -1)
			seed = 1;		
		if(depth == -1)
			depth = 0;
		
//		for(int i = -1; i < 20; i+=2) {
		Instances[] sets = selectFeatures(newNumFeatures, -1, trainingSet, testSet);
//		if(i==-1) i = 1;
		testSetSelect = sets[1];
		trainingSetSelect = sets[0];
		
		randomforest = new RandomForest();
		
		// Set the number of features to use in random selection
		randomforest.setNumFeatures(newNumFeatures);		
		// Set the value of numTrees.
		randomforest.setNumTrees(newNumTrees);		
		// Set the seed for random number generation.
		randomforest.setSeed(seed);		
		// Set the maximum depth of the tree, 0 for unlimited
		randomforest.setMaxDepth(depth);
		
		try {
//			evaluation = runTenFoldCrossValidation(randomforest, trainingSet);
			randomforest.buildClassifier(trainingSetSelect);
			evaluation = new Evaluation(trainingSetSelect);

//			System.out.println("Evaluation ten fold cross\n##################################" +
//						evaluation.toSummaryString());

			try {
				evaluation.evaluateModel(randomforest, testSetSelect);
				System.out.println(newNumFeatures + "\t" + newNumTrees + "\t" +
							(evaluation.correct()/(evaluation.correct() + evaluation.incorrect())));
			}
			catch(Exception e) {
				System.err.println("Error during evaluation of test set");
				e.printStackTrace();
			}
		}
		catch(Exception e) {
			System.err.println("Error during 10-fold-cross validation");
			e.printStackTrace();
		}		
//		}
	}
	
	/**
	 * Creates a test and training set from a set of instances. The wholeSet is first randomized and
	 * then splitted up according to sizeTestSet
	 * @param wholeSet		Set of instances from which test/train set will be created
	 * @param sizeTestSet	Size of test set in percent (0 < sizeTestSet < 1)
	 * @return				Returns array with two element, first element is training set, second
	 * 						element is testing set
	 */
	public Instances[] createTestAndTrainSet(Instances wholeSet, double sizeTestSet) {
		wholeSet.randomize(new java.util.Random(0));
		int trainSize = (int)Math.round(wholeSet.numInstances() * sizeTestSet);
		int testSize = wholeSet.numInstances() - trainSize;
		Instances testSet = new Instances(wholeSet, 0, testSize);
		Instances trainSet = new Instances(wholeSet, testSize, trainSize);
		
		return new Instances[]{trainSet, testSet};
	}
	
	/**
	 * Reads instances from arff file, creates test and training set and returns content of those
	 * sets as string
	 * @param source	Path to source arff file
	 * @param size		size of test set
	 * @return			String array containing test and train set as string. First element is train set
	 * 					second element is test set
	 */
	public String[] readDataSetAndCreateTestTrainingSet(String source, double size) {
		Instances set = readInstancesFromARFF(source);
		Instances[] traintest = createTestAndTrainSet(set, size);
		return new String[]{traintest[0].toString(), traintest[1].toString()};
	}
		
	/**
	 * Prepares loocv. Creates tests model created from n - 1 patients against each patient for
	 * the specified classifiers once
	 * @param arffFolder	Path pointing to the folder folds for loocv reside
	 * @param classifiers	Classifiers loocv should be performed for
	 */
	public ArrayList<ClassificationResult> prepareLoocValidation(ArrayList<Classifier> classifiers, 
			String arffFolder, int folds) {
		int count = 0;
		// TODO set numClasses dynamically
		int numClasses = 5;
		boolean firsttime = true;
		Instances test;
		Instances train;
		Evaluation eval;
		AttributeSelection selection = null;
		
		ArrayList<ClassificationResult> results = new ArrayList<ClassificationResult>();

		// Initialize
		for(int i = 0; i < classifiers.size(); i++) {
			results.add(new ClassificationResult(classifiers.get(i), "looc-" + new Date().getTime()));
			String[] options =  classifiers.get(i).getOptions();
			for(String s : options)
				System.out.println(s);
			System.out.println(classifiers.get(i).toString());
		}
		
		for(int fold = 1; fold <= folds; fold++) {
			System.out.println("Fold " + fold);
			
			// Load instances from file
			test = readInstancesFromARFF(arffFolder + "fold-" + fold + "_test.arff");
			train = readInstancesFromARFF(arffFolder + "fold-" + fold + "_train.arff");
			test.setClassIndex(test.numAttributes() - 1);
			train.setClassIndex(train.numAttributes() - 1);
			
			// If this is the first fold when obtain a filter to use only those attributes with
			// highest information gain.
			// This filter is then used in subsequent folds
			if(firsttime) {
				selection = getFilterFromRanker(10, -1, train); // get selection to perform filtering
			}
			
			// Apply the in fold one obtained filter to test and training set
			try {
				test = Filter.useFilter(test, selection);
				train = Filter.useFilter(train, selection);
				
				if(firsttime) {
					firsttime = false; // Set firsttime false since all operations for first run are done
					numClasses = test.classAttribute().numValues();
					for(int j = 0; j < results.size(); j++)
						results.get(j).setInstances(test);
				}
			}
			catch(Exception e) {
				firsttime = false;
				System.out.println("Error during application of filter to sets\n=====================");
				e.printStackTrace();
			}
			
			// For each classifier perform one fold of loocv and add result to results
			for(int i = 0; i < classifiers.size(); i++) {
				eval = performLoocValidation(train, test, classifiers.get(i)); // perform loocv
				// add a new fold to the result for this classifier
				results.get(i).addFold(new Fold(eval, "fold-" + new Date().getTime(),  
						results.get(i).loocId, test.relationName()));	
				
				String[] options =  classifiers.get(i).getOptions();
				for(String s : options)
					System.out.println(s);
				System.out.println(classifiers.get(i).getClass());
			}
		};
		
		// Consolidate f1score, precision, recall, etc from folds into one result
		for(int i = 0; i < classifiers.size(); i++) {
			results.get(i).finalizeLooc(numClasses);
		}
		
		return results;
	}
	
	private Evaluation performLoocValidation(Instances train, Instances test, Classifier classifier) {
		Evaluation eval = null;
		
		try {
			classifier.buildClassifier(train);
			eval = new Evaluation(train);
			
			try {
				eval.evaluateModel(classifier, test);
			}
			catch(Exception e) {
				System.err.println("Error during evaluation model for classifier " +
						classifier.toString() + "\n==============================================");
				e.printStackTrace();
			}
			
		}
		catch(Exception e) {
			System.err.println("Error during training classifier " + classifier.toString()
					+"============================================================");
			e.printStackTrace();
		}
//		System.out.println(eval.correct() + "\t" + eval.incorrect());
		return eval;
	}

	/**
	 * This method creates the folds for loocv. In the arff file for the test set the patient id
	 * is used as relation name to later retrieve it when performing loocv
	 * @param gprFolder		Path pointing to folder containing gpr files
	 * @param arffFolder	Path pointing to folder in which arff files should be written
	 */
	public void createNewFolds(String gprFolder, String arffFolder) {
		int index = 0;
		int count = 0;
		Microarray array;
		DataController dctrl = new DataController();
		ArrayList<Microarray> arrays = dctrl.gprFilesToMicroarray(gprFolder);
		ArrayList<Microarray> tested = new ArrayList<Microarray>(arrays.size());
		ArrayList<Microarray> tmp = new ArrayList<Microarray>(1);
		ArrayList<Microarray> trainingset = new ArrayList<Microarray>(arrays.size());
		
		do {
			count++;
			// After the first fold always use first array from list
			if(count != 1 && index != 0)
				index = 0;
			
			// For the first fold use a random array
			if(count == 1) {
				index = new Random().nextInt(arrays.size());
			}
			
			// Remove the array from the list and put it into a temporary variable. Then add this
			// temporary arry to a list (array will be the only one element in the list, needed for
			// function in datacontroller). Then add remaining arrays from arrays as well as the 
			// arrays in tested (arrays which were already tested) into a separate list denoting
			// the training set for this fold
			array = arrays.get(index);
			arrays.remove(index);
			tmp.add(array);
			trainingset.addAll(tested);
			trainingset.addAll(arrays);
			
			// Write test and training set for one fold
			dctrl.writeMicroarraysToArffFile(tmp, arffFolder + "fold-" + count + "_test.arff",
					array.getPatientId() + "_fold-" + count + "_test_set");
			dctrl.writeMicroarraysToArffFile(trainingset, arffFolder + "fold-" + count + "_train.arff", 
					"fold-" + count + "_training_set");
			
			// add array used for testing in this fold to set of tested arrays
			// clear other lists for next fold
			tested.add(array);
			tmp.clear();
			trainingset.clear();			
		}
		while(!arrays.isEmpty());
	}
}