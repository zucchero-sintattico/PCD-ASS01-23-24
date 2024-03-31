import model.activeComponent.SimulationRunner;
import model.monitor.barrier.CyclicBarrier;
import model.passiveComponent.simulation.examples.TrafficSimulationSingleRoadTwoCars;
import model.passiveComponent.simulation.examples.TrafficSimulationSingleRoadWithTrafficLightTwoCars;
import model.passiveComponent.simulation.listeners.RoadSimStatistics;
import model.passiveComponent.simulation.examples.TrafficSimulationWithCrossRoads;



public class RunTrafficSimulation {

	private static class SimulationClass extends Thread{

		int a;
		CyclicBarrier barrier;

		private SimulationClass(int a, CyclicBarrier barrier) {
			super();
			this.a = a;
			this.barrier = barrier;
		}

		@Override
		public void run() {
			for (int i = 0; i < this.a; i++) {
                try {
                    barrier.hitAndWaitAll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
		}
	}


	public static void main(String[] args) throws InterruptedException {
		CyclicBarrier barrier = new CyclicBarrier(2);
		int a = 2;

		Thread t1 = new SimulationClass(a, barrier);
		t1.start();

		for (int i = 0; i < a; i++) {
			try {
				barrier.hitAndWaitAll();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}


	}
}
