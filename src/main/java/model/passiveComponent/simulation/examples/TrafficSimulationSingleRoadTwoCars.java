package model.passiveComponent.simulation.examples;


import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.agent.agentImpl.base.BaseCarAgent;
import model.passiveComponent.environment.Environment;
import model.passiveComponent.environment.RoadsEnvironment;
import model.passiveComponent.environment.road.Road;
import model.passiveComponent.simulation.AbstractSimulation;
import utils.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Traffic Simulation about 2 cars moving on a single road, no traffic lights
 * 
 */
public class TrafficSimulationSingleRoadTwoCars extends AbstractSimulation {

//	public TrafficSimulationSingleRoadTwoCars() {
//		super();
//	}

	@Override
	protected List<AbstractCarAgent> createAgents() {
		Road r = environment.createRoad(new Point2D(0,300), new Point2D(1500,300));
		List<AbstractCarAgent> agents = new ArrayList<>();
		AbstractCarAgent car1 = new BaseCarAgent("car-1", environment, r, 0, 0.1, 0.2, 8);
		agents.add(car1);
		AbstractCarAgent car2 = new BaseCarAgent("car-2", environment, r, 100, 0.1, 0.1, 7);
		agents.add(car2);

		/* sync with wall-time: 25 steps per sec */
		this.syncWithTime(25);
		return agents;
	}

	@Override
	protected Environment createEnvironment() {
		return new RoadsEnvironment();
	}

	@Override
	protected int setDelta() {
		return 1;
	}

	@Override
	protected int setInitialCondition() {
		return 0;
	}


	
}
