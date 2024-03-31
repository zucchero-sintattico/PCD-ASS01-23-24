package pcd.ass01.simtrafficbase;

import java.util.Optional;
import java.util.Random;

import pcd.ass01.passiveComponent.simulation.agent.action.Action;
import pcd.ass01.passiveComponent.simulation.agent.perception.CarPercept;
import pcd.ass01.simengineseq.*;

/**
 * 
 * Base class modeling the skeleton of an agent modeling a car in the traffic environment
 * 
 */
public abstract class CarAgent extends AbstractAgent {
	
	/* car model */
	protected double maxSpeed;		
	protected double currentSpeed;  
	protected double acceleration;
	protected double deceleration;

	/* percept and action retrieved and submitted at each step */
	protected CarPercept currentPercept;
	protected Optional<Action> selectedAction;



	int dt = 1;
	
	
	public CarAgent(String id, RoadsEnv env, Road road, 
			double initialPos, 
			double acc, 
			double dec,
			double vmax) {
		super(id);
		this.acceleration = acc;
		this.deceleration = dec;
		this.maxSpeed = vmax;
		env.registerNewCar(this, road, initialPos);
	}

	public  CarAgent(String id, RoadsEnv env, Road road, int seed){
		super(id);
		Random gen = new Random(seed);
		this.acceleration = 1 + gen.nextDouble(0,1);
		this.deceleration = 0.3 + gen.nextDouble(0,1);
		this.maxSpeed = 4 + gen.nextDouble(1,10);
		env.registerNewCar(this, road, gen.nextInt(0, 1000));

	}

	/**
	 * 
	 * Basic behaviour of a car agent structured into a sense/decide/act structure 
	 * 
	 */
	public void sensAndDecide() {

		/* sense */

		AbstractEnvironment env = this.getEnv();		
		currentPercept = (CarPercept) env.getCurrentPercepts(getAgentId());			

		/* decide */
		
		selectedAction = Optional.empty();

		decide(dt);



	}

	public void doAction() {
		AbstractEnvironment env = this.getEnv();
		env.doAction(getAgentId(), selectedAction);
	}
	
	/**
	 * 
	 * Base method to define the behaviour strategy of the car
	 * 
	 * @param dt
	 */
	protected abstract void decide(int dt);
	
	public double getCurrentSpeed() {
		return currentSpeed;
	}
	
	protected void log(String msg) {
		System.out.println("[CAR " + this.getAgentId() + "] " + msg);
	}

	
}
