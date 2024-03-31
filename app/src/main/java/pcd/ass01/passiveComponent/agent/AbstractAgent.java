package pcd.ass01.passiveComponent.agent;

import pcd.ass01.simtrafficbase.AbstractEnvironment;

/**
 * 
 * Base  class for defining types of agents taking part to the simulation
 * 
 */
public abstract  class  AbstractAgent implements Runnable {
	
	private final String myId;
	private AbstractEnvironment env;



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

	public abstract void sensAndDecide();

	/**
	 *
	 * @return the identifier of the agent
	 */

	public abstract void doAction();

	
	
	public  String getAgentId() {
		return myId;
	}
	
	protected  AbstractEnvironment getEnv() {
		return this.env;
	}

	@Override
	public void run() {
		sensAndDecide();
	}



}
