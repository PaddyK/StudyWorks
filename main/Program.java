package main;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import classification.ClassificationResult;
import classification.Fold;
import classification.WekaController;

import data.DataController;
import data.Microarray;
import data.RawDataMini;

import weka.core.Instances;
import weka.classifiers.*;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.ComplementNaiveBayes;
import weka.classifiers.bayes.DMNBtext;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.bayes.NaiveBayesMultinomialUpdateable;
import weka.classifiers.bayes.NaiveBayesSimple;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.functions.LibLINEAR;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.SimpleLogistic;
import weka.classifiers.lazy.IB1;
import weka.classifiers.lazy.IBk;
import weka.classifiers.lazy.KStar;
import weka.classifiers.lazy.LWL;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.meta.ClassificationViaClustering;
import weka.classifiers.meta.ClassificationViaRegression;
import weka.classifiers.meta.Dagging;
import weka.classifiers.meta.Decorate;
import weka.classifiers.meta.END;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.meta.Grading;
import weka.classifiers.meta.LogitBoost;
import weka.classifiers.meta.MetaCost;
import weka.classifiers.meta.MultiBoostAB;
import weka.classifiers.meta.MultiClassClassifier;
import weka.classifiers.meta.MultiScheme;
import weka.classifiers.meta.OrdinalClassClassifier;
import weka.classifiers.meta.RacedIncrementalLogitBoost;
import weka.classifiers.meta.RandomCommittee;
import weka.classifiers.meta.RandomSubSpace;
import weka.classifiers.meta.RotationForest;
import weka.classifiers.meta.Stacking;
import weka.classifiers.meta.StackingC;
import weka.classifiers.meta.Vote;
import weka.classifiers.meta.nestedDichotomies.ClassBalancedND;
import weka.classifiers.meta.nestedDichotomies.DataNearBalancedND;
import weka.classifiers.meta.nestedDichotomies.ND;
import weka.classifiers.mi.CitationKNN;
import weka.classifiers.mi.MISMO;
import weka.classifiers.mi.MIWrapper;
import weka.classifiers.mi.SimpleMI;
import weka.classifiers.misc.HyperPipes;
import weka.classifiers.misc.VFI;
import weka.classifiers.rules.ConjunctiveRule;
import weka.classifiers.rules.DTNB;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.JRip;
import weka.classifiers.rules.NNge;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.PART;
import weka.classifiers.rules.Ridor;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.BFTree;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.FT;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.J48graft;
import weka.classifiers.trees.LADTree;
import weka.classifiers.trees.LMT;
import weka.classifiers.trees.NBTree;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.classifiers.trees.SimpleCart;
import weka.classifiers.trees.UserClassifier;

public class Program {

	/**
	 * Stores (so far) used header names of sql table
	 * @TODO Implement this as sql query to dynamically get header information
	 */
	ArrayList<String> rawDataHeader;
	/**
	 * List of integers representing antibodies
	 * @TODO should be dynamic
	 */
	List<Integer> reporterIds;
	
	Hashtable<String, ArrayList<RawDataMini>> data;
	
	public Program() {
		
		// When adding new features make sure to check war data mini if everything is set correctly !!!
		this.rawDataHeader = new ArrayList<String>();
		this.rawDataHeader.add("F635_Median");
		this.rawDataHeader.add("F635_Mean");
//		this.rawDataHeader.add("F635_SD");
//		this.rawDataHeader.add("B635");
//		this.rawDataHeader.add("B635_Median");
//		this.rawDataHeader.add("B635_Mean");
//		this.rawDataHeader.add("B635_SD");
//		this.rawDataHeader.add("F532_Median");
//		this.rawDataHeader.add("F532_Mean");
//		this.rawDataHeader.add("F532_SD");
//		this.rawDataHeader.add("B532");
//		this.rawDataHeader.add("B532_Median");
//		this.rawDataHeader.add("B532_Mean");
//		this.rawDataHeader.add("B532_SD");
//		this.rawDataHeader.add("Dia");
//		this.rawDataHeader.add("percent_gt_B635_1SD");
//		this.rawDataHeader.add("percent_gt_B635_2SD");
//		this.rawDataHeader.add("F635_percent_Sat");
//		this.rawDataHeader.add("percent_gt_B532_1SD");
//		this.rawDataHeader.add("percentage_gt_B532_2SD");
//		this.rawDataHeader.add("F532_percentage_Sat");
//		this.rawDataHeader.add("Ratio_of_Medians_635_532");
//		this.rawDataHeader.add("Ratio_of_Means_635_532");
//		this.rawDataHeader.add("Median_of_Ratios_635_532");
//		this.rawDataHeader.add("Mean_of_Ratios_635_532");
//		this.rawDataHeader.add("Ratios_SD_635_532");
//		this.rawDataHeader.add("F_Pixels");
//		this.rawDataHeader.add("B_Pixels");
//		this.rawDataHeader.add("Sum_of_Medians_635_532");
//		this.rawDataHeader.add("Sum_of_Means_635_532");
		this.rawDataHeader.add("F635_Median_B635");
		this.rawDataHeader.add("F635_Mean_B635");
		this.rawDataHeader.add("F635_Total_Intensity");
//		this.rawDataHeader.add("SNR_635");
		
		reporterIds = new ArrayList<Integer>();
		reporterIds.add(8327);
		reporterIds.add(1890);
		reporterIds.add(2158);
		reporterIds.add(2591);
		reporterIds.add(3240);
		reporterIds.add(3921);
		reporterIds.add(4079);
		reporterIds.add(4834);
		reporterIds.add(6083);
		reporterIds.add(7841);
		
		DataController datactrl = new DataController("patrick", "qwert");
		WekaController wekactrl = new WekaController();
//		data = datactrl.readMinimalRawDataFromDb(reporterIds, rawDataHeader);
		
//		data = datactrl.convertDatabaseExportToFeatureVector("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/AllFeaturesDbExport.csv");
//		data = datactrl.createFeatureVectorMean("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/AllFeaturesDbExport.csv");
//		datactrl.writeRawDataMiniToFile("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/test.csv",data, rawDataHeader, reporterIds);
//		for(String s : rawDataHeader){
//			ArrayList<String> tmp = new ArrayList<String>();
//			tmp.add(s);
//			datactrl.writeRawDataMiniToFile("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/"+ s + "_Vector_mean.csv"
//				,data, tmp, reporterIds);
//		}
//		Object[] tmp = data.keySet().toArray();
//		String[] keys = new String[tmp.length];
//		
//		for(int i = 0; i < keys.length; i++)
//			keys[i] = tmp[i].toString();
//		
//		Arrays.sort(keys);
//		for(String s : keys)
//			for(RawDataMini m : data.get(s))
//				System.out.println(m);
		
		//datactrl.writeRawDataMiniToFile("C:\\DATATEST.csv", datactrl.convertDatabaseExportToFeatureVector(rawDataHeader, "G:\\10MOST2.csv"),
				//rawDataHeader, reporterIds);
		// WekaController wc = new WekaController();
		// wc.readInstancesFromCSV("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/" +
				// "10_most_prevelant.csv");
		// wc.runTenFoldCrossValidation("weka.classifiers.trees.J48", new String[]{"-U"});
		
//		datactrl.importProtoArrayProcessedDataInDatabase("/mnt/data/Study_Works_blob/antibody/ProtoArrayAnalyzedData/PD vs NPC/");
		
//		datactrl.writeGivenProcessedDataFromCSVToArff("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/TestingSet.csv"
//				,"/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/TestingSet.arff");
//		System.out.println("\nConverting...\n=============");
//		datactrl.writeGivenProcessedDataFromCSVToArff("G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\ComparisonResults\\PD_test vs. NDC_test M Statistics.txt"
//				,"G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\Arff\\MyTestSet.arff");
//		System.out.println("\nConverting...\n=============");
//		datactrl.writeGivenProcessedDataFromCSVToArff("G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\ComparisonResults\\PD_train vs. NDC_train M Statistics.txt"
//				,"G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\Arff\\MyTrainingSet.arff");
//		datactrl.closeConnection();
		
//		readNormalizeWriteWorkflow(datactrl, wekactrl);
//		System.out.println("features\ttrees\tprecision");
//		for(int j = 1; j < 20; j++)
//		for(int i = 10; i < 50; i+=10)
//		wekactrl.classifyWithRandomForest(j, i, -1, -1,
//				wekactrl.readInstancesFromARFF("G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\Arff\\train_159-Samples_no-normalization.arff"),
//				wekactrl.readInstancesFromARFF("G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\Arff\\test_159-Samples_no-normalization.arff"));
		performLoocv(wekactrl, datactrl);
	}
	
	public void performLoocv(WekaController wekacontroller, DataController data) {
		ArrayList<weka.classifiers.Classifier> classifiers = new ArrayList<weka.classifiers.Classifier>();
//		classifiers.add(new BayesNet());							// Discretizing
//		classifiers.add(new ComplementNaiveBayes()); 				// Error, cant handle negative numeric values
//		classifiers.add(new DMNBtext());
//		classifiers.add(new NaiveBayes());
//		classifiers.add(new NaiveBayesMultinomial()); 				// Error, cant handle negative numeric values
//		classifiers.add(new NaiveBayesMultinomialUpdateable()); 	// Error, cant handle negative numeric values
//		classifiers.add(new NaiveBayesSimple()); 					// Does somehow not build a classifier
//		classifiers.add(new NaiveBayesUpdateable());
//		
//		classifiers.add(new LibLINEAR()); 							// Not in classpath
//		classifiers.add(new LibSVM()); 								// Not in classpath
//		classifiers.add(new Logistic());							// No output
//		classifiers.add(new MultilayerPerceptron());
//		classifiers.add(new SimpleLogistic());
//		classifiers.add(new SMO());
//		
//		classifiers.add(new IB1());
//		classifiers.add(new IBk());
//		classifiers.add(new KStar());
//		classifiers.add(new LWL());
//		
//		classifiers.add(new AdaBoostM1());
//		classifiers.add(new AttributeSelectedClassifier());
//		classifiers.add(new Bagging());
//		classifiers.add(new ClassificationViaClustering());
//		classifiers.add(new ClassificationViaRegression());
//		classifiers.add(new CVParameterSelection());
//		classifiers.add(new Dagging());
//		classifiers.add(new Decorate());
//		classifiers.add(new END());
//		classifiers.add(new FilteredClassifier());
//		classifiers.add(new Grading());
//		classifiers.add(new LogitBoost());
//		classifiers.add(new MetaCost());
//		classifiers.add(new MultiBoostAB());
//		classifiers.add(new MultiClassClassifier());
//		classifiers.add(new MultiScheme());
//		classifiers.add(new OrdinalClassClassifier());
//		classifiers.add(new RacedIncrementalLogitBoost());
//		classifiers.add(new RandomCommittee());
//		classifiers.add(new RandomSubSpace());
//		classifiers.add(new ClassBalancedND());
//		classifiers.add(new DataNearBalancedND());
//		classifiers.add(new ND());
//		classifiers.add(new RotationForest());
//		classifiers.add(new Stacking());
//		classifiers.add(new StackingC());
//		classifiers.add(new Vote());
//		
//		classifiers.add(new CitationKNN());
//		classifiers.add(new MISMO());
//		classifiers.add(new MIWrapper());
//		classifiers.add(new SimpleMI());
//		
//		classifiers.add(new HyperPipes());
//		classifiers.add(new VFI());
//		
//		classifiers.add(new ConjunctiveRule());
//		classifiers.add(new DecisionTable());
//		classifiers.add(new DTNB());
//		classifiers.add(new JRip());
//		classifiers.add(new NNge());
//		classifiers.add(new OneR());
//		classifiers.add(new PART());
//		classifiers.add(new Ridor());
//		classifiers.add(new ZeroR());
//		
//		classifiers.add(new BFTree());
//		classifiers.add(new DecisionStump());
//		classifiers.add(new FT());
//		classifiers.add(new J48());
//		classifiers.add(new J48graft());
//		classifiers.add(new LADTree());
//		classifiers.add(new LMT());
//		classifiers.add(new NBTree());
		RandomForest c = new RandomForest();
		c.setSeed(1);
		c.setNumTrees(10);
		c.setNumFeatures(0);
		classifiers.add(c);
//		classifiers.add(new RandomTree());
//		classifiers.add(new REPTree());
//		classifiers.add(new SimpleCart());
////		classifiers.add(new UserClassifier());
		
//		classifiers.clear();		
//		classifiers.add(new NaiveBayes());
		
		ArrayList<ClassificationResult> results = wekacontroller.prepareLoocValidation(classifiers,
				"G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\Arff\\loocv\\", 159);
		
		String content = "";
		StringBuffer buffer = new StringBuffer();
		for(ClassificationResult cr : results) {
			content += cr.getMinimal() + System.getProperty("line.separator");
			buffer.append(cr.toInsertStatemnt());
//			data.executeSqlStatement(cr.toInsertStatemnt());
//			String stmnt = cr.toInsertStatemnt() + System.getProperty("line.separator");
			for(Fold f : cr.getFolds()) {
				buffer.append(f.toInsertStatemnt());
//				data.executeSqlStatement(f.toInsertStatemnt());
//				stmnt = f.toInsertStatemnt();
				System.out.println(f.getMinimal());
			}
			System.out.println(cr.getMinimal());
		}
		data.writeToTabSeparatedFile("G:\\test.txt", content);
		data.writeToTabSeparatedFile("G:\\sql.sql", buffer.toString());
	}
	
	public void readNormalizeWriteWorkflow(DataController datactrl, WekaController wekactrl) {
		String arffDirectory = "G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\Arff\\";
		String filename = datactrl.gprFilesToArffFile("G:\\Study_Works_blob\\antibody\\DistinctFiles\\"
				, "proCAT", arffDirectory);
//		String filename = "159-Samples_no-normalization.arff";
		System.out.println("\nCreate Test and Training Set and save them to file");
		createAndSaveTestAndTrainingSet(wekactrl, datactrl, 
				arffDirectory + filename, 
				arffDirectory, filename);
	}
	
	/**
	 * Create and save a test/training set from a set of instances and save them to file
	 * @param wekactrl	WekaController instance
	 * @param datactrl	DataController instance
	 * @param source	File to initial arff File
	 * @param directory	Directory where test/Training set should be saved to
	 * @param suffix	name of file. The name will be preceeded by test_, train_
	 */
	public void createAndSaveTestAndTrainingSet(WekaController wekactrl, DataController datactrl, 
			String source, String directory, String suffix) {
		String[] sets = wekactrl.readDataSetAndCreateTestTrainingSet(source, 0.8);
		datactrl.saveInstancesToArffFile(sets[0], directory + "train_" + suffix);
		datactrl.saveInstancesToArffFile(sets[1], directory + "test_" + suffix);		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Program();
		System.out.println("Finish");
	}

}
