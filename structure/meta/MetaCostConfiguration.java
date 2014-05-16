package structure.meta;

import java.util.Date;
import java.util.LinkedList;

import structure.Component;
import concurrent.Looc;

public class MetaCostConfiguration implements Component {
	private String name;
	private String options;
	
	public MetaCostConfiguration(String defaultClassifier) {
		name = "weka.classifiers.meta.MetaCost";
		options = "-cost-matrix \"[0.0 1.0 1.0 1.0 1.0; 1.0 0.0 1.0 1.0 1.0; 1.0 1.0 0.0 1.0 1.0; 1.0 1.0 1.0 0.0 1.0; 1.0 1.0 1.0 1.0 0.0]\" " +
		          "-I 10 -P 100 -S 1 -W " + defaultClassifier;
	}
	
	@Override
	public LinkedList<Looc> baseline() {
		LinkedList<Looc> ret = new LinkedList<Looc>();
		ret.add(new Looc("looc-" + new Date().getTime(), name, options.split(" ")));
		return null;
	}

	@Override
	public LinkedList<Looc> tune() {
		String[] tmp;
		LinkedList<Looc> list = new LinkedList<Looc>();
		long milisec = new Date().getTime();
		for(int it=5; it<=50; it+=5) {
			for(int p = 10; p<=100; p+=10) {
				tmp = options.split(" ");
				tmp[3] = "" + it;
				tmp[5] = "" + p;
				list.add(new Looc("looc" + milisec++, name, tmp));
			}
		}
		return list;
	}

	@Override
	public LinkedList<Looc> roughSearch() {
		// TODO Auto-generated method stub
		return null;
	}
	
}