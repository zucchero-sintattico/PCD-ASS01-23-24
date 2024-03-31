package model.monitor.barrier;

public class BaseBarrier implements Barrier {

    //single use barrier

    private final int numberOfParticipants;
    private int arrived = 0;

    public BaseBarrier(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    @Override
    public synchronized void hitAndWaitAll() throws InterruptedException {
        arrived++;
        if (arrived == numberOfParticipants) {
            notifyAll();
        } else {
            while (arrived < numberOfParticipants) {
                wait();
            }
        }
    }


}
