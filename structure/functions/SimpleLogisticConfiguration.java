package structure.functions;

import java.util.Date;
import java.util.LinkedList;

import structure.Component;
import concurrent.Looc;

public class SimpleLogisticConfiguration implements Component {

	@Override
	public LinkedList<Looc> baseline() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		queue.add(new Looc("looc-" + new Date().getTime()
				,"weka.classifiers.functions.SimpleLogistic"
				,null));
		return queue;
	}

	@Override
	public LinkedList<Looc> tune() {
		LinkedList<Looc> queue = new LinkedList<Looc>();
		long milisec = new Date().getTime();
		for(int hstop = 20; hstop <= 100; hstop+=20)
			for(int iter = 200; iter <=1000; iter+=200)
				for(double d = 0.1; d < 1; d+=0.1) {
					queue.add(new Looc("looc-" + milisec++
							,"weka.classifiers.functions.SimpleLogistic"
							,new String[]{"-I","0","-M","" + iter,"-H", "" + hstop,"-W","" + d}));
					try{Thread.sleep(2);}
					catch(Exception e){e.printStackTrace();}
				}
		return queue;
	}

	@Override
	public LinkedList<Looc> roughSearch() {
		// TODO Auto-generated method stub
		return null;
	}
	
}