package parser;

import java.util.ArrayList;
import java.util.List;

public class ExponentialAttribute extends Attribute {
	private SequenceValue exponents;
	private double base;
	
	public ExponentialAttribute(String name, MyNumber base, SequenceValue exponents) {
		super(name);
		this.exponents = exponents;
		this.base = base.getValue();
	}

	@Override
	public List<List<String>> explode() {
		double[] expnts = exponents.getSequence();
		List<List<String>> ret = new ArrayList<List<String>>();
		for(int i = 0; i < expnts.length; i++) {
			ret.add(new ArrayList<String>());
			ret.get(i).add("-" + name);
			ret.get(i).add("" + Math.pow(base, expnts[i]));
		}			
		return ret;
	}

	@Override
	public int getFirstDimensionSize() {
		return exponents.getSequence().length;
	}

	@Override
	public int getSecondDimensionSize() {
		return 2;
	}
	
}
