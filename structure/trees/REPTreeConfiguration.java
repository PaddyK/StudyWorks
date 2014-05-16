package structure.trees;

import java.util.Date;
import java.util.LinkedList;

import structure.Component;
import concurrent.Looc;

public class REPTreeConfiguration implements Component {

	@Override
	public LinkedList<Looc> baseline() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		queue.add(new Looc("loocv-" + new Date().getTime()
				, "weka.classifiers.trees.REPTree"
				, null));
		return queue;
	}

	@Override
	public LinkedList<Looc> tune() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		long milisec = new Date().getTime();
		
		for(double d = 0.00001; d < 1; d*=10)
			for(int i = 2; i <= 20; i++)
				for(int j = 2; j <= 10; j++) {
					queue.add(new Looc("loocv-" + milisec++
							, "weka.classifiers.trees.REPTree"
							, new String[]{"-M", ""+j, "-V", ""+d, "-N", "" + i, "-S", "1", "-L", "-1"}));
				}
		return queue;
	}
	
}