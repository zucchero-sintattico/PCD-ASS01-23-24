package mvc;

import mvc.controller.Controller;
import mvc.view.StatisticalView;

public class TestGUI {
    public static void main(String[] args) {
        //var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(500);
        //simulation.setup();
        StatisticalView view = new StatisticalView();
        Controller controller = new Controller(view);
        controller.displayView();
    }
}
