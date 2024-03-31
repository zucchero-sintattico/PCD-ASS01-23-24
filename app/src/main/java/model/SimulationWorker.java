package model;

import java.util.List;

public class SimulationWorker extends Thread {

    private final List<Runnable> tasks;
    private final int step;
    private final Barrier barrier, barrier2;

    public SimulationWorker(List<Runnable> tasks, int step, Barrier barrier, Barrier barrier2){
        this.tasks = tasks;
        this.step = step;
        this.barrier = barrier;
        this.barrier2 = barrier2;
    }

    @Override
    public void run() {
        for (int i = 0; i < step; i++) {
            this.tasks.forEach(Runnable::run);
            try {
                this.barrier.hitAndWaitAll();
                this.barrier2.hitAndWaitAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
