package controller;

import model.passiveComponent.simulation.SimulationType;
import model.passiveComponent.simulation.listeners.SimulationListener;

public interface Controller {
    
    void startSimulation();

    void stopSimulation();

    void setupSimulation(SimulationType type, int numberOfSteps, int numberOfThread);

    void showView();

    void attachListener(SimulationListener listener);

    int getAvaiableProcessor();

    long getSimulationDuration();
}
