package model.passiveComponent.agent.perception;



import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.environment.trafficLight.TrafficLightInfo;

import java.util.Optional;

public interface Perception {
    double getRoadPos();

    Optional<TrafficLightInfo> getNearestSem();

    Optional<AbstractCarAgent> getNearestCarInFront();
}
