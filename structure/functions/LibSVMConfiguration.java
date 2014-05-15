package structure.functions;

import java.util.Date;
import java.util.LinkedList;

import concurrent.Looc;
import structure.Component;

public class LibSVMConfiguration implements Component {
	String classifier;
	String options;
	String optionsTuning;
	
	public LibSVMConfiguration() {
		classifier = "weka.functions.LibSVM";
		options = "-S 0 -K 0 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 40.0 -C 1.0 -E 0.001 -P 0.1 -seed 1";
	}

	@Override
	public LinkedList<Looc> baseline() {
		LinkedList<Looc> ret = new LinkedList<Looc>();
		long milisec = new Date().getTime();
		ret.add(new Looc("looc-" + milisec++, classifier,
				new String[]{"-S", "0", "-K", "0", "-D", "3", "-G", "0.0", "-R", "0.0", "-N", "0.5"
			                ,"-M", "40.0", "-C", "1.0", "-E", "0.001", "-P", "0.1", "-seed", "1"}));
		return ret;
	}
	
	/**
	 * Grid search on Parameter -C -5,-3,..15(cost) and -G -15,-2,..3 (gamma)
	 * This will take approx 20 hours
	 */
	@Override
	public LinkedList<Looc> tune() {
		LinkedList<Looc> ret = new LinkedList<Looc>();
		for(int c = -5; c <= 15; c+=2)
			for(int y = -15; y <= 3; y+=3)
				ret.add(new Looc("looc-" + new Date().getTime(), classifier,
						new String[]{"-S", "0", "-K", "0", "-D", "3", "-G", "" + Math.pow(2, y), "-R", "0.0"
					               ,"-N", "0.5","-M", "40.0", "-C", "" + Math.pow(2, c), "-E", "0.001", "-P"
					               ,"0.1","-seed", "1"}));
		return ret;
	}
	
	
}