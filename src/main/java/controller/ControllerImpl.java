package controller;

import model.activeComponent.SimulationRunner;
import model.monitor.state.SimulationState;
import model.passiveComponent.simulation.Simulation;
import model.passiveComponent.simulation.SimulationType;
import view.RoadSimView;
import model.passiveComponent.simulation.listeners.SimulationListener;

public class ControllerImpl implements Controller {

    private Simulation simulation;
    private SimulationState simulationState;
    private RoadSimView simulationView;

    public ControllerImpl() {
    }

    @Override
    public void stopSimulation() {
        this.simulationState.stopSimulation();
    }

    @Override
    public void startSimulation() {
        new SimulationRunner(this.simulation).start();
    }

    @Override
    public void setupSimulation(SimulationType type, int numberOfSteps, int numberOfThread) {
        if(this.simulationView != null){
            this.simulationView.dispose();
        }
        this.simulation = type.getSimulation(type);
        this.simulationState = this.simulation.getState();
        this.simulation.setup(numberOfSteps, numberOfThread);
    }

    @Override
    public int getAvaiableProcessor() {
        return Runtime.getRuntime().availableProcessors();
    }

    @Override
    public void attachListener(SimulationListener listener) {
        this.simulation.addSimulationListener(listener);
    }

    @Override
    public long getSimulationDuration() {
        return this.simulation.getSimulationDuration();
    }

    @Override
    public void showView() {
        this.simulationView = new RoadSimView();
        this.simulationView.display();
        this.simulation.addSimulationListener(simulationView);
    }

}
