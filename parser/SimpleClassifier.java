package parser;

import java.util.ArrayList;
import java.util.List;

public class SimpleClassifier implements Classifier {
	
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
		// TODO connect with factory later
		return "weka.classifiers.trees.J48";
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
