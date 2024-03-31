import model.activeComponent.SimulationRunner;
import model.passiveComponent.simulation.listeners.RoadSimStatistics;
import view.RoadSimView;
import model.passiveComponent.simulation.examples.TrafficSimulationWithCrossRoads;

/**
 * 
 * Main class to create and run a simulation
 * 
 */
public class RunTrafficSimulation {

	public static void main(String[] args) throws InterruptedException {
		// var simulation = new TrafficSimulationSingleRoadTwoCars();
		// var simulation = new TrafficSimulationSingleRoadSeveralCars();
		// var simulation = new TrafficSimulationSingleRoadWithTrafficLightTwoCars();
		var simulation = new TrafficSimulationWithCrossRoads();
		simulation.setup(1000, 20);
		
		RoadSimStatistics stat = new RoadSimStatistics();
		RoadSimView view = new RoadSimView();
		view.display();
		
		simulation.addSimulationListener(stat);
		simulation.addSimulationListener(view);
		Thread t = new SimulationRunner(simulation);
		t.start();
		t.join();
	}
}
