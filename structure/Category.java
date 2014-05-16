package structure;

import java.util.Collection;
import java.util.LinkedList;

import concurrent.Looc;

public class Category implements Component {
	
	private LinkedList<Component> elements;
	private Factory factory;
	
	public Category(Collection<String> classifierNames) {
		elements = new LinkedList<Component>();
		factory = new Factory();
		for(String s : classifierNames) {
			elements.add(factory.produceConfiguration(s));
		}
	}
	
	public Category(String classifierName) {
		elements = new LinkedList<Component>();
		elements.add(new Factory().produceConfiguration(classifierName));
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
	
	public Factory getFactory() {
		return factory;
	}
	
	public LinkedList<Looc> roughSearch() {
		LinkedList<Looc> ret = new LinkedList<Looc>();
		for(Component component : elements)
			ret.addAll(component.roughSearch());
		return ret;
		
	}
}