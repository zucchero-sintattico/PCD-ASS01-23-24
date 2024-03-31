package model.passiveComponent.environment.trafficLight;

import utils.Point2D;

public class TrafficLightImpl implements TrafficLight {
    private TrafficLightState state, initialState;
    private int currentTimeInState;
    private int redDuration, greenDuration, yellowDuration;
    private Point2D pos;

    public TrafficLightImpl(Point2D pos, TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration) {
        this.redDuration = redDuration;
        this.greenDuration = greenDuration;
        this.yellowDuration = yellowDuration;
        this.pos = pos;
        this.initialState = initialState;
    }



    @Override
    public void step(int simulationStep) {
        switch (state) {
            case GREEN:
                currentTimeInState += simulationStep;
                if (currentTimeInState >= greenDuration) {
                    state = TrafficLightState.YELLOW;
                    currentTimeInState = 0;
                }
                break;
            case RED:
                currentTimeInState += simulationStep;
                if (currentTimeInState >= redDuration) {
                    state = TrafficLightState.GREEN;
                    currentTimeInState = 0;
                }
                break;
            case YELLOW:
                currentTimeInState += simulationStep;
                if (currentTimeInState >= yellowDuration) {
                    state = TrafficLightState.RED;
                    currentTimeInState = 0;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void init() {
        state = initialState;
        currentTimeInState = 0;
    }

    @Override
    public boolean isGreen() {
        return state.equals(TrafficLightState.GREEN);
    }
    @Override
    public boolean isRed() {
        return state.equals(TrafficLightState.RED);
    }
    @Override
    public boolean isYellow() {
        return state.equals(TrafficLightState.YELLOW);
    }
    @Override
    public Point2D getPos() {
        return pos;
    }
}
