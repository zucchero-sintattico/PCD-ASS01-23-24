package model;

import java.util.List;

public class SimulationWorker extends Thread {

    private final List<Runnable> tasks;
    private final int step;
    private final Barrier barrier;

    public SimulationWorker(List<Runnable> tasks, int step, Barrier barrier){
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
