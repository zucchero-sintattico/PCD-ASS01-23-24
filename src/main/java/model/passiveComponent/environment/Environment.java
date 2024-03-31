package model.passiveComponent.environment;

import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.agent.action.Action;
import model.passiveComponent.agent.perception.Perception;
import model.passiveComponent.environment.road.Road;
import model.passiveComponent.environment.trafficLight.TrafficLight;
import model.passiveComponent.environment.trafficLight.TrafficLightState;
import model.passiveComponent.simulation.SimulationComponent;
import utils.Point2D;

public interface Environment extends SimulationComponent {

    //todo refactor
    //todo missing id
    void registerNewCar(AbstractCarAgent abstractCarAgent);

    Road createRoad(Point2D p0, Point2D p1);

    TrafficLight createTrafficLight(Point2D pos, TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration);

    Perception getCurrentPercept(String agentID);

    void doAction(String agentID, Action selectedAction);

    void step();
}
