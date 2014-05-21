package parser;

import java.util.ArrayList;
import java.util.List;

public class SequenceAttribute extends Attribute {
	private SequenceValue svalue;
	
	public SequenceAttribute(String name, SequenceValue sval) {
		super(name);
		svalue = sval;
	}

	@Override
	public List<List<String>> explode() {
		double[] values = svalue.getSequence();
		List<List<String>> ret = new ArrayList<List<String>>();
		for(int i = 0; i < values.length; i++) {
			ret.add(new ArrayList<String>());
			ret.get(i).add("-" + name);
			ret.get(i).add("" + values[i]);
		}			
		return ret;
	}

	@Override
	public int getFirstDimensionSize() {
		return svalue.getSequence().length;
	}

	@Override
	public int getSecondDimensionSize() {
		return 2;
	}
}
