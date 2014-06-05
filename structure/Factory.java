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
		switch(classifierName.toLowerCase()) {
		case "adaboostm1"						: component = "weka.classifiers.meta.AdaBoostM1"; break;
		case "adtree"							: component = "weka.classifiers.trees.ADTree"; break;
		case "bagging" 							: component = "weka.classifiers.meta.Bagging"; break;
		case "bayesnet"							: component = "weka.classifiers.bayes.BayesNet"; break;
		case "bftree"							: component = "weka.classifiers.trees.BFTree"; break;
		case "citationknn"						: component = "weka.classifiers.mi.CitationKNN"; break;
		case "complementnaivebayes"				: component = "weka.classifiers.bayes.ComplementNaiveBayes"; break;
		case "conjunctiverule"					: component = "weka.classifiers.rules.ConjunctiveRule"; break;
		case "decisionstump"					: component = "weka.classifiers.trees.DecisionStump"; break;
		case "decisiontable"					: component = "weka.classifiers.rules.DecisionTable"; break;
		case "dtnb"								: component = "weka.classifiers.rules.DTNB"; break;
		case "dmnbtext"							: component = "weka.classifiers.bayes.DMNBtext"; break;
		case "ft"								: component = "weka.classifiers.trees.FT"; break;
		case "ib1"								: component = "weka.classifiers.lazy.IB1"; break;
		case "ibk"								: component = "weka.classifiers.lazy.IBk"; break;
		case "j48"								: component = "weka.classifiers.trees.J48"; break;
		case "j48graft"							: component = "weka.classifiers.trees.J48graft"; break;
		case "jrip"								: component = "weka.classifiers.rules.JRip"; break;
		case "kstar" 							: component = "weka.classifiers.lazy.KStar"; break;
		case "ladtree"							: component = "weka.classifiers.trees.LADTree"; break;
		case "liblinear"						: component = "weka.classifiers.functions.LibLINEAR"; break;
		case "libsvm"							: component = "weka.classifiers.functions.LibSVM"; break;
		case "lmt" 								: component = "weka.classifiers.trees.LMT"; break;
		case "logistic"							: component = "weka.classifiers.functions.Logistic"; break;
		case "lwl"								: component = "weka.classifiers.lazy.LWL"; break;
		case "metacost" 						: component = "weka.classifiers.meta.MetaCost"; break;
		case "mismo"							: component = "weka.classifiers.mi.MISMO"; break;
		case "miwrapper"						: component = "weka.classifiers.mi.MIWrapper"; break;
		case "multilayerperceptron" 			: component = "weka.classifiers.functions.MultilayerPerceptron"; break;
		case "nbtree"							: component = "weka.classifiers.trees.NBTree"; break;
		case "naivebayes"						: component = "weka.classifiers.bayes.NaiveBayes"; break;
		case "naivebayesmultinomial"			: component = "weka.classifiers.bayes.NaiveBayesMultinomial"; break;
		case "naivebayesmultinomialupdateable"	: component = "weka.classifiers.bayes.NaiveBayesMultinomialUpdateable"; break;
		case "naivebayessimple"					: component = "weka.classifiers.bayes.NaiveBayesSimple"; break;
		case "naivebayessimpleupdateable"		: component = "weka.classifiers.bayes.NaiveBayesSimpleUpdateable"; break;
		case "nnge"								: component = "weka.classifiers.rules.NNge"; break;
		case "oner"								: component = "weka.classifiers.rules.OneR"; break;
		case "part"								: component = "weka.classifiers.rules.PART"; break;
		case "randomforest" 					: component = "weka.classifiers.trees.RandomForest"; break;
		case "randomtree"						: component = "weka.classifiers.trees.RandomTree"; break;
		case "rbfnetwork"						: component = "weka.classifiers.functions.RBFNetwork"; break;
		case "reptree" 							: component = "weka.classifiers.trees.REPTree"; break;
		case "ridor"							: component = "weka.classifiers.rules.Ridor"; break;
		case "simplelogistic" 					: component = "weka.classifiers.functions.SimpleLogistic"; break;
		case "simplemi"							: component = "weak.classifiers.mi.SimpleMI"; break;
		case "stacking"							: component = "weka.classifiers.meta.Stacking"; break;
		case "smo"								: component = "weka.classifiers.functions.SMO"; break;
		case "zeror"							: component = "weka.classifiers.rules.ZeroR"; break;
		default: component = null;
		}
		return component;
	}
	
	public void setMetaClassifier(String classifier) {
		defaultClassifier = classifier;
	}
}