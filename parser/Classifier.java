package parser;

import java.util.List;

/**
 * Interface representing for creating classifier.
 * Makes common methods available
 * @author kalmbach
 *
 */
public interface Classifier {
	/**
	 * Return combination of attribute specified in configuration file
	 * @return
	 */
	public abstract List<List<String>> getOptions();
	
	/**
	 * Get the fully qualified name of weka classifier
	 * @return	fully qualified name, e.g. weka.classifiers.trees.J48
	 */
	public abstract String getPath();
	
	/**
	 * Returns with space concatenated attributes
	 * @return
	 */
	public abstract List<String> getOptionsAsString();
	
	/**
	 * Get name of classifier specified in configuration file
	 * @return	name of classifier, e.g. J48
	 */
	public abstract String getName();
}
