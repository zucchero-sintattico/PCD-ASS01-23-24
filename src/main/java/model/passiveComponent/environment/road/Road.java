package model.passiveComponent.environment.road;



import model.passiveComponent.environment.trafficLight.TrafficLight;
import model.passiveComponent.environment.trafficLight.TrafficLightInfo;
import utils.Point2D;

import java.util.List;

public interface Road {

    Point2D getFrom();

    Point2D getTo();

    void addTrafficLight(TrafficLight sem, double pos);

    List<TrafficLightInfo> getTrafficLights();

    double getLen();
}
