package logic.passiveComponent.agent.action;

public class MoveForward implements Action {

	private final double distance;

	public MoveForward(double distance) {
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}


}