package parser;

public class SequenceValue {
	double[] sequence;
	
	public SequenceValue(MyNumber start, MyNumber next, MyNumber end) {
		// Add one since start and end are inclusive, e.g. 1,2..9
		// --> (9-1)/(2-1) = 8 --> nine would not be in set of values
		sequence = new double[(int)((end.getValue() - start.getValue())
				/(next.getValue() - start.getValue()) + 1)];
		sequence[0] = start.getValue();
		for(int i = 1; i < sequence.length; i++)
			sequence[i] = sequence[i-1] + next.getValue() - start.getValue();
	}
	
	public double[] getSequence() {
		return sequence;
	}
}
