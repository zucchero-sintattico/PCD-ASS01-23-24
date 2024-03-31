package model;

public interface Barrier {

    void hitAndWaitAll() throws InterruptedException;

}
