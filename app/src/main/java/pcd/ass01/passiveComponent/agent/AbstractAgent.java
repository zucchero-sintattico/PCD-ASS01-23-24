package pcd.ass01.passiveComponent.agent;

import pcd.ass01.passiveComponent.agent.action.Action;
import pcd.ass01.passiveComponent.agent.percept.CarPercept;
import pcd.ass01.passiveComponent.agent.task.ParallelTask;
import pcd.ass01.passiveComponent.agent.task.SerialTask;
import pcd.ass01.passiveComponent.simulation.SimulationComponent;
import pcd.ass01.simtrafficbase.AbstractEnvironment;
import pcd.ass01.simtrafficbase.Road;
import pcd.ass01.simtrafficbase.RoadsEnv;

import java.util.Optional;
import java.util.Random;

/**
 * 
 * Base  class for defining types of agents taking part to the simulation
 * 
 */
public  abstract class  AbstractAgent implements SimulationComponent, Agent {
	
	private final String myId;
	private AbstractEnvironment env;
	/* car model */
	protected double maxSpeed;
	protected double currentSpeed;
	protected double acceleration;
	protected double deceleration;

	/* percept and action retrieved and submitted at each step */
	protected CarPercept currentPercept;
	protected Optional<Action> selectedAction;



	protected int dt;


	public AbstractAgent(String id, RoadsEnv env, Road road,
					double initialPos,
					double acc,
					double dec,
					double vmax) {
		this.myId = id;
		this.acceleration = acc;
		this.deceleration = dec;
		this.maxSpeed = vmax;
		env.registerNewCar(this, road, initialPos);
	}



	/**
	 *
	 * Basic behaviour of a car agent structured into a sense/decide/act structure
	 *
	 */
	private void sensAndDecide() {

		/* sense */

		AbstractEnvironment env = this.getEnv();
		currentPercept = (CarPercept) env.getCurrentPercepts(getAgentId());

		/* decide */

		selectedAction = Optional.empty();

		decide();



	}

	private void doAction() {
		AbstractEnvironment env = this.getEnv();
		env.doAction(getAgentId(), selectedAction);
	}

	/**
	 *
	 * Base method to define the behaviour strategy of the car
	 *
	 * @param
	 */
	protected  abstract void decide();

	public double getCurrentSpeed() {
		return currentSpeed;
	}

	protected void log(String msg) {
		System.out.println("[CAR " + this.getAgentId() + "] " + msg);
	}


	/**
	 * Each agent has an identifier
	 * 
	 * @param id
	 */
	protected  AbstractAgent(String id) {
		this.myId = id;
	}
	
	/**
	 * This method is called at the beginning of the simulation
	 * 
	 * @param env
	 */
	public void init(AbstractEnvironment env) {
		this.env = env;
	}
	
	/**
	 * This method is called at each step of the simulation
	 * 
	 */


	/**
	 *
	 * @return the identifier of the agent
	 */


	
	
	public  String getAgentId() {
		return myId;
	}
	
	protected  AbstractEnvironment getEnv() {
		return this.env;
	}

	


	@Override
	public void setup(int dt) {
		this.dt = dt;
	}

	@Override
	public SerialTask getSerialTask() {
		return this::doAction;
	}

	@Override
	public ParallelTask getParallelTask() {
		return this::sensAndDecide;
	}
}
