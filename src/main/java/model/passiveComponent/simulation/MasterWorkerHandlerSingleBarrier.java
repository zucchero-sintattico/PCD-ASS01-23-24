package model.passiveComponent.simulation;

import model.activeComponent.SimulationWorkerSingleBarrier;
import model.monitor.barrier.CyclicBarrier;
import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.agent.task.ParallelTask;

import java.util.ArrayList;
import java.util.List;

public class MasterWorkerHandlerSingleBarrier {
    private List<ParallelTask> listOFAgent;
    public MasterWorkerHandlerSingleBarrier(int numOfThread, List<AbstractCarAgent> listOFAgent, int numOfStep,
            CyclicBarrier barrier) {

        // this.listOFAgent = new ArrayList<>(listOFAgent);
        this.listOFAgent = new ArrayList<>();
        for (AbstractCarAgent agent : listOFAgent) {
            this.listOFAgent.add(agent.getParallelAction());

        }
        int size = listOFAgent.size();
        int split = size / numOfThread;
        List<Integer> splitIndex = new ArrayList<>();
        for (int i = 1; i < numOfThread + 1; i++) {
            splitIndex.add(i * split + size % numOfThread);
        }

        for (int i = 0; i < numOfThread; i++) {
            int start = i == 0 ? 0 : splitIndex.get(i - 1);
            int end = splitIndex.get(i);
            List<ParallelTask> subList = this.listOFAgent.subList(start, end);
            new SimulationWorkerSingleBarrier(subList, numOfStep, barrier).start();

        }

    }

}
