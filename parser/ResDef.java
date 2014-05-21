package parser;

import java.util.HashMap;

/**
 * Represents definition of a ressource consisting of a name and a single number.
 * 
 * Remark:
 * --------
 * Currently ResDef is viewed in context of definition of ressources like reader or writer
 * and nothing else. This scope may be expanded
 * @author kalmbach
 *
 */
public class ResDef {
	/**
	 * Value for ressource
	 */
	private MyNumber num;
	/**
	 * Name of ressource
	 */
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
