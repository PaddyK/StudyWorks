package parser;

import java.util.List;

/**
 * Represents setup for one experiment. Contains specified classifier and ressources
 * @author kalmbach
 *
 */
public class Setup {
	/**
	 * List of ressources
	 */
	private List<ResDef> ressources;
	
	/**
	 * Specified classifier being used to perform experiment
	 */
	private Classifier classifier;
	
	public Setup(List<ResDef> ressources, Classifier classifier) {
		this.ressources = ressources;
		this.classifier = classifier;
	}
	
	public List<List<String>> explode() {
		return classifier.getOptions();
	}
	
	public double getRessource(String name) {
		for(ResDef def : ressources)
			if(def.getName().equalsIgnoreCase(name))
				return def.getNum().getValue();
		return -1;
	}
	
	public Classifier getClassifier() {
		return classifier;
	}
	
	public List<ResDef> getRessources() {
		return ressources;
	}
}
