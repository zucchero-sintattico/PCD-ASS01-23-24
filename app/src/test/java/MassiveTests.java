import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pcd.ass01ridesign.activeComponent.SimulationRunner;
import pcd.ass01ridesign.passiveComponent.simulation.listeners.RoadSimStatistics;
import pcd.ass01ridesign.passiveComponent.simulation.examples.TrafficSimulationSingleRoadMassiveNumberOfCars;
//import pcd.ass01.passiveComponent.simulation.listeners.RoadSimStatistics;
//import pcd.ass01.passiveComponent.simulation.examples.TrafficSimulationSingleRoadMassiveNumberOfCars;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class MassiveTests {
    // Compare this snippet from app/src/test/java/MassiveTests.java:
    // package pcd.ass01.simtrafficexamples;
    //
    // import org.junit.jupiter.api.Test;
    //
    // public class MassiveTests {
    //
    // 	@Test
    // 	public void testMassive() {
    // 		RunTrafficSimulationMassiveTest.main(null);
    // 	}
    // }

    @BeforeEach
    public void setup() {
        File file = new File("log.txt");
        if (file.exists()) {
            file.delete();
        }
    }


    @Test
    public void testMassive() throws InterruptedException {
        int numCars =5000;
        int nSteps = 100;


        var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(numCars);
        simulation.setup(nSteps,32);
        RoadSimStatistics stat = new RoadSimStatistics();
        simulation.addSimulationListener(stat);
        Thread t = new SimulationRunner(simulation);
        t.start();
        t.join();


        // /app/log.txt must be the same of resources/log.txt
        boolean areFilesEqual = FileComparator.compareFiles("log.txt", "src/test/java/resources/log.txt");
        assertTrue(areFilesEqual, "The files /app/log.txt and /app/src/test/java/resources/log.txt are not the same");

    }

    @Test
    public void testRandMassive() throws InterruptedException {
        int numCars =5000;
        int nSteps = 100;


        var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(numCars, 1234);
        simulation.setup(nSteps,200);
        RoadSimStatistics stat = new RoadSimStatistics();
        simulation.addSimulationListener(stat);
//        simulation.run(nSteps, 200);
        Thread t = new SimulationRunner(simulation);
        t.start();
        t.join();


        // /app/log.txt must be the same of resources/log.txt
        boolean areFilesEqual = FileComparator.compareFiles("log.txt", "src/test/java/resources/rand_log.txt");
        assertTrue(areFilesEqual, "The files /app/log.txt and /app/src/test/java/resources/log.txt are not the same");

    }
}
