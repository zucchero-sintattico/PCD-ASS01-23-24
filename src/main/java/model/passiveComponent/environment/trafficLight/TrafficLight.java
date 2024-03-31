package model.passiveComponent.environment.trafficLight;

import utils.Point2D;

public interface TrafficLight {

    boolean isGreen();

    void step(int simulationStep);

    void init();

    boolean isRed();

    boolean isYellow();

    Point2D getPos();
}
