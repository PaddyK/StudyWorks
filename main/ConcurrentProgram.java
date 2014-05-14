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
	
	public void tuneREPTree(boolean tuneVar, boolean tuneFolds, boolean tuneMinNum,
			boolean tuneCombination, LinkedList<Looc> queue) {
		if(tuneVar)
		for(double d = 0.00001; d < 1; d*=10) {
			queue.add(new Looc("loocv-" + new Date().getTime()
					, "weka.classifiers.trees.REPTree"
					, new String[]{"-M", "3", "-V", "" + d, "-N", "3", "-S", "1", "-L", "-1"}));
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if(tuneFolds)
		for(int i = 2; i <= 10; i++) {
			queue.add(new Looc("loocv-" + new Date().getTime()
					, "weka.classifiers.trees.REPTree"
					, new String[]{"-M", "2", "-V", "0.0001", "-N", ""+i, "-S", "1", "-L", "-1"}));
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(tuneMinNum)
		for(int i = 1; i <= 10; i++) {
			queue.add(new Looc("loocv-" + new Date().getTime()
					, "weka.classifiers.trees.REPTree"
					, new String[]{"-M", ""+i, "-V", "0.001", "-N", "3", "-S", "1", "-L", "-1"}));
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(tuneCombination)
		for(double d = 0.00001; d < 1; d*=10)
			for(int i = 2; i <= 20; i++)
				for(int j = 2; j <= 10; j++) {
					queue.add(new Looc("loocv-" + new Date().getTime()
							, "weka.classifiers.trees.REPTree"
							, new String[]{"-M", ""+j, "-V", ""+d, "-N", "" + i, "-S", "1", "-L", "-1"}));
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}
	
	public void tuneSimpleLogistic(LinkedList<Looc> queue) {
		for(int hstop = 20; hstop <= 100; hstop+=20)
			for(int iter = 200; iter <=1000; iter+=200)
				for(double d = 0.1; d < 1; d+=0.1) {
					queue.add(new Looc("looc-" + new Date().getTime()
							,"weka.classifiers.functions.SimpleLogistic"
							,new String[]{"-I","0","-M","" + iter,"-H", "" + hstop,"-W","" + d}));
					try{Thread.sleep(2);}
					catch(Exception e){e.printStackTrace();}
				}
	}
	
	public void tuneLibSVM(LinkedList<Looc> queue) {
		for(int c = -5; c < 16; c+=1)
			for(int y = -15; y < 4; y+=1)
				queue.add(new Looc("test-svm" + new Date().getTime(), "weka.classifiers.functions.LibSVM",
						new String[]{"-S", "0", "-K", "0", "-D", "3", "-G", "" + Math.pow(2, y), "-R", "0.0", "-N", "0.5"
						,"-M", "40.0", "-C", "" + Math.pow(2, c), "-E", "0.001", "-P", "0.1", "-seed", "1"}));
	}
	
	public void tuneAdaBoostM1(LinkedList<Looc> queue, String src) {
		// "G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\dbExports\\besAccuracy.csv"
		LinkedList<String[]> list = dcontroller.readClassifierClassifierOptionPairs(src);
		String tmp[];
		String tmp2[];
		String options[];
		while((tmp = list.poll()) != null) {
			tmp2 = tmp[1].split(" ");
			options = new String[9 + tmp2.length + 1];
			options[0] = "-P";
			options[1] = "100";
			options[2] = "-S";
			options[3] = "1";
			options[4] = "-I";
			options[5] = "10";
			options[6] = "-W";
			options[7] = tmp[0];
			options[8] = "--";
			for(int i = 0; i < tmp2.length; i++)
				options[i + 9] = tmp2[i];
			options[options.length - 1] = "-P";
			queue.add(new Looc("looc" + new Date().getTime()
					,"weka.classifiers.meta.AdaBoostM1"
					,options));
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void tuneMetaCost(LinkedList<Looc> queue) {
		String[] options = new String[22];
		options[0] = "-cost-matrix";
		options[1] = "\"[0.0 1.0 1.0 1.0 1.0; " +
				"1.0 0.0 1.0 1.0 1.0; " +
				"1.0 1.0 0.0 1.0 1.0; " +
				"1.0 1.0 1.0 0.0 1.0; " +
				"1.0 1.0 1.0 1.0 0.0]\"";
		options[2] = "-I";
		options[3] = "10";
		options[4] = "-P";
		options[5] = "100";
		options[6] = "-S";
		options[7] = "1";
		options[8] = "-W";
		options[9] = "weka.classifiers.trees.REPTree";
		options[10] = "--";
		options[11] = "-M";
		options[12] = "2";
		options[13] = "-V";
		options[14] = "0.001";
		options[15] = "-N";
		options[16] = "3";
		options[17] = "-S";
		options[18] = "1";
		options[19] = "-L";
		options[20] = "1";
		options[21] = "-P";
		Looc looc = new Looc("looc" + new Date().getTime()
				,"weka.classifiers.meta.MetaCost"
				,options);
		System.out.println(looc.getOptionString());
		queue.add(looc);
		
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
				
				System.out.println("Start loocvs " + infoGain);
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
				System.out.println("Finished classifying");
				System.out.println("Start persisting in database");
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
		
		System.out.println("RUNS");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
//		int numLoocs = 5;
//		int numDbWriter = 8;
//		int numReader = 4;
//		
//		LoocConcurrentList toConsist = new LoocConcurrentList();
//		LinkedList<Looc> queue = new LinkedList<Looc>();
//		LinkedList<Looc> execute = new LinkedList<Looc>();
//		ArrayList<Integer> numAttributes = new ArrayList<Integer>();
//		ArrayList<Double> infoGain = new ArrayList<Double>();
//		ConcurrentProgram p = new ConcurrentProgram("patrick","qwert");
//		DbWriter[] writers = new DbWriter[numDbWriter];
//		
//		for(int i = 0; i < writers.length; i++) {
//			writers[i] = new DbWriter(toConsist
//					,"patrick"
//					,"qwert"
//					,"G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\SQL\\");
//			writers[i].setName("dbWriter" + (i+1));
//			writers[i].start();
//		}
//		
//		numAttributes.add(-1);
//		infoGain.add(0.2);
////		p.tuneAdaBoostM1(queue, "G:\\Documents\\DHBW\\5Semester\\Study_Works\\antibodies\\Data Analysis\\dbExports\\besAccuracy.csv");
////		p.tuneMetaCost(queue);
////		p.tuneSimpleLogistic(queue);
//		p.tuneREPTree(false, false, true, false, queue);
//		do{
//			while(execute.size() < numLoocs && !queue.isEmpty()) {
//				execute.add(queue.poll());
//			}			
//			p.performLoocv(execute
//					,toConsist
//					,infoGain
//					,numAttributes
//					,numReader);
//			execute.clear();
//		}while(!queue.isEmpty());
//		
//		for(int i = 0; i < writers.length; i++)
//			writers[i].interrupt();
//		try {
//			for(int i = 0; i < writers.length; i++)
//				writers[i].join();
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
//			System.err.println("Closing Interrupt Exception on Thread " + Thread.currentThread().getName());
//			e.printStackTrace();
//		}
//
//		System.out.println("Finish\n===========");
	}
}