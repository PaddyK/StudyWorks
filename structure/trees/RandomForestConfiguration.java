package structure.trees;

import java.util.Date;
import java.util.LinkedList;

import structure.Component;
import concurrent.Looc;

public class RandomForestConfiguration implements Component {
	
	private String classifier;
	public RandomForestConfiguration() {
		classifier = "weka.classifiers.trees.RandomForest";
	}
	
	@Override
	public LinkedList<Looc> baseline() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		queue.add(new Looc("loocv-" + new Date().getTime()
				, classifier
				, null));
		return queue;
	}

	@Override
	public LinkedList<Looc> tune() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		long milisec = new Date().getTime();
		for(int k = 0; k < 24; k+=8)
			for(int i = 1; i < 50; i++)
				queue.add(new Looc("looc-" + milisec
						,classifier
						,new String[]{"-I", "" + i, "-K", "" + k, "-S", "1"}));
		return queue;
	}

	@Override
	public LinkedList<Looc> roughSearch() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		long milisec = new Date().getTime();
		for(int k = 0; k < 24; k+=8)
			for(int i = 1; i < 51; i+=5)
				queue.add(new Looc("looc-" + milisec
						,classifier
						,new String[]{"-I", "" + i, "-K", "" + k, "-S", "1"}));
		return queue;
	}
	
}