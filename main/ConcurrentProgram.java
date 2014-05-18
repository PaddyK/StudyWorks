package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import data.DataController;
import classification.WekaController;
import concurrent.*;
import structure.Category;

public class ConcurrentProgram {

	private WekaController wcontroller;
	private DataController dcontroller;
	private String username;
	private String password;

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
			List<Double> infoGain, List<Integer> numAttributes, int reader) {
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
					dcontroller.appendToTabSeparatedFile("G:\\Results_Param_Tuning.csv"
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

	public static void main(String[] args) {
		int numLoocs = 6;
		int numReader = 4;
		int numDbWriter = 6;

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

		if(Utils.isBaseline(args))
			queue = new Category(Utils.extractClassifierToConfigure(args)).baseline();
		if(Utils.isTune(args))
			queue = new Category(Utils.extractClassifierToConfigure(args)).tune();
		if(Utils.isRoughSearch(args))
			queue = new Category(Utils.extractClassifierToConfigure(args)).roughSearch();

		do{
			while(execute.size() < numLoocs && !queue.isEmpty()) {
				execute.add(queue.poll());
			}			
			p.performLoocv(execute
					,toConsist
					,infoGain
					,numAttributes
					,numReader);
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
}