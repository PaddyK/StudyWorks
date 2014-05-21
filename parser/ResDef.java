package parser;

import java.util.HashMap;

public class ResDef {
	private MyNumber num;
	private String name;
	
	public ResDef(String name, MyNumber num) {
		this.num = num;
		this.name = name;
	}

	public MyNumber getNum() {
		return num;
	}

	public String getName() {
		return name;
	}
}
