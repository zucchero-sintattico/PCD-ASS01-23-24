package pcd.ass01.passiveComponent.agent.action;

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
