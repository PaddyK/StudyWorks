package structure.lazy;

import java.util.Date;
import java.util.LinkedList;

import structure.Component;
import concurrent.Looc;

public class KStarConfiguration implements Component {

	@Override
	public LinkedList<Looc> baseline() {
		LinkedList<Looc> ret = new LinkedList<Looc>();
		ret.add(new Looc("looc-" + new Date().getTime()
				,"weka.classifiers.lazy.KStar"
				, new String[]{"-B", "20", "-M", "a"}));
		return ret;
	}
	
	@Override
	/**
	 * tuning of parameter -B (globalBlend) from 0,5,..,100 and switching on -E (entropy based) with
	 * same global blend settings
	 */
	public LinkedList<Looc> tune() {
		LinkedList<Looc> ret = new LinkedList<Looc>();
		long milisec = new Date().getTime();
		for(int b = 0; b <= 100; b+=5) {
			ret.add(new Looc("" + milisec++
					,"weka.classifiers.lazy.KStar"
					,new String[]{"-B", "" + b,"-M","a"}));

			ret.add(new Looc("" + milisec++
					,"weka.classifiers.lazy.KStar"
					,new String[]{"-B", "" + b, "-E", "-M","a"}));
		}
		return null;
	}

	@Override
	public LinkedList<Looc> roughSearch() {
		// TODO Auto-generated method stub
		return null;
	}
	
}