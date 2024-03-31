package pcd.ass01.passiveComponent.agent;

import pcd.ass01.passiveComponent.agent.task.ParallelTask;
import pcd.ass01.passiveComponent.agent.task.SerialTask;

public interface Agent {

    SerialTask getSerialTask();

    ParallelTask getParallelTask();

}
