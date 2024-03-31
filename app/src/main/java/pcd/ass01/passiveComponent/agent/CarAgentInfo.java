package pcd.ass01.passiveComponent.agent;

import pcd.ass01.passiveComponent.agent.AbstractAgent;
import pcd.ass01.simtrafficbase.Road;
import pcd.ass01ridesign.passiveComponent.agent.AbstractCarAgent;

public class CarAgentInfo {

	private AbstractAgent car;
	private double pos;
	private Road road;
	
	public CarAgentInfo(AbstractAgent car, Road road, double pos) {
		this.car = car;
		this.road = road;
		this.pos = pos;
	}
	
	public double getPos() {
		return pos;
	}
	
	public void updatePos(double pos) {
		this.pos = pos;
	}
	
	public AbstractAgent getCar() {
		return car;
	}	
	
	public Road getRoad() {
		return road;
	}
}
