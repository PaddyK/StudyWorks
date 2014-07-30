package main;

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
	//private String username;
	//private String password;

	public ConcurrentProgram(String username, String password) {
		//wcontroller = new WekaController();
		dcontroller = new DataController("patrick", "qwert");
		//this.username = username;
		//this.password = password;
	}
	
	public ConcurrentProgram() {
		//wcontroller = new WekaController();
		dcontroller = new DataController();
		//username = null;
		//password = null;
	}
	
	public void initializeQueue(String classifier, String[] options, ConcurrentLinkedQueue<Looc> queue) {
		queue.add(new Looc("loocv-" + new Date().getTime(), classifier, options));
	}
	
	public void performLoocv(LinkedList<Looc> queue, LoocConcurrentList toConsist, String pathToArffFiles,
			double infoGain, double numAttributes, double reader, int numClassifier, String loocvResultsFile) {
		List<Double> gain = new ArrayList<Double>();
		List<Integer> num = new ArrayList<Integer>();
		gain.add(infoGain);
		num.add((int)numAttributes);
		performLoocv(queue, toConsist, pathToArffFiles, gain, num, (int)reader, numClassifier, loocvResultsFile);
	}
	
	public void performLoocv(LinkedList<Looc> queue, LoocConcurrentList toConsist, String pathToArffFiles,
			List<Double> infoGain, List<Integer> numAttributes, int reader, int numClassifier, String loocvResultsFile) {
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

	private ConcurrentLinkedQueue<String[]> preparePaths(String pathToFolder) {
		ConcurrentLinkedQueue<String[]> paths = new ConcurrentLinkedQueue<String[]>();
		for(int i = 1; i <= 159; i++)
			paths.add(new String[]{
					pathToFolder + "\\fold-" + i + "_test.arff",
					pathToFolder + "\\fold-" + i + "_train.arff"});

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
	
	public static void offlineTest(String file) {
		int 			writer;
		double 			reader;
		double 			bag;
		List<Double> 	infoGain;
		List<Integer>	numAttributes;
		
		LinkedList<Looc> 		queue;
		List<ExperimentSetup>	setups 		= useParser(file);
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
				
				/* Perform loocv for bag portion of data
				 * ========================================== */
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
	
	public static void executionFromConfig(String configfile, String resultfile) throws Exception  {
		int 			writer;
		int				classifier;
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
					classifier = 1;
				else
					classifier = (Integer)tmp;
				
				if((tmp = eSetup.getResource("bag")) == null)
					bag = 1;
				else
					bag = (Integer)tmp;
				
				if((tmp = eSetup.getResource("arffFiles")) == null)
					throw new Exception("No path to arff files specified!");
				else
					pathToArffFiles = (String)tmp;
				
				//TODO make list for info gain possible
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
				
				/* Create and start DBWriters
				 * ========================================== */
				writers = new DbWriter[writer];
				for(int i = 0; i < writers.length; i++) {
					writers[i] = new DbWriter(toConsist
							,"patrick"
							,"qwert"
							,"G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\SQL\\");
					writers[i].setName("dbWriter" + (i+1));
					writers[i].start();
				}
			
				/* Perform loocv for bag portion of data
				 * ========================================== */
				while(!queue.isEmpty())
					p.performLoocv(prepareBag(queue, (int)bag)
							, toConsist
							, pathToArffFiles
							, infoGain
							, numAttributes
							, reader
							, classifier
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
	
	public static void main(String[] args) {
		String value;
		if(MyUtils.isDebug(args) && MyUtils.isWithConfig(args)) {
			value = MyUtils.searchArgs("-config", args);
			offlineTest(value);
		}
		else if(MyUtils.isWithConfig(args)) {
			value = MyUtils.searchArgs("-config", args);
			String file = null;
			if((MyUtils.searchArgs("-resultfile", args)) == null)
				file = "G:\\results_param_tuning.csv";
			try {
				executionFromConfig(value, file);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}