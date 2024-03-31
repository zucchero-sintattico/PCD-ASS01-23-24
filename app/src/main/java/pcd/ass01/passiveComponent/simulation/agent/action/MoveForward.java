package pcd.ass01.passiveComponent.simulation.agent.action;

import pcd.ass01.passiveComponent.simulation.agent.action.Action;

/**
 * Car agent move forward action
 */
public class MoveForward implements Action {

    public final double distance;

    public MoveForward(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }
}
