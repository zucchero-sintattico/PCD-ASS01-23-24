package model.passiveComponent.agent;

import model.passiveComponent.agent.task.ParallelTask;
import model.passiveComponent.agent.task.SerialTask;
import model.passiveComponent.simulation.SimulationComponent;

public interface Agent extends SimulationComponent {

    ParallelTask getParallelAction();

    SerialTask getSerialAction();

}
