package parser;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class ComplexClassifier implements Classifier {
	private List<Attribute> attributes;
	private String name;
	
	public ComplexClassifier() {
		attributes = new ArrayList<Attribute>();
		name = "";
	}
	
	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public List<List<String>> getOptions() {
		LinkedList<Attribute> attrs = new LinkedList<Attribute>();
		attrs.addAll(attributes);		
		return recursive(attrs);
	}
	
	private List<List<String>> recursive(LinkedList<Attribute> attrs) {	
		List<List<String>> ret;
		if(attrs.isEmpty()) {
			ret = new ArrayList<List<String>>();
			ret.add(new ArrayList<String>());
		}
		else {
			ret = new ArrayList<List<String>>();
			Attribute attr = attrs.poll();
			List<List<String>> tmp = recursive(attrs);
			List<List<String>> newVals = attr.explode();
			
			for(List<String> l1 : tmp) {
				for(List<String> l2 : newVals) {
					ret.add(new ArrayList<String>());
					ret.get(ret.size() - 1).addAll(l1);
					ret.get(ret.size() - 1).addAll(l2);
				}
			}							
		}
		return ret;
	}

	@Override
	public String getPath() {
		// TODO connect with factory later
		return "weka.classifiers.trees.J48";
	}

	@Override
	public List<String> getOptionsAsString() {
		List<String> ret = new ArrayList<String>();
		List<List<String>> options = getOptions();
		for(List<String> list : options)
			ret.add(implode(list));
		return ret;
	}
	
	private String implode(List<String> values) {
		String ret = "";
		for(String s : values)
			ret += s + " ";
		return ret.trim();
	}

	@Override
	public String getName() {
		return name;
	}

}
