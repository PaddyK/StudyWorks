package structure.trees;

import java.util.Date;
import java.util.LinkedList;

import structure.Component;
import concurrent.Looc;

public class LADTreeConfiguration implements Component {

	@Override
	public LinkedList<Looc> baseline() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		queue.add(new Looc("loocv-" + new Date().getTime()
				, "weka.classifiers.trees.LADTree"
				, null));
		return queue;
	}

	@Override
	public LinkedList<Looc> tune() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		long milisec = new Date().getTime();
		for(int b = 1; b <= 20; b++)
			queue.add(new Looc("looc-" + milisec++
					,"weka.classifiers.trees.LADTree"
					,new String[]{"-B", "" + b}));
		return queue;
	}
	
	@Override
	public LinkedList<Looc> roughSearch() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		long milisec = new Date().getTime();
		for(int b = 1; b <= 21; b+=5)
			queue.add(new Looc("looc-" + milisec++
					,"weka.classifiers.trees.LADTree"
					,new String[]{"-B", "" + b}));
		return queue;
	}
	
}