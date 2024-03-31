package model;

import pcd.ass01.passiveComponent.simulation.AbstractSimulation;

public class SimulationRunner extends Thread {

    private final AbstractSimulation simulation;
    private final SimulationState state;

    public SimulationRunner(AbstractSimulation simulation) {
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

