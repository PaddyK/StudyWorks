package structure.trees;

import java.util.Date;
import java.util.LinkedList;

import structure.Component;
import concurrent.Looc;

public class LMTConfiguration implements Component {

	@Override
	public LinkedList<Looc> baseline() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		queue.add(new Looc("loocv-" + new Date().getTime()
				, "weka.classifiers.trees.LMT"
				, null));
		return queue;
	}

	@Override
	public LinkedList<Looc> tune() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		long milisec = new Date().getTime();
		for(double beta = 0; beta < 1; beta+=0.1)
			for(int istc = 0; istc <= 30; istc+=5)
				queue.add(new Looc("looc-" + milisec++
						,"weka.classifiers.LMT"
						,new String[]{"-C", "-I", "-1", "-M", "" + istc, "-W", "" + beta}));

		for(double beta = 0; beta < 1; beta+=0.25)
			for(int istc = 0; istc <= 30; istc+=5)
				queue.add(new Looc("looc-" + milisec++
						,"weka.classifiers.LMT"
						,new String[]{"-P", "-I", "-1", "-M", "" + istc, "-W", "" + beta}));

		for(double beta = 0; beta < 1; beta+=0.25)
			for(int istc = 0; istc <= 30; istc+=5)
				queue.add(new Looc("looc-" + milisec++
						,"weka.classifiers.LMT"
						,new String[]{"-C", "-P", "-I", "-1", "-M", "" + istc, "-W", "" + beta}));
		
		for(double beta = 0; beta < 1; beta+=0.25)
			for(int istc = 0; istc <= 30; istc+=5)
				queue.add(new Looc("looc-" + milisec++
						,"weka.classifiers.LMT"
						,new String[]{"-I", "-1", "-M", "" + istc, "-W", "" + beta}));
		return queue;
	}
	
	@Override
	public LinkedList<Looc> roughSearch() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		long milisec = new Date().getTime();
		for(double beta = 0; beta < 1; beta+=0.25)
			for(int istc = 0; istc <= 30; istc+=5)
				queue.add(new Looc("looc-" + milisec++
						,"weka.classifiers.LMT"
						,new String[]{"-C", "-I", "-1", "-M", "" + istc, "-W", "" + beta}));

		for(double beta = 0; beta < 1; beta+=0.25)
			for(int istc = 0; istc <= 30; istc+=5)
				queue.add(new Looc("looc-" + milisec++
						,"weka.classifiers.LMT"
						,new String[]{"-P", "-I", "-1", "-M", "" + istc, "-W", "" + beta}));

		for(double beta = 0; beta < 1; beta+=0.25)
			for(int istc = 0; istc <= 30; istc+=5)
				queue.add(new Looc("looc-" + milisec++
						,"weka.classifiers.LMT"
						,new String[]{"-C", "-P", "-I", "-1", "-M", "" + istc, "-W", "" + beta}));
		
		for(double beta = 0; beta < 1; beta+=0.25)
			for(int istc = 0; istc <= 30; istc+=5)
				queue.add(new Looc("looc-" + milisec++
						,"weka.classifiers.LMT"
						,new String[]{"-I", "-1", "-M", "" + istc, "-W", "" + beta}));
		return queue;
	}
	
}