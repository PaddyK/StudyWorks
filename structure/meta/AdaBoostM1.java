package structure.meta;

import java.util.Date;
import java.util.LinkedList;

import concurrent.Looc;
import structure.Component;

public class AdaBoostM1Configuration implements Component {
	private String name;
	private String options;
	
	public AdaBoostM1Configuration(String defaultClassifier) {
		name="weka.classifiers.meta.AdaBoostM1";
		options = "-P 100 -S 1 -I 10 -W " + defaultClassifier;
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
		for(int it=5; it<=50; it+=5) {
			for(int p = 10; p<=100; p+=10) {
				tmp = options.split(" ");
				tmp[5] = "" + it;
				tmp[2] = "" + p;
				list.add(new Looc("looc" + new Date().getTime(), name, tmp));
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}