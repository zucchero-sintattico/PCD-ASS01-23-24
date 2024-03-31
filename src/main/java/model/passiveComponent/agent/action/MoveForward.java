package model.passiveComponent.agent.action;

public class MoveForward implements Action {

    private final double distance;

    public MoveForward(double distance){
        this.distance = distance;
    }

//    @Override
    public double getDistance() {
        return distance;
    }





}
