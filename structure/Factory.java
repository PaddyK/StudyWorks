package structure;

import structure.functions.LibSVMConfiguration;
import structure.functions.SimpleLogisticConfiguration;
import structure.lazy.KStarConfiguration;
import structure.meta.*;
import structure.trees.LMTConfiguration;
import structure.trees.REPTreeConfiguration;
import structure.trees.RandomForestConfiguration;

public class Factory {
	private String defaultClassifier;
	
	public Factory() {
		defaultClassifier = "weka.classifiers.trees.REPTree -- -M 2 -V 0.001 -N 3 -S 1 -L -1";
	}
	
	public Component produceConfiguration(String classifierName) {
		Component component;
		switch(classifierName) {
		case "AdaBoostM1"		: component = new AdaBoostM1Configuration(defaultClassifier); break;
		case "Bagging" 			: component = new BaggingConfiguration(defaultClassifier); break;
		case "KStar" 			: component = new KStarConfiguration(); break;
		case "LibSVM"			: component = new LibSVMConfiguration(); break;
		case "LMT" 				: component = new LMTConfiguration(); break;
		case "MetaCost" 		: component = new MetaCostConfiguration(defaultClassifier); break;
		case "RandomForest" 	: component = new RandomForestConfiguration(); break;
		case "REPTree" 			: component = new REPTreeConfiguration(); break;
		case "SimpleLogistic" 	: component = new SimpleLogisticConfiguration(); break;
		default: component = null;
		}
		return component;
	}
	
	public void setMetaClassifier(String classifier) {
		defaultClassifier = classifier;
	}
}