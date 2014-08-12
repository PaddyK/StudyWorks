package main;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import wekaConfigFileInterpretation.ExperimentSetup;
import wekaConfigFileInterpretation.ClassifierSetup;
import wekaConfigFileParser.WekaConfigGrammarParser;
import wekaConfigFileParser.WekaConfigGrammarLexer;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

import data.DataController;
//import classification.WekaController;
import concurrent.*;
//import structure.Category;

public class ConcurrentProgram {

	//private WekaController wcontroller;
	private DataController dcontroller;
	private String username;
	private String password;

	public ConcurrentProgram(String username, String password) {
		//wcontroller = new WekaController();
		this.username = username;
		this.password = password;
		dcontroller = new DataController(username, password);
	}
	
	public ConcurrentProgram() {
		//wcontroller = new WekaController();
		dcontroller = new DataController();
		username = null;
		password = null;
	}
	
	public boolean isDbCapable() {
		if(username == null && password == null)
			return false;
		else
			return true;
	}
	
	public void convertGprToArff(String folder, String normalizationMethod, String destFolder) {
		dcontroller.gprFilesToArffFile(folder, normalizationMethod, destFolder);
	}
	
	public void initializeQueue(String classifier, String[] options, ConcurrentLinkedQueue<Looc> queue) {
		queue.add(new Looc("loocv-" + new Date().getTime(), classifier, options));
	}
	
	public void performLoocv(LinkedList<Looc> queue, LoocConcurrentList toConsist, String pathToArffFiles,
			double infoGain, double numAttributes, double reader, int numClassifier, String loocvResultsFile) throws Exception {
		List<Double> gain = new ArrayList<Double>();
		List<Integer> num = new ArrayList<Integer>();
		gain.add(infoGain);
		num.add((int)numAttributes);
		performLoocv(queue, toConsist, pathToArffFiles, gain, num, (int)reader, numClassifier, loocvResultsFile);
	}
	
	public void performLoocv(LinkedList<Looc> queue, LoocConcurrentList toConsist, String pathToArffFiles,
			List<Double> infoGain, List<Integer> numAttributes, int reader, int numClassifier, String loocvResultsFile) throws Exception {
		ConcurrentLinkedQueue<String[]> paths;
		MyConcurrentList 				list;
		Thread[] 						readers		= new Thread[reader];
		Thread[] 						classifiers	= new Thread[numClassifier];
		ConcurrentLinkedQueue<Looc> 	execute		= new ConcurrentLinkedQueue<Looc>();
		SimpleDateFormat				dateFormat	= new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
		execute.addAll(queue);
		
		for(double gain : infoGain) {
			for(int numAttr: numAttributes) {
				
				System.out.println(dateFormat.format(new Date().getTime()) + 
						": Start loocvs with infoGain " + gain + " of " + infoGain);
				paths = preparePaths(pathToArffFiles);
				list = new MyConcurrentList();

				// Prepare classifier
				//=================================

				// Start threads
				//=======================================
				for(int i = 0; i < readers.length; i++) {
					readers[i] = new ReadWorker(list, paths, numAttr, gain);
					readers[i].setName("reader" + (i+1));
					readers[i].start();
				}
				
				for(int i = 0; i < classifiers.length; i++) {
					classifiers[i] = new ClassifyWorker(execute, list);
					classifiers[i].setName("classifier" + i);
					classifiers[i].start();
				}

				// Wait for threads
				//============================================
				try {
					for(int i = 0; i < readers.length; i++)
						readers[i].join();

				}
				catch(InterruptedException e) {
					System.err.println("Error on joining readers");
					e.printStackTrace();
					Thread.currentThread().interrupt();
					break;
				}
				
				list.setDone();
				
				try {

					for(int i = 0; i < classifiers.length; i++)
						classifiers[i].join();
				}
				catch(InterruptedException e) {
					System.err.println("Error on joining writers");
					e.printStackTrace();
					Thread.currentThread().interrupt();
					break;
				}
				int count=0;

				// Persist results
				//===============================================
				for(Looc looc : execute) {
					count++;
					looc.setFeatures(numAttr);
					looc.setInfoGain(gain);
					looc.consolidateFolds(5);
					System.out.println("\t " + dateFormat.format(new Date().getTime()) + ": looc " + count + ":\t" + looc.toString());
					dcontroller.appendToTabSeparatedFile(loocvResultsFile
							, looc.toString() + System.getProperty("line.separator"));
					toConsist.add(looc);
				}
				execute.clear();
			}
		}
	}
	
	/**
	 * Returns a Queue containing test and training set. It is important for the test set being
	 * the first element in the array and the training set being the second element in the array
	 * as ReadWorker expects this order
	 * @param pathToFolder
	 * @return				Collection of String[]{testset, trainset}
	 * @throws Exception
	 */
	public ConcurrentLinkedQueue<String[]> preparePaths(String pathToFolder) throws Exception {
		class MyFilenameFilter implements FilenameFilter {
			private int index;
			public void setIndex(int idx){index = idx;}
			@Override
			public boolean accept(File dir, String name) {
				if(name.toLowerCase().endsWith(".arff") && name.toLowerCase().startsWith("fold-" + index + "_"))
					return true;
				else
					return false;
			}
		}
		
		ConcurrentLinkedQueue<String[]> paths = new ConcurrentLinkedQueue<String[]>();
		File folder = new File(pathToFolder);
		MyFilenameFilter filter = new MyFilenameFilter();
		String tmp[];
		
		if(folder.isFile()) {
			System.err.println("Path to arff files is not a folder");
			throw new Exception(pathToFolder + " is a file and not a folder. Please specify folder with arff files");
		}		
		
		for(int i = 1; i <=159; i++) {
			filter.setIndex(i);
			tmp = folder.list(filter);
			for(int j=0;j<tmp.length; j++)
				tmp[j] = pathToFolder + System.getProperty("file.separator") + tmp[j];
			paths.add(tmp);				
		}
		return paths;
	}
	
	private static List<ExperimentSetup> useParser(String configFile) {
		List<ExperimentSetup> setups = null;
		try {
			ANTLRFileStream			stream = new ANTLRFileStream(configFile);
			WekaConfigGrammarLexer	lexer  = new WekaConfigGrammarLexer(stream);
			CommonTokenStream		ts     = new CommonTokenStream(lexer);
			WekaConfigGrammarParser	parser = new WekaConfigGrammarParser(ts);
			setups = parser.configfile().f.interpret();

		} catch(Exception e) {
			e.printStackTrace();
		}
		return setups;
	}
	
	public static void createLoocvs(LinkedList<Looc> queue, ClassifierSetup setup) {
		long milisec = new Date().getTime();
		if(setup.hasOptions()) {
			for(String[] arr : setup.getOptionsAsArray()) {
				queue.add(new Looc("loocv-" + milisec++
						,setup.getClassifierName()
						,arr));
			}
		}
		else {
			queue.add(new Looc("loocv-" + milisec++
					,setup.getClassifierName()
					,null));
		}
	}
	
	public static LinkedList<Looc> prepareBag(LinkedList<Looc> wholeSet, int bagSize) {
		LinkedList<Looc> subset = new LinkedList<Looc>();
		while(bagSize > 0 && !wholeSet.isEmpty()) {
			subset.add(wholeSet.poll());
			bagSize--;
		}
		return subset;
	}
	
	public static void offlineTest(String file) throws Exception {
		int 			writer;
		double 			reader;
		double 			bag;
		List<Double> 	infoGain;
		List<Integer>	numAttributes;
		
		LinkedList<Looc> 		queue;
		List<ExperimentSetup>	setups 		= useParser(file);
		String					arffFolder	= null;
		Object					tmp;

		for(ExperimentSetup eSetup : setups) {
			for(ClassifierSetup cSetup : eSetup.getClassifierSetups()) {
				queue = new LinkedList<Looc>();
				createLoocvs(queue, cSetup);
				
				/* Retrieve Settings from configuration file
				 * ========================================== */
				if((tmp = eSetup.getResource("reader")) == null)
					reader = 1;
				else
					reader = (Integer)tmp;
				
				if((tmp = eSetup.getResource("writer")) == null)
					writer = 1;
				else
					writer = (Integer)tmp;
				
				if((tmp = eSetup.getResource("bag")) == null)
					bag = 1;
				else
					bag = (Integer)tmp;
					
				if((tmp = eSetup.getResource("infogain")) == null) {
					infoGain = new ArrayList<Double>();
					infoGain.add(-1.0);
				}
				else if(tmp instanceof List)
					infoGain = (List<Double>) tmp;
				else {
					infoGain = new ArrayList<Double>();
					infoGain.add((Double)tmp);
				}
				
				if((tmp = eSetup.getResource("numAttributes")) == null) {
					numAttributes = new ArrayList<Integer>();
					numAttributes.add(-1);
				}
				else if(tmp instanceof List)
					numAttributes = (List<Integer>) tmp;
				else {
					numAttributes = new ArrayList<Integer>();
					numAttributes.add((Integer)tmp);
				}
				
				if((tmp = eSetup.getResource("arffFolder")) == null){
					throw new Exception("Folder containing arff files for microarrays not specified");
				} else {
					arffFolder = (String)tmp;
				}
				
				/* Perform loocv for bag portion of data
				 * ========================================== */
				ConcurrentLinkedQueue<String[]> paths = new ConcurrentProgram().preparePaths(arffFolder);
				for(String[] arr : paths)
					System.out.println(arr[0] + "\n" + arr[1]);

				System.out.println("Perform with:\n\treader:\t\t\t" + reader+"\n\twriter:\t\t\t" + writer+"\n\tbagsize:\t\t"+bag
						+"\n\tinfo Gain:\t\t "+infoGain+"\n\tnumber Attributes:\t"+numAttributes +"\n");
					
				while(!queue.isEmpty()) {
					LinkedList<Looc> list = prepareBag(queue, (int)bag);
					for(Looc l : list)
						System.out.println(l.getClassifier() + "\t" + l.getOptionString());
				}
			}
		}
		System.out.println("===============\nFINISHED");
	}

	public static void executionFromConfig(String configfile) throws Exception {
		System.out.println("Execution reached");
		int 			writer;
		int				numClassifier;
		int 			reader;
		double 			bag;
		String			pathToArffFiles;
		List<Double> 	infoGain;
		List<Integer>	numAttributes;
		
		DbWriter[] 				writers;
		LinkedList<Looc> 		queue;
		List<ExperimentSetup>	setups 		= useParser(configfile);
		ConcurrentProgram 		p 			= new ConcurrentProgram();
		LoocConcurrentList 		toConsist	= new LoocConcurrentList();
		String					arffFolder	= null;
		String					resultfile	= null;
		String					sqlOut;
		Object					tmp;

		for(ExperimentSetup eSetup : setups) {
			for(ClassifierSetup cSetup : eSetup.getClassifierSetups()) {
				queue = new LinkedList<Looc>();
				createLoocvs(queue, cSetup);
				
				/* Retrieve Settings from configuration file
				 * ========================================== */
				if((tmp = eSetup.getResource("reader")) == null)
					reader = 1;
				else
					reader = (Integer)tmp;
				
				if((tmp = eSetup.getResource("writer")) == null)
					writer = 1;
				else
					writer = (Integer)tmp;
				
				if((tmp = eSetup.getResource("classifier")) == null)
					numClassifier = 1;
				else
					numClassifier = (Integer)tmp;
				
				if((tmp = eSetup.getResource("bag")) == null)
					bag = 1;
				else
					bag = (Integer)tmp;
				
				if((tmp = eSetup.getResource("infogain")) == null) {
					infoGain = new ArrayList<Double>();
					infoGain.add(-1.0);
				}
				else if(tmp instanceof List)
					infoGain = (List<Double>) tmp;
				else {
					infoGain = new ArrayList<Double>();
					infoGain.add((Double)tmp);
				}
				
				if((tmp = eSetup.getResource("numAttributes")) == null) {
					numAttributes = new ArrayList<Integer>();
					numAttributes.add(-1);
				}
				else if(tmp instanceof List)
					numAttributes = (List<Integer>) tmp;
				else {
					numAttributes = new ArrayList<Integer>();
					numAttributes.add((Integer)tmp);
				}
				
				if((tmp = eSetup.getResource("arffFolder")) == null){
					throw new Exception("Folder containing arff files for microarrays not specified");
				} else {
					arffFolder = (String)tmp;
				}
				
				if((tmp = eSetup.getResource("resultfile")) == null)
					if(System.getProperty("os.name").contains("Windows"))
						resultfile = "C:\\weka_experiment_results.csv";
					else
						resultfile = "~/weka_experiment_results.csv";
				else if(tmp instanceof String)
					resultfile = (String)tmp;
				else
					throw new Exception("Resultfile was not a String");
				
				if((tmp = eSetup.getResource("sqlOut")) == null){
					throw new Exception("No SQL outout folder for classification results defined");
				} else {
					sqlOut = (String)tmp;
				}
				
				/* Create and start DBWriters
				 * ========================================== */
				writers = new DbWriter[writer];
				for(int i = 0; i < writers.length; i++) {
					if(p.isDbCapable())
						writers[i] = new DbWriter(toConsist
								,p.username
								,p.password
								,sqlOut);
					else
						writers[i] = new DbWriter(toConsist, sqlOut);
					writers[i].setName("dbWriter" + (i+1));
					writers[i].start();
				}

				/* Perform loocv for bag portion of data
				 * ========================================== */
				while(!queue.isEmpty())
					p.performLoocv(prepareBag(queue, (int)bag)
							, toConsist
							, arffFolder
							, infoGain
							, numAttributes
							, reader
							, numClassifier
							, resultfile);
				
				/* Interrupt DBWriters and wait for them to terminate
				 * =================================================== */
				for(int i = 0; i < writers.length; i++)
					writers[i].interrupt();
				try {
					for(int i = 0; i < writers.length; i++)
						writers[i].join();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.err.println("Closing Interrupt Exception on Thread " + Thread.currentThread().getName());
					e.printStackTrace();
				}
			}
			System.out.println("===============\nFINISHED");
		}
	}
	
	public static void convert(String[] args) {
		String 	srcFolder,
				normalizationMethod,
				destFolder;
		if((srcFolder = MyUtils.searchArgs("-src", args)) == null)
			System.err.println("Conversion failed. No source folder specified");
		
		if((normalizationMethod = MyUtils.searchArgs("-normalization", args)) == null)
			System.err.println("Conversion failed. No source folder specified");
		else
			normalizationMethod = "none";
		
		if((destFolder = MyUtils.searchArgs("-dest", args)) == null)
			System.err.println("Conversion failed. No destination folder specified");
		new DataController().gprFilesToArffFile(srcFolder, normalizationMethod, destFolder);
	}
	
	public static void main(String[] args) {
		String configFile;
		if(MyUtils.isDebug(args) && MyUtils.isWithConfig(args)) {
			configFile = MyUtils.searchArgs("-config", args);
			try{
			offlineTest(configFile);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(MyUtils.isWithConfig(args)) {
			configFile = MyUtils.searchArgs("-config", args);
			try{
				executionFromConfig(configFile);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(MyUtils.isConversion(args))
			convert(args);
	}
}