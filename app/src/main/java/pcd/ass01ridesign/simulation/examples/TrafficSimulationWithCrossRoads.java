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
import java.util.Random;

public class TrafficSimulationWithCrossRoads extends AbstractSimulation {

//	public TrafficSimulationWithCrossRoads() {
//		super();
//	}

	@Override
	protected List<AbstractCarAgent> createAgents() {
		TrafficLight tl1 = environment.createTrafficLight(new Point2D(740,300), TrafficLightState.GREEN, 75, 25, 100);
		List<AbstractCarAgent> agents = new ArrayList<>();
		Road r1 = environment.createRoad(new Point2D(0,300), new Point2D(1500,300));
		r1.addTrafficLight(tl1, 740);

		AbstractCarAgent car1 = new ExtendedCarAgent("car-1", environment, r1, 0, 0.1, 0.3, 6);
		agents.add(car1);
		AbstractCarAgent car2 = new ExtendedCarAgent("car-2", environment, r1, 100, 0.1, 0.3, 5);
		agents.add(car2);

		TrafficLight tl2 = environment.createTrafficLight(new Point2D(750,290),  TrafficLightState.RED, 75, 25, 100);

		Road r2 = environment.createRoad(new Point2D(750,0), new Point2D(750,600));
		r2.addTrafficLight(tl2, 290);

		AbstractCarAgent car3 = new ExtendedCarAgent("car-3", environment, r2, 0, 0.1, 0.2, 5);
		agents.add(car3);
		AbstractCarAgent car4 = new ExtendedCarAgent("car-4", environment, r2, 100, 0.1, 0.1, 4);
		agents.add(car4);


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
