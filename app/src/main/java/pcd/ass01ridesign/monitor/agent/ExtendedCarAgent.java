package pcd.ass01ridesign.monitor.agent;


import pcd.ass01ridesign.monitor.agent.action.MoveForward;
import pcd.ass01ridesign.monitor.agent.state.ExtendedCarAgentState;
import pcd.ass01ridesign.monitor.environment.Environment;
import pcd.ass01ridesign.monitor.environment.Road;
import pcd.ass01ridesign.monitor.environment.TrafficLightInfo;

import java.util.Optional;

public class ExtendedCarAgent extends AbstractCarAgent{

    protected static final int SEM_NEAR_DIST = 100;
    private ExtendedCarAgentState state = ExtendedCarAgentState.STOPPED;

    public ExtendedCarAgent(String agentID, Environment environment, Road road, double initialPosition, double acceleration, double deceleration, double maxSpeed, double stepSize) {
        super(agentID, environment, road, initialPosition, acceleration, deceleration, maxSpeed, stepSize);
    }

    @Override
    protected void decide() {
        switch (state) {
            case ExtendedCarAgentState.STOPPED:
                if (!detectedNearCar()) {
                    state = ExtendedCarAgentState.ACCELERATING;
                }
                break;
            case ExtendedCarAgentState.ACCELERATING:
                if (detectedNearCar()) {
                    state = ExtendedCarAgentState.DECELERATING_BECAUSE_OF_A_CAR;
                } else if (detectedRedOrOrgangeSemNear()) {
                    state = ExtendedCarAgentState.DECELERATING_BECAUSE_OF_A_NOT_GREEN_SEM;
                } else {
                    this.currentSpeed += acceleration * stepSize;
                    if (currentSpeed >= maxSpeed) {
                        state = ExtendedCarAgentState.MOVING_CONSTANT_SPEED;
                    }
                }
                break;
            case ExtendedCarAgentState.MOVING_CONSTANT_SPEED:
                if (detectedNearCar()) {
                    state = ExtendedCarAgentState.DECELERATING_BECAUSE_OF_A_CAR;
                } else if (detectedRedOrOrgangeSemNear()) {
                    state = ExtendedCarAgentState.DECELERATING_BECAUSE_OF_A_NOT_GREEN_SEM;
                }
                break;
            case ExtendedCarAgentState.DECELERATING_BECAUSE_OF_A_CAR:
                this.currentSpeed -= deceleration * stepSize;
                if (this.currentSpeed <= 0) {
                    state =  ExtendedCarAgentState.STOPPED;
                } else if (this.carFarEnough()) {
                    state = ExtendedCarAgentState.WAIT_A_BIT;
                    waitingTime = 0;
                }
                break;
            case ExtendedCarAgentState.DECELERATING_BECAUSE_OF_A_NOT_GREEN_SEM:
                this.currentSpeed -= deceleration * stepSize;
                if (this.currentSpeed <= 0) {
                    state =  ExtendedCarAgentState.WAITING_FOR_GREEN_SEM;
                } else if (!detectedRedOrOrgangeSemNear()) {
                    state = ExtendedCarAgentState.ACCELERATING;
                }
                break;
            case ExtendedCarAgentState.WAIT_A_BIT:
                waitingTime += stepSize;
                if (waitingTime > MAX_WAITING_TIME) {
                    state = ExtendedCarAgentState.ACCELERATING;
                }
                break;
            case ExtendedCarAgentState.WAITING_FOR_GREEN_SEM:
                if (detectedGreenSem()) {
                    state = ExtendedCarAgentState.ACCELERATING;
                }
                break;
        }

        if (currentSpeed > 0) {
            selectedAction = new MoveForward(currentSpeed * stepSize);
        }
    }

    private boolean detectedNearCar() {
        Optional<CarAgentInfo> car = currentPerception.getNearestCarInFront();
        if (car.isEmpty()) {
            return false;
        } else {
            double dist = car.get().getPos() - currentPerception.getRoadPos();
            return dist < CAR_NEAR_DIST;
        }
    }

    private boolean detectedRedOrOrgangeSemNear() {
        Optional<TrafficLightInfo> sem = currentPerception.getNearestSem();
        if (sem.isEmpty() || sem.get().getSem().isGreen()) {
            return false;
        } else {
            double dist = sem.get().getRoadPos() - currentPerception.getRoadPos();
            return dist > 0 && dist < SEM_NEAR_DIST;
        }
    }


    private boolean detectedGreenSem() {
        Optional<TrafficLightInfo> sem = currentPerception.getNearestSem();
        return (!sem.isEmpty() && sem.get().getSem().isGreen());
    }

    private boolean carFarEnough() {
        Optional<CarAgentInfo> car = currentPerception.getNearestCarInFront();
        if (car.isEmpty()) {
            return true;
        } else {
            double dist = car.get().getPos() - currentPerception.getRoadPos();
            return dist > CAR_FAR_ENOUGH_DIST;
        }
    }
}
