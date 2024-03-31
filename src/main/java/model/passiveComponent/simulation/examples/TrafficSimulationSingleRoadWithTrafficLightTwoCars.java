package model.passiveComponent.simulation.examples;

import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.agent.agentImpl.extended.ExtendedCarAgent;
import model.passiveComponent.environment.Environment;
import model.passiveComponent.environment.RoadsEnvironment;
import model.passiveComponent.environment.road.Road;
import model.passiveComponent.environment.trafficLight.TrafficLight;
import model.passiveComponent.environment.trafficLight.TrafficLightState;
import model.passiveComponent.simulation.AbstractSimulation;
import utils.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Traffic Simulation about 2 cars moving on a single road, with one semaphore
 * 
 */
public class TrafficSimulationSingleRoadWithTrafficLightTwoCars extends AbstractSimulation {

//	public TrafficSimulationSingleRoadWithTrafficLightTwoCars() {
//		super();
//	}

	@Override
	protected List<AbstractCarAgent> createAgents() {
		Road r = environment.createRoad(new Point2D(0,300), new Point2D(1500,300));
		List<AbstractCarAgent> agents = new ArrayList<>();
		TrafficLight tl = environment.createTrafficLight(new Point2D(740,300), TrafficLightState.GREEN, 75, 25, 100);
		r.addTrafficLight(tl, 740);

		AbstractCarAgent car1 = new ExtendedCarAgent("car-1", environment, r, 0, 0.1, 0.3, 6);
		agents.add(car1);
		AbstractCarAgent car2 = new ExtendedCarAgent("car-2", environment, r, 100, 0.1, 0.3, 5);
		agents.add(car2);

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
