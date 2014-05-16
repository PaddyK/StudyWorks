package structure;

import java.util.LinkedList;

import concurrent.Looc;

public interface Component {
	public abstract LinkedList<Looc> baseline();
	public abstract LinkedList<Looc> tune();
	public abstract LinkedList<Looc> roughSearch();
}