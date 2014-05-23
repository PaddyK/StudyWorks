package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import parser.Setup;
import parser.common.simpleLexer;
import parser.common.simpleParser;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

import data.DataController;
import classification.WekaController;
import concurrent.*;
import structure.Category;

public class ConcurrentProgram {

	private WekaController wcontroller;
	private DataController dcontroller;
	private String username;
	private String password;

	private static void oldApproach(String[] args) {

		int numLoocs = 6;
		int numReader = 4;
		int numDbWriter = 6;
		String file;

		DbWriter[] writers 					= new DbWriter[numDbWriter];
		ConcurrentProgram p 				= new ConcurrentProgram("patrick","qwert");
		LinkedList<Looc> queue  		 	= new LinkedList<Looc>();
		LinkedList<Looc> execute 			= new LinkedList<Looc>();
		ArrayList<Double> infoGain 			= new ArrayList<Double>();
		LoocConcurrentList toConsist     	= new LoocConcurrentList();
		ArrayList<Integer> numAttributes 	= new ArrayList<Integer>();
		
		for(int i = 0; i < writers.length; i++) {
			writers[i] = new DbWriter(toConsist
					,"patrick"
					,"qwert"
					,"G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\SQL\\");
			writers[i].setName("dbWriter" + (i+1));
			writers[i].start();
		}
		
		numAttributes.add(-1);
		infoGain.add(1.0);
		infoGain.add(0.75);
		infoGain.add(0.5);
		infoGain.add(0.25);
		infoGain.add(-1.0);

		if(MyUtils.isBaseline(args))
			queue = new Category(MyUtils.extractClassifierToConfigure(args)).baseline();
		if(MyUtils.isTune(args))
			queue = new Category(MyUtils.extractClassifierToConfigure(args)).tune();
		if(MyUtils.isRoughSearch(args))
			queue = new Category(MyUtils.extractClassifierToConfigure(args)).roughSearch();
		if((file=MyUtils.searchArgs("-resultfile", args)) == null)
			file  = "G:\results_param_tuning.csv"; 

		do{
			while(execute.size() < numLoocs && !queue.isEmpty()) {
				execute.add(queue.poll());
			}			
			p.performLoocv(execute
					,toConsist
					,infoGain
					,numAttributes
					,numReader
					,args[1]);
			execute.clear();
		}while(!queue.isEmpty());
		
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

		System.out.println("Finish\n===========");
	}
	
	public ConcurrentProgram(String username, String password) {
		wcontroller = new WekaController();
		dcontroller = new DataController("patrick", "qwert");
		this.username = username;
		this.password = password;
	}
	
	public ConcurrentProgram() {
		wcontroller = new WekaController();
		dcontroller = new DataController();
		username = null;
		password = null;
	}
	
	public void initializeQueue(String classifier, String[] options, ConcurrentLinkedQueue<Looc> queue) {
		queue.add(new Looc("loocv-" + new Date().getTime(), classifier, options));
	}
	
	public void performLoocv(LinkedList<Looc> queue, LoocConcurrentList toConsist,
			double infoGain, double numAttributes, double reader, String loocvResultsFile) {
		List<Double> gain = new ArrayList<Double>();
		List<Integer> num = new ArrayList<Integer>();
		gain.add(infoGain);
		num.add((int)numAttributes);
		performLoocv(queue, toConsist, gain, num, (int)reader, loocvResultsFile);
	}
	
	public void performLoocv(LinkedList<Looc> queue, LoocConcurrentList toConsist,
			List<Double> infoGain, List<Integer> numAttributes, int reader, String loocvResultsFile) {
		ConcurrentLinkedQueue<String[]> paths;
		MyConcurrentList list;
		Thread[] readers = new Thread[reader];
		Thread classifier1 = null;
		ConcurrentLinkedQueue<Looc> execute = new ConcurrentLinkedQueue<Looc>();
		execute.addAll(queue);
		
		for(double gain : infoGain) {
			for(int numAttr: numAttributes) {
				
				System.out.println("Start loocvs with infoGain " + gain + " of " + infoGain);
				paths = preparePaths();
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

				classifier1 = new ClassifyWorker(execute, list);
				classifier1.setName("classifier1");
				classifier1.start();

				// Wait for threads
				//============================================
				try {
					for(int i = 0; i < readers.length; i++)
						readers[i].join();
					list.setDone();
					classifier1.join();
				}
				catch(InterruptedException e) {
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
					System.out.println("\tlooc " + count + ":\t" + looc.toString());
					dcontroller.appendToTabSeparatedFile(loocvResultsFile
							, looc.toString() + System.getProperty("line.separator"));
					toConsist.add(looc);
				}
				execute.clear();
			}
		}
	}

	private ConcurrentLinkedQueue<String[]> preparePaths() {
		ConcurrentLinkedQueue<String[]> paths = new ConcurrentLinkedQueue<String[]>();
		for(int i = 1; i <= 159; i++)
			paths.add(new String[]{
					"G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\Arff\\loocv\\fold-" + i + "_test.arff",
					"G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\Arff\\loocv\\fold-" + i + "_train.arff"});

		return paths;
	}
	
	private static List<Setup> useParser(String configFile) {
		List<Setup> setups = null;
		try {
			ANTLRFileStream		stream = new ANTLRFileStream(configFile);
			simpleLexer 		lexer  = new simpleLexer(stream);
			CommonTokenStream	ts     = new CommonTokenStream(lexer);
			simpleParser      	parser = new simpleParser(ts);
			setups = parser.document().setups;


		} catch(Exception e) {
			e.printStackTrace();
		}
		return setups;
	}
	
	public static void createLoocvs(LinkedList<Looc> queue, Setup setup) {
		String[] arr;
		long milisec = new Date().getTime();
		if(setup.getClassifier().getOptions().isEmpty())
			queue.add(new Looc("loocv-" + milisec++
					,setup.getClassifier().getPath()
					,null));
		else
			for(List<String> l : setup.getClassifier().getOptions()){
				arr = new String[l.size()];
				for(int i=0; i<arr.length; i++)
					arr[i] = l.get(i);
				queue.add(new Looc("loocv-" + milisec++
						,setup.getClassifier().getPath()
						,arr));
			}	
	}
	
	public static LinkedList<Looc> prepareBag(LinkedList<Looc> wholeSet, int bagSize) {
		LinkedList<Looc> subset = new LinkedList<Looc>();
		while(bagSize > 0 && !wholeSet.isEmpty())
			subset.add(wholeSet.poll());
		return subset;
	}
	
	public static void offlineTest(String file) {
		int 	writer;
		double 	reader;
		double 	bag;
		double 	infoGain;
		double 	numAttributes;
		
		LinkedList<Looc> 	queue;
		List<Setup> 		setups 		= useParser(file);
		
		for(Setup s : setups) {
			queue = new LinkedList<Looc>();
			createLoocvs(queue, s);
			
			/* Retrieve Settings from configuration file
			 * ========================================== */
			if((reader = s.getRessource("reader")) == -1)
				reader = 1;
			if((writer = (int)s.getRessource("writer")) == -1)
				writer = 1;
			if((bag = s.getRessource("bag")) == -1)
				bag = 1;			
			infoGain 		= s.getRessource("infogain");
			numAttributes 	= s.getRessource("numattributes");
			
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
		System.out.println("===============\nFINISHED");
	}
	
	public static void executionFromConfig(String file, String resultfile) {
		int 	writer;
		double 	reader;
		double 	bag;
		double 	infoGain;
		double 	numAttributes;
		
		DbWriter[] 			writers;
		LinkedList<Looc> 	queue;
		List<Setup> 		setups 		= useParser(file);
		ConcurrentProgram 	p 			= new ConcurrentProgram();
		LoocConcurrentList 	toConsist	= new LoocConcurrentList();
		
		for(Setup s : setups) {
			queue = new LinkedList<Looc>();
			createLoocvs(queue, s);
			
			/* Retrieve Settings from configuration file
			 * ========================================== */
			if((reader = s.getRessource("reader")) == -1)
				reader = 1;
			if((writer = (int)s.getRessource("writer")) == -1)
				writer = 1;
			if((bag = s.getRessource("bag")) == -1)
				bag = 1;			
			infoGain 		= s.getRessource("infogain");
			numAttributes 	= s.getRessource("numattributes");
			
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
						, infoGain
						, (int)numAttributes
						, (int)reader
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
	
	public static void main(String[] args) {
		String value;
		if((value = MyUtils.searchArgs("-config", args)) != null) {
			offlineTest(value);
			String file = null;
			if((MyUtils.searchArgs("-resultfile", args)) == null)
				file = "G:\\results_param_tuning.csv";
			executionFromConfig(value, file);
		}
		else {
			oldApproach(args);
		}
	}
}