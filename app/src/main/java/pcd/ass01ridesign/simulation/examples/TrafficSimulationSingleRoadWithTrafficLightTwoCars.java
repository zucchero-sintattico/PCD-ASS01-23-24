package pcd.ass01ridesign.simulation.examples;

import pcd.ass01ridesign.passiveComponent.agent.AbstractCarAgent;
import pcd.ass01ridesign.passiveComponent.agent.agentImpl.extended.ExtendedCarAgent;
import pcd.ass01ridesign.passiveComponent.environment.Environment;
import pcd.ass01ridesign.passiveComponent.environment.RoadsEnvironment;
import pcd.ass01ridesign.passiveComponent.environment.road.Road;
import pcd.ass01ridesign.passiveComponent.environment.trafficLight.TrafficLight;
import pcd.ass01ridesign.passiveComponent.environment.trafficLight.TrafficLightState;
import pcd.ass01ridesign.passiveComponent.utils.Point2D;
import pcd.ass01ridesign.simulation.AbstractSimulation;

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
