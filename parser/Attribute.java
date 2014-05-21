package parser;

import java.util.List;

/** 
 * Representation of an attribute of an classifier in weka
 * @author kalmbach
 *
 */
public abstract class Attribute {
	protected String name;
	
	public Attribute(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * The user may give possible attributes as a sequence, for example 1,2..10
	 * This means execution of this classifier ten times each with another value for
	 * specified attributes.
	 * 
	 * This method expands the given attributes and creates combinations of them
	 * @return	List of List of Strings, one List of Strings contain attributes for one
	 * 			run, e.g. {-C, 1, -P, 10}
	 */
	public abstract List<List<String>> explode();
	/**
	 * Represents the number of different values this attribute may assume, e.g. 1 to 10
	 * @return	Number of different values
	 */
	public abstract int getFirstDimensionSize();
	
	/**
	 * Returns of how many elements this attribute is made up, e.g. -C 5 would be two.
	 * Usually this value is to, switch + value
	 * @return	Number of elements attribute declaration consists of
	 */
	public abstract int getSecondDimensionSize();
}
