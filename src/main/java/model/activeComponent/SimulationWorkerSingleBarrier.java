package model.activeComponent;

import model.monitor.barrier.CyclicBarrier;
import model.passiveComponent.agent.task.ParallelTask;

import java.util.List;

public class SimulationWorkerSingleBarrier extends Thread {

    private final List<ParallelTask> tasks;
    private final int step;
    private final CyclicBarrier barrier;

    public SimulationWorkerSingleBarrier(List<ParallelTask> tasks, int step, CyclicBarrier barrier){
        this.tasks = tasks;
        this.step = step;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        for (int i = 0; i < step; i++) {
            this.tasks.forEach(Runnable::run);
            try {
                this.barrier.hitAndWaitAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
