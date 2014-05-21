package parser;

public class MyNumber {
	private double value;
	
	public MyNumber(String token) {
		value = Double.parseDouble(token);
	}
	
	public double getValue() {
		return value;
	}
}
