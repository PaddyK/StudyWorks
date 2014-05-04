package concurrent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;

/**
 * Thread to read test and training sets from file.
 * The task of this thread is to read all sets from file and create a MyWekaSet. Additionally to
 * reading from file, feature selection is performed. This class was designed with the thought have
 * having multiple threads doing this.
 * @author Patrick
 *
 */
public class ReadWorker extends Thread{
	/**
	 * buffer in which created MyWekaLists are pushed
	 */
	private MyConcurrentList buffer;
	/**
	 * Contains paths to test and training set.
	 */
	private ConcurrentLinkedQueue<String[]> paths;
	/**
	 * Number of features which should remain when using feature selection
	 */
	private int featuresToSelect;
	/**
	 * Value under which features are discarded
	 */
	private double infoGain;
	
	/**
	 * Constructor for ReadWorker runnable
	 * @param buffer	Buffer where sets are to be stored
	 * @param paths		Each array assumes the path for the test set to be in the first array
	 * 					slot and the path to the training set in the second slot
	 * @param features	Number of features to retain
	 * @param gain		Threshold at which attributes may be discarded
	 */
	public ReadWorker(MyConcurrentList buffer, ConcurrentLinkedQueue<String[]> paths,
			int features, double gain) {
		this.buffer = buffer;
		this.paths = paths;
		featuresToSelect = features;
		infoGain = gain;
	}
	
	@Override
	public void run() {
		String[] patharr;
		MyWekaSet set;

		while(!paths.isEmpty() && !isInterrupted()) {
			patharr = paths.poll();
			System.out.println(patharr[0]);
			try {
				set = new MyWekaSet(readInstances(patharr[0]), readInstances(patharr[1]));
				featureSelection(set);
				buffer.add(set);
			}
			catch(IOException e) {
				e.printStackTrace();
				buffer.setDone();
				return;
			}
			catch(OutOfMemoryError e) {
				System.err.println("Out of memory error occured on files " + patharr[0] + 
						"and " + patharr[1]);
				e.printStackTrace();
				return;
			}
			catch(Exception e) {
				e.printStackTrace();
				buffer.setDone();
				return;
			}
		}		
	}
	
	/**
	 * Reads an arff file and returns contents as Instances
	 * @param path			Path to arff file
	 * @return				Instances derived from arff file
	 * @throws IOException	Exceptione when file does not exist or something else regarding IO went wrong
	 */
	private Instances readInstances(String path) throws IOException {
		BufferedReader reader = null;
		File src = new File(path);
		Instances instances = null;
		
		if(!src.exists()) 
			System.err.println("File " + path + " to load data from does not exist!");
		else{
			reader = new BufferedReader(new FileReader(src));
			instances = new Instances(reader);
			reader.close();
		}
		instances.setClassIndex(instances.numAttributes() - 1);
		return instances;
	}
	
	/**
	 * Perform feauture selection using Ranker
	 * @param set			MyWekaSet containing test and training set instances
	 */
	private void featureSelection(MyWekaSet set) throws Exception, OutOfMemoryError {
		AttributeSelection sel = new AttributeSelection();
		InfoGainAttributeEval attributeEvaluator = new InfoGainAttributeEval();
		Ranker ranker = new Ranker();
		
		if(infoGain == -1) 
			infoGain = -1.7976931348623157 * Math.pow(10, 308);
		
		attributeEvaluator.setBinarizeNumericAttributes(false);
		attributeEvaluator.setMissingMerge(true);
		
		ranker.setGenerateRanking(true);
		ranker.setNumToSelect(featuresToSelect);
		ranker.setThreshold(infoGain);
		
		sel.setEvaluator(attributeEvaluator);
		sel.setSearch(ranker);
		try{
			sel.setInputFormat(set.train());
			set.setTrain(Filter.useFilter(set.train(), sel));
			set.setTest(Filter.useFilter(set.test(), sel));			
		}
		catch(OutOfMemoryError e) {
			throw new OutOfMemoryError(e.getMessage());
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
}