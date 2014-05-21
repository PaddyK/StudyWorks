package parser;

import java.util.List;

public abstract class Attribute {
	protected String name;
	
	public Attribute(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract List<List<String>> explode();
	
	public abstract int getFirstDimensionSize();
	public abstract int getSecondDimensionSize();
}
