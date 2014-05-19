package structure.trees;

import java.util.Date;
import java.util.LinkedList;

import structure.Component;
import concurrent.Looc;

public class ADTreeConfiguration implements Component {
	
	private String classifier;
	
	public ADTreeConfiguration() {
		classifier = "weka.classifiers.trees.ADTree";
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
		for(int e = 0; e <= 3; e++)
			for(int b = 1; b <= 20; b++)
				queue.add(new Looc("looc-" + milisec++
						,classifier
						,new String[]{"-B", "" + b, "-E", "" + e}));
		return queue;
	}
	
	@Override
	public LinkedList<Looc> roughSearch() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		long milisec = new Date().getTime();
		for(int e = 0; e <= 3; e++)
			for(int b = 1; b <= 21; b+=5)
				queue.add(new Looc("looc-" + milisec++
						,classifier
						,new String[]{"-B", "" + b, "-E", "" + e}));
		return queue;
	}
	
}