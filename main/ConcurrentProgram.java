package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import data.DataController;
import classification.WekaController;
import concurrent.*;

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

	public void configureRandomForest(ConcurrentLinkedQueue<Looc> queue, int trees) {
		queue.add(new Looc("loocv_RandomForest_" + new Date().getTime()
				,"weka.classifiers.trees.RandomForest"
				,new String[]{"-I","" + trees,"-K","0","-S","1"}));
	}
	
	public void configureNaiveBayes(ConcurrentLinkedQueue<Looc> queue) {
		queue.add(new Looc("loocv_NaiveBayes_" + new Date().getTime()
				, "weka.classifiers.bayes.NaiveBayes"
				, new String[]{}));		
	}
	
	public void configureNaiveBayesUpdateable(ConcurrentLinkedQueue<Looc> queue) {
		queue.add(new Looc("loocv_NaiveBayesUpdate_" + new Date().getTime()
				, "weka.classifiers.bayes.NaiveBayesUpdateable"
				, new String[]{}));

	}

	public void configureSVM(ConcurrentLinkedQueue<Looc> queue, int kernel, int c) {
		queue.add(new Looc("loocv-" + new Date().getTime(), "weka.classifiers.functions.LibSVM",
				new String[]{"-S","0","-K","" + kernel,"-D","3","-G","0.0","-R","0.0","-N","0.5","-M","40",
			"-C","" + c,"-E","0.001","-P","0.1","-seed","1"}));
	}
	
	public void configureSimpleLogistic(ConcurrentLinkedQueue<Looc> queue) {
		queue.add(new Looc("loocv-" + new Date().getTime(), "weka.classifiers.functions.SimpleLogistic",
				new String[]{"-I", "0", "-M", "500", "-H", "50", "-W" ,"0.0"}));
	}
	
	public void configureBayesNet(ConcurrentLinkedQueue<Looc> queue, int estimator, int search) {
//		String est = "weka.classifiers.bayes.net.estimate.";
//		String srch = "weka.classifiers.bayes.net.search.local.";
//		switch(estimator) {
//		case 0: est += "SimpleEstimator -A 0.5"; break;
//		case 1: est += "MultiNomialBMAEstimator -k2 -A 0.5"; break;
//		case 2: est += "BMAEstimator -A 0.5"; break;
//		case 3: est += "BayesNetEstimator -A 0.5"; break;
//		default: est += "SimpleEstimator -A 0.5";
//		}
//		
//		switch(search) {
//		case 0: srch += "GeneticSearch -L 10 -A 100 -U 10 -R 1 -M -C -S BAYES"; break;
//		case 1: srch += "HillClimber -P 1 -S BAYES"; break;
//		case 2: srch += "K2 -P 1 -S BAYES"; break;
//		case 3: srch += "LAGDHillClimber -L 2 -G 5 -P 1 -S BAYES"; break;
//		case 4: srch += "RepeatedHillClimber -U 10 -A 1 -P 1 -S BAYES";
//		case 5: srch += "SimulatedAnnealing -A 10.0 -U 10000 -D 0.999 -R 1 -S BAYES"; break;
//		case 6: srch += "TabuSearch -L 5 -U 10 -P 1 -S BAYES"; break;
//		case 7: srch += "TAN -S BAYES"; break;
//		default: srch += "K2 -P 1 -S BAYES";
//		}
//		queue.add(new Looc("loocv-" + new Date().getTime(), "weka.classifiers.bayes.BayesNet",
//						new String[]{"-D","-Q",srch,"-E", est}));
		Looc l = new Looc("loocv-BayesNet-" + new Date().getTime(), "weka.classifiers.bayes.BayesNet",null);
//		l.setDiscretize(true);
		queue.add(l);
	}
	
	public void configureKStar(ConcurrentLinkedQueue<Looc> queue) {
		queue.add(new Looc("loocv-Kstar-" + new Date().getTime(), "weka.classifiers.lazy.KStar",
				new String[]{"-B", "20", "-M", "a"}));
	}
	
	public void configureRBFNetwork(ConcurrentLinkedQueue<Looc> queue, int cluster, double stdDev) {
		queue.add(new Looc("loocv_NaiveBayesUpdate_" + new Date().getTime()
		,"weka.classifiers.functions.RBFNetwork"
		,new String[]{"-B", "" + cluster, "-S", "1", "-R", "1.0E-8", "-M", "-1", "-W", "" + stdDev}));
	}
	
	public void configureSMO(ConcurrentLinkedQueue<Looc> queue) {
		queue.add(new Looc("loocv-BayesNet-" + new Date().getTime(), "weka.classifiers.functions.SMO",null));
	}
	
	public void configureIB1(ConcurrentLinkedQueue<Looc> queue) {
		queue.add(new Looc("loocv-IB1-" + new Date().getTime(), "weka.classifiers.lazy.IB1",null));
	}
	
	public void configureIBk(ConcurrentLinkedQueue<Looc> queue) {
		queue.add(new Looc("loocv-IBk-" + new Date().getTime(), "weka.classifiers.lazy.IBk",null));
	}
	
	public void configureLWL(ConcurrentLinkedQueue<Looc> queue) {
		queue.add(new Looc("loocv-LWL-" + new Date().getTime(), "weka.classifiers.lazy.LWL",null));
	}
	
	public void defaultSettings(String classifier, ConcurrentLinkedQueue<Looc> queue) {
		queue.add(new Looc("loocv-" + new Date().getTime(), classifier,null));
	}
	
	public void performLoocv(String classifier) {
		LoocConcurrentList toConsist = new LoocConcurrentList();

		ConcurrentLinkedQueue<Looc> queue;
		ConcurrentLinkedQueue<String[]> paths;
		MyConcurrentList list;
		double infoGain;
		int features = -1;
		Thread reader1 = null;
		Thread reader2 = null;
		Thread classifier1 = null;
		
		DbWriter dbWriter = new DbWriter(toConsist
				,username
				,password
				,"G:\\Documents\\DHBW\5Semester\\Study_Works\\antibodies\\Data Analysis\\SQL\\");
		dbWriter.start();

		for(infoGain = 1; infoGain >= 0; infoGain-=0.1) {
			if(infoGain == 0) infoGain = -1;

			System.out.println("Start loocvs " + infoGain);
			queue = new ConcurrentLinkedQueue<Looc>();
			paths = preparePaths();
			list = new MyConcurrentList();

			// Prepare classifier
			//=================================
			defaultSettings(classifier, queue);

			// Start threads
			//=======================================

			reader1 = new ReadWorker(list, paths, features, infoGain);
			reader1.setName("reader1");
			reader1.start();
			reader2 = new ReadWorker(list, paths, features, infoGain);
			reader2.setName("reader2");
			reader2.start();

			classifier1 = new ClassifyWorker(queue, list);
			classifier1.setName("classifier1");
			classifier1.start();

			// Wait for threads
			//============================================
			try {
				reader1.join();
				reader2.join();
				list.setDone();
				classifier1.join();
			}
			catch(InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
				break;
			}
			System.out.println("Finished classifying");
			System.out.println("Start persisting in database");
			int count=0;

			// Persist results
			//===============================================
			for(Looc looc : queue) {
				count++;
				looc.setFeatures(features);
				looc.setInfoGain(infoGain);
				looc.consolidateFolds(5);
				System.out.println("\tlooc " + count + ":\t" + looc.toString());
				//				try {
				//					dcontroller.executeBatch(looc.generateInsertStatement());
				//				}
				//				catch(Exception e) {
				//					dcontroller.writeToTabSeparatedFile("G:\\sqldump"+count + "_" + new Date().getTime(), implode(looc.generateInsertStatement()));
				//				}
				//				System.out.println("\t" + (double)count/(double)size + "%");
				//				dcontroller.writeToTabSeparatedFile("G:\\sqldump"+count + "_" + new Date().getTime(), implode(looc.generateInsertStatement()));
				dcontroller.appendToTabSeparatedFile("G:\\Results.csv", looc.toString() + System.getProperty("line.separator"));
				toConsist.add(looc);
			}
		}
		dbWriter.interrupt();
		try {
			dbWriter.join();
		} catch (InterruptedException e) {
			dbWriter.interrupt();
			e.printStackTrace();
		}
	}
	
	private String implode(List<String> list) {
		String ret = "";
		for(String s : list)
			ret += s + System.getProperty("line.separator");
		return ret;
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
		// TODO Auto-generated method stub
		String prefix = "weka.classifiers.rules.";
		String suffix = "ConjunctiveRule";
//		suffix = "DecisionTable";			// yet to do
//		suffix = "DTNB";					// Out of memory (one reader only)
//		suffix = "JRip";
//		suffix = "NNge";
//		suffix = "OneR";
//		suffix = "PART";					// Out of memory (one reader only)
//		suffix = "Ridor";					// Out of memory first fold
		prefix = "weka.classifiers.trees.";

//		list.add("J48");
//		list.add("RandomTree");
//		list.add("BFTree");
//		list.add("DecisionStump");
//		list.add("FT");
//		list.add("J48graft");
//		list.add("LADTree");
//		list.add("LMT");
//		list.add("NBTree");					^		//Out of memory and then took forever
//		list.add("REPTree");
//		list.add("SimpleCart");
//		suffix = "RandomForest";
		
		prefix = "weka.classifiers.functions.";
		suffix = "Logistic";
		suffix = "SimpleLogistic";
		
		ConcurrentProgram p = new ConcurrentProgram();
		p.performLoocv(prefix+suffix);
		System.out.println("Finish");
	}
}