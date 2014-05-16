package structure.trees;

import java.util.Date;
import java.util.LinkedList;

import structure.Component;
import concurrent.Looc;

public class RandomForestConfiguration implements Component {

	@Override
	public LinkedList<Looc> baseline() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		queue.add(new Looc("loocv-" + new Date().getTime()
				, "weka.classifiers.trees.RandomForest"
				, null));
		return queue;
	}

	@Override
	public LinkedList<Looc> tune() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Looc> roughSearch() {
		// TODO Auto-generated method stub
		return null;
	}
	
}