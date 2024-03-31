import model.activeComponent.SimulationRunner;
import model.passiveComponent.simulation.listeners.RoadSimStatistics;
import model.passiveComponent.simulation.examples.TrafficSimulationSingleRoadMassiveNumberOfCars;

import java.io.FileWriter;
import java.util.List;

public class RunTrafficSimulationMassiveTest {

	public static void main(String[] args) {

		int numCars = 5000;
		int nSteps = 100;

		List<Integer> numOfThreads = List.of(1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024);
		//List<Integer> numOfThreads = List.of(64);

		numOfThreads.forEach(numOfThread -> {
			System.out.println("Thread: " + numOfThread);
			var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(numCars);
			simulation.setup(nSteps, numOfThread);

			log("Running the simulation: " + numCars + " cars, for " + nSteps + " steps ...");

			RoadSimStatistics stat = new RoadSimStatistics();

			simulation.addSimulationListener(stat);

			Thread t = new SimulationRunner(simulation);
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			long d = simulation.getSimulationDuration();
			log("Completed in " + d + " ms - average time per step: " + simulation.getAverageTimePerCycle() + " ms");

			//ad d in a file
			try {
				FileWriter myWriter = new FileWriter("log_time_thread.txt", true);
				myWriter.write(numOfThread + "\t" + d + "\n");
				myWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}
	
	private static void log(String msg) {
		System.out.println("[ SIMULATION ] " + msg);

	}
}
