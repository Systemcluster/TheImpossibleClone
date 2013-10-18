package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import core.Actor;
import core.Scene;

public class Player extends Actor {
	
	private double weight = 0.0015;
	private double force = 0;
	
	public Player(Scene parent) {
		super(parent);
		x = 0.1;
	}
	
	public boolean isGrounded() {
		return y >= parent.getGround();
		// TODO: add check if player is on an obstacle
	}
	
	/**
	 * Checks if player is beneath or over an obstacle
	 * @return
	 */
	public boolean touchObstacle(){
		Actor left = new Actor(parent, x, y + force);
		Actor right = new Actor(parent, x + w, y + force);
		for (Actor child:parent.getActors()){
			if(left.intersects(child) || right.intersects(child))
				return true;
		}
		return false;
	}

	public void addForce(double force) {
		if(isGrounded() || touchObstacle()) this.force = force;
	}
	
	@Override
	public void update() {
		x += parent.getScrollSpeed();
		y += force;
		force += weight;
		
		if(touchObstacle()) force = 0;
		if(isGrounded()) y = parent.getGround(); 
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(Color.black);
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
		g2D.drawRect(parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h));
	}
	
}
