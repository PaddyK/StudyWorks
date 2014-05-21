package parser;

import java.util.List;

public class Setup {
	private List<ResDef> ressources;
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
