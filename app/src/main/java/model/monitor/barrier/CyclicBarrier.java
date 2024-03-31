package model.monitor.barrier;

public class CyclicBarrier implements Barrier {

	private final int numberOfParticipants;
	private int participants = 0;
	private boolean broken;
	private Runnable preprocessing = () -> {
	};
	private Runnable postprocessing = () -> {
	};

	public CyclicBarrier(int numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}

	public CyclicBarrier(int numberOfParticipants, Runnable preprocessing, Runnable postprocessing) {
		this.numberOfParticipants = numberOfParticipants;
		this.preprocessing = preprocessing;
		this.postprocessing = postprocessing;
		this.preprocessing.run();
	}

	@Override
	public synchronized void hitAndWaitAll() throws InterruptedException {
		this.enterBarrier();
//		System.out.println("enter");
		while (!broken) {
			wait();
		}
		this.passBarrier();
//		System.out.println("exited");
	}

	private void enterBarrier() throws InterruptedException {
		while (broken) {
			wait();
		}
		evaluateParticipants(this::breakBarrier);
	}

	private void passBarrier() {
		evaluateParticipants(this::reset);
	}

	private void breakBarrier() {
		broken = true;
		postprocessing.run();
		preprocessing.run();
//		System.out.println("runPostProcessing----------------------");
		this.setupAndNotify();
	}

	private void reset() {
		broken = false;
//		System.out.println("runPrePrePreProcessing----------------------");

//		System.out.println("runPreProcessing----------------------");
		this.setupAndNotify();
	}

	private void setupAndNotify() {
		participants = 0;
		notifyAll();
	}

	private void evaluateParticipants(Runnable action) {
		participants++;
		if (participants == numberOfParticipants) {
			action.run();
		}
	}

}
