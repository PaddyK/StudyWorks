package structure;

import structure.functions.LibSVMConfiguration;
import structure.lazy.KStarConfiguration;
import structure.meta.*;

public class Factory {
	private String defaultClassifier;
	
	public Factory() {
		defaultClassifier = "weka.classifiers.trees.REPTree -- -M 2 -V 0.001 -N 3 -S 1 -L -1";
	}
	
	public Component produceConfiguration(String classifierName) {
		Component component;
		switch(classifierName) {
		case "AdaBoostM1": component = new AdaBoostM1Configuration(defaultClassifier); break;
		case "LibSVM": component = new LibSVMConfiguration(); break;
		case "KStar" : component = new KStarConfiguration();
		default: component = null;
		}
		return component;
	}
}