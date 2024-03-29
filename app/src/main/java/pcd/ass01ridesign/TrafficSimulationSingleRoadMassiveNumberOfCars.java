package pcd.ass01ridesign;


import pcd.ass01ridesign.activeComponent.AbstractSimulation;
import pcd.ass01ridesign.monitor.agent.AbstractCarAgent;
import pcd.ass01ridesign.monitor.agent.BaseCarAgent;
import pcd.ass01ridesign.monitor.environment.Environment;
import pcd.ass01ridesign.monitor.environment.Road;
import pcd.ass01ridesign.monitor.environment.RoadsEnvironment;
import pcd.ass01ridesign.passiveComponent.utils.Point2D;

import java.util.Optional;
import java.util.Random;

public class TrafficSimulationSingleRoadMassiveNumberOfCars extends AbstractSimulation {

	private int numCars;
	private Optional<Integer> seed = Optional.empty();
	
	public TrafficSimulationSingleRoadMassiveNumberOfCars(int numCars) {
		super();
		this.numCars = numCars;
	}

	public TrafficSimulationSingleRoadMassiveNumberOfCars(int numCars, int seed) {
		super();
		this.numCars = numCars;
		this.seed = Optional.of(seed);
	}
	
	public void setup() {
		int dt= 1;
		this.setupTimings(0, dt);
		//todo dove si passava il dt???
		Environment env = new RoadsEnvironment(dt);
		this.setupEnvironment(env);
		
		Road road = env.createRoad(new Point2D(0,300), new Point2D(15000,300));

		for (int i = 0; i < numCars; i++) {
			
			String carId = "car-" + i;
			double initialPos = i*10;			
			double carAcceleration = 1;
			double carDeceleration = 0.3;
			double carMaxSpeed = 7;
						
			AbstractCarAgent car = new BaseCarAgent(carId, env,
									road,
									initialPos, 
									carAcceleration, 
									carDeceleration,
									carMaxSpeed,1);

			if (seed.isPresent()) {
				car = new BaseCarAgent(carId, env, road, initialPos, seed.get());
			}

			this.addAgent(car);
			
			
			/* no sync with wall-time */
		}
		
	}	
}
	