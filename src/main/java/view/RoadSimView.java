package view;

import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.environment.Environment;
import model.passiveComponent.environment.road.Road;
import model.passiveComponent.environment.RoadsEnvironment;
import model.passiveComponent.environment.trafficLight.TrafficLight;
import model.passiveComponent.simulation.listeners.SimulationListener;
import utils.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RoadSimView extends JFrame implements SimulationListener {

	private RoadSimViewPanel panel;
	private static final int CAR_DRAW_SIZE = 10;
	
	public RoadSimView() {
		super("RoadSim View");
		setSize(1500,600);
			
		panel = new RoadSimViewPanel(1500,600); 
		panel.setSize(1500, 600);

		JPanel cp = new JPanel();
		LayoutManager layout = new BorderLayout();
		cp.setLayout(layout);
		cp.add(BorderLayout.CENTER,panel);
		setContentPane(cp);		
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
	}
	
	public void display() {
		SwingUtilities.invokeLater(() -> {
			this.setVisible(true);
		});
	}

	@Override
	public void notifyInit(int t, List<AbstractCarAgent> agents, Environment env) {

	}

	@Override
	public void notifyStepDone(int t,List<AbstractCarAgent> agents, Environment env) {
		var e = ((RoadsEnvironment) env);
		panel.update(e.getRoads(), e.getAgentInfo(), e.getTrafficLights());
	}
	
	
	class RoadSimViewPanel extends JPanel {
		
		List<AbstractCarAgent> cars;
		List<Road> roads;
		List<TrafficLight> sems;
		
		public RoadSimViewPanel(int w, int h){
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);   
	        Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2.clearRect(0,0,this.getWidth(),this.getHeight());
			
			if (roads != null) {
				for (var r: roads) {
					g2.drawLine((int)r.getFrom().getX(), (int)r.getFrom().getY(), (int)r.getTo().getX(), (int)r.getTo().getY());
				}
			}
			
			if (sems != null) {
				for (var s: sems) {
					if (s.isGreen()) {
						g.setColor(new Color(0, 255, 0, 255));
					} else if (s.isRed()) {
						g.setColor(new Color(255, 0, 0, 255));
					} else {
						g.setColor(new Color(255, 255, 0, 255));
					}
					g2.fillRect((int)(s.getPos().getX()-5), (int)(s.getPos().getY()-5), 10, 10);
				}
			}
			int i = 0;
			Color c1 = new Color(255,0,0);
			Color c2 = new Color(0,255,0);
			//g.setColor(new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)));

			if (cars != null) {
				for (var c: cars) {
					double pos = c.getPosition();
					Road r = c.getRoad();
					Vector2D dir = Vector2D.makeV2d(r.getFrom(), r.getTo()).getNormalized().mul(pos);
					g.setColor(c2);
					if (i == 0) {
						g.setColor(c1);
						i++;
					}
					g2.fillOval((int)(r.getFrom().getX() + dir.getX() - CAR_DRAW_SIZE/2), (int)(r.getFrom().getY() + dir.getY() - CAR_DRAW_SIZE/2), CAR_DRAW_SIZE , CAR_DRAW_SIZE);
				}
			}
  	   }
	
	   public void update(List<Road> roads, 
			   			  List<AbstractCarAgent> cars,
			   			List<TrafficLight> sems) {
		   this.roads = roads;
		   this.cars = cars;
		   this.sems = sems;
		   repaint();
	   }
	}


	@Override
	public void notifySimulationEnded() {
	}

	@Override
	public void notifyStat(double averageSpeed) {
		
	}
}
