package concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

import weka.classifiers.Evaluation;

/**
 * Worker thraet performing classification for one fold with all classifiers specified
 * @author Patrick
 *
 */
public class ClassifyWorker extends Thread {
	/**
	 * Holds MyWekaSets containing Instances for test and training set.
	 */
	private MyConcurrentList buffer;
	/**
	 * When modelling this structure it was assumed, different classifiers's performance would be
	 * evaluated. For each classifier a own loocv is necessary. This object represents this set of
	 * loocv and has to be created extern of this class								
	 */
	private ConcurrentLinkedQueue<Looc> loocs;

	public ClassifyWorker(ConcurrentLinkedQueue<Looc> looc, MyConcurrentList buffer) {
		this.loocs = looc;
		this.buffer = buffer;
	}

	@Override
	public void run() {
		MyWekaSet set;
		MyClassifier classifier;
		Evaluation eval;
		ConcurrentFold fold;

		// Should run as long as there are elements in the buffer. However it could happen, that the
		// thread responsible for filling buffer is too slow. Therefore a attribute checked by isDone
		// indicates whether there are more sets still coming
		while((set = buffer.poll()) != null && !isInterrupted()) {
			try {
//				set = buffer.poll();
				if(set != null) {
					for(Looc looc : loocs) {
						classifier = new MyClassifier(looc.getClassifier()
								,looc.getOptions()
								,looc.isDiscretize()
								,set);
						classifier.buildClassifier();

						eval = classifier.evaluate();
						String loocId = looc.getId();
						fold = new ConcurrentFold(loocId +"_fold_" + (looc.getFoldNumber() + 1), loocId
								,set.getPatientId(), eval);
						if(looc.isAntibodiesSet())
							fold.setAntibodies(set.test());
						looc.addFold(fold);
					}
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

}