package pcd.ass01.simengineseq;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Base class for defining concrete simulations
 *  
 */
public abstract class AbstractSimulation {

	/* environment of the simulation */
	private AbstractEnvironment env;
	
	/* list of the agents */
	private List<AbstractAgent> agents;
	
	/* simulation listeners */
	private List<SimulationListener> listeners;

	/* logical time step */
	private int dt;
	
	/* initial logical time */
	private int t0;

	/* in the case of sync with wall-time */
	private boolean toBeInSyncWithWallTime;
	private int nStepsPerSec;
	
	/* for time statistics*/
	private long currentWallTime;
	private long startWallTime;
	private long endWallTime;
	private long averageTimePerStep;
	private Random r = new Random();

	private Barrier barrier1, barrier2;
	private int nSteps;
	private int numSteps;
	private int t;
	private long timePerStep;

	private SimulationState state;

	protected AbstractSimulation() {
		agents = new ArrayList<AbstractAgent>();
		listeners = new ArrayList<SimulationListener>();
		toBeInSyncWithWallTime = false;
		this.state = new SimulationState();
	}
	
	/**
	 * 
	 * Method used to configure the simulation, specifying env and agents
	 * 
	 */
	
	protected abstract void setupComponent();
	public void setup(int numSteps, int numOfThread){
		setupComponent();
		beforeRun(numSteps, numOfThread);
	}

	private void beforeRun(int numSteps, int numOfThread) {
		barrier1 = new CyclicBarrier(numOfThread+1);
		barrier2 = new CyclicBarrier(numOfThread+1);
		startWallTime = System.currentTimeMillis();

		List<Thread> carsList = new ArrayList<Thread>();


		/* initialize the env and the agents inside */
		t = t0;

		env.init();
		for (var a: agents) {
			a.init(env);
		}


		new MasterWorkerHandler(numOfThread, agents, numSteps, barrier1, barrier2);

		this.notifyReset(t, agents, env);
		this.numSteps = numSteps;
		timePerStep = 0;
		nSteps = 0;
	}

	/**
	 * Method running the simulation for a number of steps,
	 * using a sequential approach
	 * 
	 * @param
	 */
	public void run() {

		
		if (nSteps < numSteps) {

			currentWallTime = System.currentTimeMillis();
		
			/* make a step */
			
			env.step(dt);

			System.out.println("Step: " + nSteps);

			try {
				barrier1.hitAndWaitAll();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
//			while(!barrier.isResettable()){}
			for (var a: agents) {
				a.doAction();
			}

			
			t += dt;
			
			notifyNewStep(t, agents, env);

			nSteps++;			
			timePerStep += System.currentTimeMillis() - currentWallTime;
			
			if (toBeInSyncWithWallTime) {
				syncWithWallTime();
			}
			try {
				barrier2.hitAndWaitAll();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
//			barrier.reset();

		}	
		if(nSteps == numSteps){
			endWallTime = System.currentTimeMillis();
			this.averageTimePerStep = timePerStep / numSteps;
			this.state.stopSimulation();
		}

		
	}
	
	public long getSimulationDuration() {
		return (endWallTime - startWallTime);
	}
	
	public long getAverageTimePerCycle() {
		return (averageTimePerStep);
	}
	
	/* methods for configuring the simulation */
	
	protected void setupTimings(int t0, int dt) {
		this.dt = dt;
		this.t0 = t0;
	}
	
	protected void syncWithTime(int nCyclesPerSec) {
		this.toBeInSyncWithWallTime = true;
		this.nStepsPerSec = nCyclesPerSec;
	}
		
	protected void setupEnvironment(AbstractEnvironment env) {
		this.env = env;
	}

	protected void addAgent(AbstractAgent agent) {
		agents.add(agent);
	}
	
	/* methods for listeners */
	
	public void addSimulationListener(SimulationListener l) {
		this.listeners.add(l);
	}
	
	private void notifyReset(int t0, List<AbstractAgent> agents, AbstractEnvironment env) {
		for (var l: listeners) {
			l.notifyInit(t0, agents, env);
		}
	}

	private void notifyNewStep(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
		for (var l: listeners) {
			l.notifyStepDone(t, agents, env);
		}
	}

	/* method to sync with wall time at a specified step rate */
	
	private void syncWithWallTime() {
		try {
			long newWallTime = System.currentTimeMillis();
			long delay = 1000 / this.nStepsPerSec;
			long wallTimeDT = newWallTime - currentWallTime;
			if (wallTimeDT < delay) {
				Thread.sleep(delay - wallTimeDT);
			}
		} catch (Exception ex) {}		
	}

	public SimulationState getState() {
		return this.state;
	}
}
