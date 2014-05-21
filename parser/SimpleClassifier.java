package parser;

import java.util.ArrayList;
import java.util.List;

import structure.Factory;

/**
 * Name of classifier is given only. No additional attributes are specified.
 * Use this if you want to use standard weka attributes for a classifier
 * @author kalmbach
 *
 */
public class SimpleClassifier implements Classifier {
	
	/**
	 * Name of the classifier, e.g. J48
	 */
	private String name;
	
	public SimpleClassifier(String name) {
		this.name = name;
	}
	
	@Override
	public List<List<String>> getOptions() {
		List<List<String>> ret = new ArrayList<List<String>>();
		return ret;
	}

	@Override
	public String getPath() {
		return Factory.getClassifierPath(name);
	}

	@Override
	public List<String> getOptionsAsString() {
		return new ArrayList<String>();
	}

	@Override
	public String getName() {
		return name;
	}

}
