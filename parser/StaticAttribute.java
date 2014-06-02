package parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprents a static attribute, i.e. an attribute not assuming multiple values (e.G. -C 0.25)
 * @author kalmbach
 *
 */
public class StaticAttribute extends Attribute {
	String value;
	public StaticAttribute(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public List<List<String>> explode() {
		List<List<String>> ret = new ArrayList<List<String>>();
		ret.add(new ArrayList<String>());
		ret.get(0).add("-" + name);
		ret.get(0).add(value);
		return ret;
	}

	@Override
	public int getFirstDimensionSize() {
		return 1;
	}

	@Override
	public int getSecondDimensionSize() {
		return 2;
	}
}
