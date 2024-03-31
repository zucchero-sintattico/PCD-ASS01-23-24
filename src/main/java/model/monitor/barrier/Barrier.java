package model.monitor.barrier;

public interface Barrier {

    void hitAndWaitAll() throws InterruptedException;

}
