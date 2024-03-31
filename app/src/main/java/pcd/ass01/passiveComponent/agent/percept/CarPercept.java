package pcd.ass01.passiveComponent.agent.percept;

import java.util.Optional;

import pcd.ass01.passiveComponent.agent.CarAgentInfo;
import pcd.ass01.simtrafficbase.TrafficLightInfo;

/**
 * 
 * Percept for Car Agents
 * 
 * - position on the road
 * - nearest car, if present (distance)
 * - nearest semaphore, if presente (distance)
 * 
 */
public class CarPercept implements Percept {
    private double roadPos;
    private Optional<CarAgentInfo> nearestCarInFront;
    private Optional<TrafficLightInfo> nearestSem;

    public CarPercept(double roadPos, Optional<CarAgentInfo> nearestCarInFront, Optional<TrafficLightInfo> nearestSem) {
        this.roadPos = roadPos;
        this.nearestCarInFront = nearestCarInFront;
        this.nearestSem = nearestSem;
    }

    public double getRoadPos() {
        return roadPos;
    }

    public Optional<CarAgentInfo> getNearestCarInFront() {
        return nearestCarInFront;
    }

    public Optional<TrafficLightInfo> getNearestSem() {
        return nearestSem;
    }
}
