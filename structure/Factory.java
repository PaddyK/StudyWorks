package structure;

import structure.functions.*;
import structure.lazy.*;
import structure.meta.*;
import structure.trees.*;

public class Factory {
	private String defaultClassifier;
	
	public Factory() {
		defaultClassifier = "weka.classifiers.trees.REPTree -- -M 2 -V 0.001 -N 3 -S 1 -L -1";
	}
	
	public Component produceConfiguration(String classifierName) {
		Component component;
		switch(classifierName) {
		case "AdaBoostM1"		: component = new AdaBoostM1Configuration(defaultClassifier); break;
		case "ADTree"			: component = new ADTreeConfiguration(); break;
		case "Bagging" 			: component = new BaggingConfiguration(defaultClassifier); break;
		case "KStar" 			: component = new KStarConfiguration(); break;
		case "LADTree"			: component = new LADTreeConfiguration(); break;
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
	public static String getClassifierPath(String classifierName) {
		String component;
		switch(classifierName) {
		case "AdaBoostM1"		: component = "weka.classifiers.meta.AdaBoostM1"; break;
		case "ADTree"			: component = "weka.classifiers.trees.ADTree"; break;
		case "Bagging" 			: component = "weka.classifiers.meta.Bagging"; break;
		case "J48"				: component = "weka.classifiers.trees.J48"; break;
		case "KStar" 			: component = "weka.classifiers.lazy.KStar"; break;
		case "LADTree"			: component = "weka.classifiers.trees.LADTree"; break;
		case "LibSVM"			: component = "weka.classifiers.functions.LibSVM"; break;
		case "LMT" 				: component = "weka.classifiers.trees.LMT"; break;
		case "MetaCost" 		: component = "weka.classifiers.meta.MetaCost"; break;
		case "NaiveBayes"		: component = "weka.classifiers.bayes.NaiveBayes"; break;
		case "RandomForest" 	: component = "weka.classifiers.trees.RandomForest"; break;
		case "REPTree" 			: component = "weka.classifiers.trees.REPTree"; break;
		case "SimpleLogistic" 	: component = "weka.classifiers.functions.SimpleLogistic"; break;
		default: component = null;
		}
		return component;
	}
	
	public void setMetaClassifier(String classifier) {
		defaultClassifier = classifier;
	}
}