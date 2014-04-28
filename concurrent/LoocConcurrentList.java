package concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LoocConcurrentList extends ConcurrentLinkedQueue<Looc> {
	@Override
	public synchronized boolean add(Looc looc) {
		boolean ret = super.add(looc);
		if(size() == 1)
			notifyAll();
		return ret;
	}
	
	@Override
	public synchronized Looc poll() {
		while(isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
				return null;
			}
		}
		
		return super.poll();			
	}
}