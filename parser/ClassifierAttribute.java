package parser;

import java.util.List;
import java.util.ArrayList;

public class ClassifierAttribute extends Attribute {
	
	private Classifier classifier;
	
	public ClassifierAttribute(String name, Classifier classifier) {
		super(name);
		this.classifier = classifier;
	}

	@Override
	public List<List<String>> explode() {
		switch(name.toUpperCase()) {
		case "W": return explode1();
		case "M": return explode2();
		case "B": return explode2();
		default: return null;
		}
	}
	
	private List<List<String>> explode1() {
		List<List<String>> given = classifier.getOptions();
		List<List<String>> ret = new ArrayList<List<String>>();
		int index = 0;
		
		do {
			ret.add(new ArrayList<String>());
			ret.get(index).add("-" + name);
			ret.get(index).add(classifier.getPath());
			
			if(!given.isEmpty()){
				ret.get(index).add("--");
				for(int j = 0; j < given.get(index).size(); j++)
					ret.get(index).add(given.get(index).get(j));
			}
		} while(++index < given.size());
		
		return ret;
	}
	
	private List<List<String>> explode2() {
		List<String> options = classifier.getOptionsAsString();
		List<List<String>> ret = new ArrayList<List<String>>();
		int index = 0;
		String tmp;
		
		do {
			ret.add(new ArrayList<String>());
			ret.get(index).add("-" + name);
			tmp = "\"" + classifier.getPath();
			
			if(!options.isEmpty())
				tmp +=  " " + options.get(index);
			ret.get(index).add(tmp + "\"");
		}while(++index < options.size());
		
		return ret;
	}

	@Override
	public int getFirstDimensionSize() {
		return classifier.getOptions().size();
	}

	@Override
	public int getSecondDimensionSize() {
		switch(name.toUpperCase()) {
		case "W": return classifier.getOptions().get(0).size();
		case "M": return 2;
		case "B": return 2;
		default: return 0;
		}
	}
}
