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
		case "AdaBoostM1"						: component = "weka.classifiers.meta.AdaBoostM1"; break;
		case "ADTree"							: component = "weka.classifiers.trees.ADTree"; break;
		case "Bagging" 							: component = "weka.classifiers.meta.Bagging"; break;
		case "BayesNet"							: component = "weka.classifiers.bayes.BayesNet"; break;
		case "BFTree"							: component = "weka.classifiers.trees.BFTree"; break;
		case "CitationKNN"						: component = "weka.classifiers.mi.CitationKNN"; break;
		case "ComplementNaiveBayes"				: component = "weka.classifiers.bayes.ComplementNaiveBayes"; break;
		case "ConjunctiveRule"					: component = "weka.classifiers.rules.ConjunctiveRule"; break;
		case "DecisionStump"					: component = "weka.classifiers.trees.DecisionStump"; break;
		case "DecisionTable"					: component = "weka.classifiers.rules.DecisionTable"; break;
		case "DTNB"								: component = "weka.classifiers.rules.DTNB"; break;
		case "DMNBtext"							: component = "weka.classifiers.bayes.DMNBtext"; break;
		case "FT"								: component = "weka.classifiers.trees.FT"; break;
		case "IB1"								: component = "weka.classifiers.lazy.IB1"; break;
		case "IBk"								: component = "weka.classifiers.lazy.IBk"; break;
		case "J48"								: component = "weka.classifiers.trees.J48"; break;
		case "J48graft"							: component = "weka.classifiers.trees.J48graft"; break;
		case "JRip"								: component = "weka.classifiers.rules.JRip"; break;
		case "KStar" 							: component = "weka.classifiers.lazy.KStar"; break;
		case "LADTree"							: component = "weka.classifiers.trees.LADTree"; break;
		case "LibLINEAR"						: component = "weka.classifiers.functions.LibLinear"; break;
		case "LibSVM"							: component = "weka.classifiers.functions.LibSVM"; break;
		case "LMT" 								: component = "weka.classifiers.trees.LMT"; break;
		case "Logistic"							: component = "weka.classifiers.functions.Logistic"; break;
		case "LWL"								: component = "weka.classifiers.lazy.LWL"; break;
		case "MetaCost" 						: component = "weka.classifiers.meta.MetaCost"; break;
		case "MISMO"							: component = "weka.classifiers.mi.MISMO"; break;
		case "MIWrapper"						: component = "weka.classifiers.mi.MIWrapper"; break;
		case "MultilayerPerceptron" 			: component = "weka.classifiers.functions.MultilayerPerceptron"; break;
		case "NBTree"							: component = "weka.classifiers.trees.NBTree"; break;
		case "NaiveBayes"						: component = "weka.classifiers.bayes.NaiveBayes"; break;
		case "NaiveBayesMultinomial"			: component = "weka.classifiers.bayes.NaiveBayesMultinomial"; break;
		case "NaiveBayesMultinomialUpdateable"	: component = "weka.classifiers.bayes.NaiveBayesMultinomialUpdateable"; break;
		case "NaiveBayesSimple"					: component = "weka.classifiers.bayes.NaiveBayesSimple"; break;
		case "NaiveBayesSimpleUpdateable"		: component = "weka.classifiers.bayes.NaiveBayesSimpleUpdateable"; break;
		case "NNge"								: component = "weka.classifiers.rules.NNge"; break;
		case "OneR"								: component = "weka.classifiers.rules.OneR"; break;
		case "PART"								: component = "weka.classifiers.rules.PART"; break;
		case "RandomForest" 					: component = "weka.classifiers.trees.RandomForest"; break;
		case "RandomTree"						: component = "weka.classifiers.trees.RandomTree"; break;
		case "RBFNetwork"						: component = "weka.classifiers.functions.RBFNetwork"; break;
		case "REPTree" 							: component = "weka.classifiers.trees.REPTree"; break;
		case "Ridor"							: component = "weka.classifiers.rules.Ridor"; break;
		case "SimpleLogistic" 					: component = "weka.classifiers.functions.SimpleLogistic"; break;
		case "SimpleMI"							: component = "weak.classifiers.mi.SimpleMI"; break;
		case "Stacking"							: component = "weka.classifiers.meta.Stacking"; break;
		case "SMO"								: component = "weka.classifiers.functions.SMO"; break;
		case "ZeroR"							: component = "weka.classifiers.rules.ZeroR"; break;
		default: component = null;
		}
		return component;
	}
	
	public void setMetaClassifier(String classifier) {
		defaultClassifier = classifier;
	}
}