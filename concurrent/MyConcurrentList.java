package concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Wrapper class for ConcurrentLinkedQueue to store MyWekaSet objects. Indicates whether any more
 * sets are coming via done attribute
 * @author Patrick
 *
 */
public class MyConcurrentList extends ConcurrentLinkedQueue<MyWekaSet> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Indicates whether sets are still pending. If no sets for folds are still to be read this
	 * attribute is set to true
	 */
	private boolean done;
	public MyConcurrentList() {
		super();
		done = false;
	}
	
	/**
	 * true indicates that all test and training sets have been read from file. false indicates
	 * that sets still have to be read
	 * @return
	 */
	public synchronized boolean isDone() { return done; }
	/**
	 * true indicates that all test and training sets have been read from file. false indicates
	 * that sets still have to be read
	 */
	public synchronized void setDone() { done = true; notifyAll(); }
	public synchronized void unsetDone() { done = false; }
	
	@Override
	public synchronized boolean add(MyWekaSet set) {
		boolean ret = super.add(set);
		if(this.size() == 1)
			notifyAll();
		return ret;
	}
	
	@Override
	public synchronized MyWekaSet poll(){
		while(this.isEmpty()) {
			if(done)
				return null;
			try {
				wait();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			};
		}
		return super.poll();
	}
}