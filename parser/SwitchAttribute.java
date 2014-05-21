package parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprents a swicht, i.e. an attribute not assuming any values (e.G. -U for unpruned)
 * @author kalmbach
 *
 */
public class SwitchAttribute extends Attribute {
	public SwitchAttribute(String name) {
		super(name);
	}

	@Override
	public List<List<String>> explode() {
		List<List<String>> ret = new ArrayList<List<String>>();
		ret.add(new ArrayList<String>());
		ret.get(0).add(name);
		return null;
	}

	@Override
	public int getFirstDimensionSize() {
		return 1;
	}

	@Override
	public int getSecondDimensionSize() {
		return 1;
	}
}
