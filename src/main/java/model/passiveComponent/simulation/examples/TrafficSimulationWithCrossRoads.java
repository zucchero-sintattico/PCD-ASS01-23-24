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
