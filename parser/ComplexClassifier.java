package parser;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import structure.Factory;

/**
 * Implements interface Classifier
 * Represents a classifier for which attributes are specified. These attributes can
 * represent different values or other classifier
 * @author kalmbach
 *
 */
public class ComplexClassifier implements Classifier {
	/**
	 * List of attributes denoted in configuration file
	 */
	private List<Attribute> attributes;
	
	/**
	 * Name of the classifier denoted in configuration file, e.g. J48
	 */
	private String name;
	
	public ComplexClassifier() {
		attributes = new ArrayList<Attribute>();
		name = "";
	}
	
	/**
	 * Adds an attribute to list
	 * @param attribute
	 */
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
	
	/**
	 * Recursive function exploding attribute combinations.
	 * @param attrs
	 * @return	Returns combination of all specified attribute values for all attributes
	 */
	private List<List<String>> recursive(LinkedList<Attribute> attrs) {	
		List<List<String>> ret;
		
		// If attributes are empty create empty array list
		// One element should be contained so loops later are executed
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
		return Factory.getClassifierPath(name);
	}

	@Override
	public List<String> getOptionsAsString() {
		List<String> ret = new ArrayList<String>();
		List<List<String>> options = getOptions();
		for(List<String> list : options)
			ret.add(implode(list));
		return ret;
	}
	
	/**
	 * Concatenates Strings in given List with spaces
	 * @param values
	 * @return	with space concatenated elements of list
	 */
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
