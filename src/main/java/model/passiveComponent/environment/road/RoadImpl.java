package model.passiveComponent.environment.road;

import model.passiveComponent.environment.trafficLight.TrafficLight;
import model.passiveComponent.environment.trafficLight.TrafficLightInfo;
import utils.Point2D;

import java.util.ArrayList;
import java.util.List;

public class RoadImpl implements Road {
    //todo refactor


    private double len;
    private Point2D from;
    private Point2D to;
    private List<TrafficLightInfo> trafficLights;

    public RoadImpl(Point2D p0, Point2D p1) {
        this.from = p0;
        this.to = p1;
        this.len = Point2D.len(from, to);
        trafficLights = new ArrayList<>();
    }

    @Override
    public double getLen() {
        return len;
    }

    @Override
    public Point2D getFrom() {
        return from;
    }

    @Override
    public Point2D getTo() {
        return to;
    }

    @Override
    public void addTrafficLight(TrafficLight sem, double pos) {
        trafficLights.add(new TrafficLightInfo(sem, this, pos));
    }

    @Override
    public List<TrafficLightInfo> getTrafficLights(){
        return trafficLights;
    }
}
