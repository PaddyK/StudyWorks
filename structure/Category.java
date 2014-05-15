package structure;

import java.util.Collection;
import java.util.LinkedList;

import concurrent.Looc;

public class Category implements Component {
	
	private LinkedList<Component> elements;
	private Factory factory;
	
	public Category(Collection<String> classifierNames) {
		elements = new LinkedList<>();
		for(String s : classifierNames) {
			elements.add(factory.produceConfiguration(s));
		}
	}
	
	@Override
	public LinkedList<Looc> baseline() {
		LinkedList<Looc> ret = new LinkedList<Looc>();
		for(Component component : elements)
			ret.addAll(component.baseline());
		return ret;
	}

	@Override
	public LinkedList<Looc> tune() {
		LinkedList<Looc> ret = new LinkedList<Looc>();
		for(Component component : elements)
			ret.addAll(component.baseline());
		return ret;
	}
}