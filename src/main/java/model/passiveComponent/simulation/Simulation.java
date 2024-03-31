package model.passiveComponent.simulation;

import model.monitor.state.SimulationState;
import model.passiveComponent.simulation.listeners.SimulationListener;

public interface Simulation {

    void setup(int numSteps, int numOfThread);

    void addSimulationListener(SimulationListener listener);

    void doStep();

    long getSimulationDuration();

    long getAverageTimePerCycle();

    void syncWithTime(int nCyclesPerSec);

    SimulationState getState();
}
