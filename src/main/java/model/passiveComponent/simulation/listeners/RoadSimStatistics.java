package model.passiveComponent.simulation.listeners;


import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.environment.Environment;

import java.io.FileWriter;
import java.util.List;

/**
 * Simple class keeping track of some statistics about a traffic simulation
 * - average number of cars
 * - min speed
 * - max speed
 */
public class RoadSimStatistics implements SimulationListener {

	
	private double averageSpeed;
	private double minSpeed;
	private double maxSpeed;
	
	public RoadSimStatistics() {
	}
	
	@Override
	public void notifyInit(int t, List<AbstractCarAgent> agents, Environment env) {
		// log("reset: " + t);
		averageSpeed = 0;
	}

	@Override
	public void notifyStepDone(int t, List<AbstractCarAgent> agents, Environment env) {
		double avSpeed = 0;
		
		maxSpeed = -1;
		minSpeed = Double.MAX_VALUE;
		for (AbstractCarAgent agent: agents) {
			AbstractCarAgent car = (AbstractCarAgent) agent;
			double currSpeed = car.getCurrentSpeed();
			avSpeed += currSpeed;			
			if (currSpeed > maxSpeed) {
				maxSpeed = currSpeed;
			} else if (currSpeed < minSpeed) {
				minSpeed = currSpeed;
			}
		}
		
		if (agents.size() > 0) {
			avSpeed /= agents.size();
		}
		log("average speed: " + avSpeed);
	}
	
	public double getAverageSpeed() {
		return this.averageSpeed;
	}

	public double getMinSpeed() {
		return this.minSpeed;
	}
	
	public double getMaxSpeed() {
		return this.maxSpeed;
	}
	
	
	private void log(String msg) {
		System.out.println("[STAT] " + msg);
		//save to file
		//delete file if exists


		try {
			FileWriter fw = new FileWriter("log.txt", true);
			fw.write(msg + "\n");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void notifySimulationEnded() {
		
	}

	@Override
	public void notifyStat(double averageSpeed) {
		
	}

}
