package model.activeComponent;

import model.monitor.state.SimulationState;
import model.passiveComponent.simulation.Simulation;

public class SimulationRunner extends Thread {

    private final Simulation simulation;
    private final SimulationState state;

    public SimulationRunner(Simulation simulation) {
        this.simulation = simulation;
        this.state = simulation.getState();
    }

    @Override
    public void run() {
        state.startSimulation();
        while (state.isSimulationRunning()) {
            simulation.doStep();
        }
    }



}

