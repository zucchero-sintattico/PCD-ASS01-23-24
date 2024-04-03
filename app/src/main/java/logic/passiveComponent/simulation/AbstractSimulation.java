package logic.passiveComponent.simulation;

import logic.passiveComponent.environment.Environment;
import logic.monitor.barrier.CyclicBarrier;
import logic.monitor.state.SimulationState;
import logic.passiveComponent.agent.AbstractCarAgent;
import logic.passiveComponent.simulation.listeners.SimulationListener;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSimulation implements Simulation {

	private final SimulationState state;
	protected List<AbstractCarAgent> agents;
	private final List<SimulationListener> listeners = new ArrayList<>();
	private boolean toBeInSyncWithWallTime;
	protected Environment environment;
	/* logical time step */
	private int dt;
	/* initial logical time */
	private int t0;
	private int t;
	private long currentWallTime;
	private long cumulativeTimePerStep;
	private int nStepsPerSec;
	private int numSteps;
	private int numStepDone;
	private CyclicBarrier barrier;
	private long startWallTime;
	private long averageTimePerStep;
	private long endWallTime;
	private double maxSpeed;
	private double minSpeed;

	public AbstractSimulation() {
		this.state = new SimulationState();
	}

	@Override
	public void setup(int numSteps, int numOfThread) {
		this.dt = this.setDelta();
		this.t = this.t0 = this.setInitialCondition();
		this.setupComponents();
		this.setupSimulation(numSteps, numOfThread);
	}

	private void setupSimulation(int numSteps, int numOfThread) {
		this.numSteps = numSteps;
		this.startWallTime = System.currentTimeMillis();
		this.environment.step();
		int effectiveNumOfThread = Math.min(numOfThread, agents.size());
		this.barrier = new CyclicBarrier(effectiveNumOfThread + 1, this::postProcessing);
		new MasterWorkerHandler(effectiveNumOfThread,
				agents, numSteps, barrier);
		this.notifyReset(this.t0, this.agents, this.environment);
	}

	private void setupComponents() {
		environment = createEnvironment();
		environment.setup(dt);
		agents = createAgents();
		for (var agent : agents) {
			agent.setup(dt);
		}
	}

	protected abstract List<AbstractCarAgent> createAgents();

	protected abstract Environment createEnvironment();

	protected abstract int setDelta();

	protected abstract int setInitialCondition();

	@Override
	public void addSimulationListener(SimulationListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void doStep() {
		if (this.numStepDone < this.numSteps) {

			try {
				this.barrier.hitAndWaitAll();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

		}
		if (this.numStepDone == this.numSteps) {
			this.endWallTime = System.currentTimeMillis();
			this.averageTimePerStep = cumulativeTimePerStep / numSteps;
			this.notifyEnd();
			this.state.stopSimulation();
		}
	}
	private void postProcessing(){
		extracted();
		if(this.numStepDone != this.numSteps){
			environment.step();
		}
	}
	private void extracted() {
		this.numStepDone++;
		this.currentWallTime = System.currentTimeMillis();
		for (AbstractCarAgent agent : agents) {
			agent.getSerialAction().run();
		}
		this.t += this.dt;
		this.notifyAvarageSpeed(this.computeAvarageSpeed());
		this.notifyNewStep(this.t, this.agents, this.environment);
		cumulativeTimePerStep += System.currentTimeMillis() - this.currentWallTime;
		if (this.toBeInSyncWithWallTime) {
			this.syncWithWallTime();
		}
	}

	@Override
	public long getSimulationDuration() {
		return (endWallTime - startWallTime);
	}

	@Override
	public long getAverageTimePerCycle() {
		return (averageTimePerStep);
	}

	@Override
	public void syncWithTime(int nCyclesPerSec) {
		this.toBeInSyncWithWallTime = true;
		this.nStepsPerSec = nCyclesPerSec;
	}

	private void syncWithWallTime() {
		try {
			long newWallTime = System.currentTimeMillis();
			long delay = 1000 / this.nStepsPerSec;
			long wallTimeDT = newWallTime - this.currentWallTime;
			if (wallTimeDT < delay) {
				Thread.sleep(delay - wallTimeDT);
			}
		} catch (Exception ignored) {
		}
	}

	// todo refactor
	private void notifyReset(int t0, List<AbstractCarAgent> agents, Environment env) {
		for (var l : listeners) {
			l.notifyInit(t0, agents, env);
		}
	}

	@Override
	public SimulationState getState() {
		return this.state;
	}

	private void notifyNewStep(int t, List<AbstractCarAgent> agents, Environment env) {
		for (var l : listeners) {
			l.notifyStepDone(t, agents, env);
		}
	}

	private double computeAvarageSpeed(){
		double avSpeed = 0;
		
		maxSpeed = -1;
		minSpeed = Double.MAX_VALUE;
		for (var agent: agents) {
			AbstractCarAgent car = (AbstractCarAgent) agent;
			double currSpeed = car.getCurrentSpeed();
			avSpeed += currSpeed;			
			if (currSpeed > maxSpeed) {
				maxSpeed = currSpeed;
			} else if (currSpeed < minSpeed) {
				minSpeed = currSpeed;
			}
		}
		
		if (agents.size() > 0) {
			avSpeed /= agents.size();
		}

		return avSpeed;
	}

	private void notifyAvarageSpeed(double averageSpeed){
		for(var l: listeners){
			l.notifyStat(averageSpeed);
		}
	}

	private void notifyEnd(){
		for(var l: listeners){
			l.notifySimulationEnded();
		}
	}

}
