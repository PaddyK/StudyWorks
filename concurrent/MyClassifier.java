package concurrent;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;

/**
 * Wrapper class for weka.classifier in order to do discretization of values if needed
 * @author Patrick
 *
 */
public class MyClassifier {
	String classifierName;
	String[] options;
	Classifier classifier;
	MyWekaSet set;
	
	public MyClassifier(String classifier, String[] options, boolean discretize
			,MyWekaSet set) throws Exception {
		this.options = options;
		this.classifierName = classifier;
		this.classifier = Classifier.forName(classifier, options);
		if(options == null)
			this.options = this.classifier.getOptions();
		else
			this.options = options;
		
		if(discretize) {
			discretize(set);
		}
		else {
			this.set = set;
		}
	}
	
	public void discretize() throws Exception {
		discretize(set);
	}
	
	/**
	 * Performs discretization taking the class attribute into account
	 * @param set
	 * @throws Exception
	 */
	public void discretize(MyWekaSet set) throws Exception {

		MyWekaSet tmp = new MyWekaSet();
		Discretize filter = new Discretize();
		filter.setInputFormat(set.train());
		tmp.setTrain(Filter.useFilter(set.train(), filter));
		tmp.setTest(Filter.useFilter(set.test(), filter));
		set = tmp;
	}
	
	public void buildClassifier() throws Exception {
		classifier.buildClassifier(set.train());
	}
	
	public Evaluation evaluate() throws Exception {
		Evaluation eval = new Evaluation(set.train());		
		eval.evaluateModel(classifier, set.test());
		return eval;
	}

	public String getClassifierName() { return classifierName; }
	
	public String[] getOptions() { return options; }
}