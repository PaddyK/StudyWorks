package parser;

import java.util.List;

public interface Classifier {
	public abstract List<List<String>> getOptions();
	public abstract String getPath();
	public abstract List<String> getOptionsAsString();
	public abstract String getName();
}
