package model.passiveComponent.agent.agentImpl.base;


import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.agent.action.MoveForward;
import model.passiveComponent.environment.Environment;
import model.passiveComponent.environment.road.Road;

import java.util.Optional;

public class BaseCarAgent extends AbstractCarAgent {

    private BaseCarAgentState state = BaseCarAgentState.STOPPED;

    public BaseCarAgent(String agentID, Environment environment, Road road, double initialPosition, double acceleration, double deceleration, double maxSpeed) {
        super(agentID, environment, road, initialPosition, acceleration, deceleration, maxSpeed);
    }

    public BaseCarAgent(String agentID, Environment environment, Road road, double initialPosition, int seed) {
        super(agentID, environment, road, initialPosition,  seed);
    }

    @Override
    protected void decide() {

        switch (state) {
            case BaseCarAgentState.STOPPED:
                if (!detectedNearCar()) {
                    state = BaseCarAgentState.ACCELERATING;
                }
                break;
            case BaseCarAgentState.ACCELERATING:
                if (detectedNearCar()) {
                    state = BaseCarAgentState.DECELERATING_BECAUSE_OF_A_CAR;
                } else {
                    currentSpeed += acceleration * stepSize;
                    if (currentSpeed >= maxSpeed) {
                        state = BaseCarAgentState.MOVING_CONSTANT_SPEED;
                    }
                }
                break;
            case BaseCarAgentState.MOVING_CONSTANT_SPEED:
                if (detectedNearCar()) {
                    state = BaseCarAgentState.DECELERATING_BECAUSE_OF_A_CAR;
                }
                break;
            case BaseCarAgentState.DECELERATING_BECAUSE_OF_A_CAR:
                currentSpeed -= deceleration * stepSize;
                if (currentSpeed <= 0) {
                    state = BaseCarAgentState.STOPPED;
                } else if (carFarEnough()) {
                    state = BaseCarAgentState.WAIT_A_BIT;
                    waitingTime = 0;
                }
                break;
            case BaseCarAgentState.WAIT_A_BIT:
                waitingTime += stepSize;
                if (waitingTime > MAX_WAITING_TIME) {
                    state = BaseCarAgentState.ACCELERATING;
                }
                break;
        }

        if (currentSpeed > 0) {
            selectedAction = new MoveForward(currentSpeed * stepSize);
        }

    }

    private boolean detectedNearCar() {
        Optional<AbstractCarAgent> car = currentPerception.getNearestCarInFront();
        if (car.isEmpty()) {
            return false;
        } else {
            double dist = car.get().getPosition() - currentPerception.getRoadPos();
            return dist < CAR_NEAR_DIST;
        }
    }


    private boolean carFarEnough() {
        Optional<AbstractCarAgent> car = currentPerception.getNearestCarInFront();
        if (car.isEmpty()) {
            return true;
        } else {
            double dist = car.get().getPosition() - currentPerception.getRoadPos();
            return dist > CAR_FAR_ENOUGH_DIST;
        }
    }
}
