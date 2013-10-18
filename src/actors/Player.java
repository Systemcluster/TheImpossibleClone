package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import core.Actor;
import core.Scene;

public class Player extends Actor {
	
	//TODO : CHANGE VALUES
	private double msAirMax = 0;
	private double maxHeight = 0;
	//-- TODO
	
	private double msAirStart = 0;
	private double msAir = 0;
	private double initY = 0;
	
	//w = 0.0018
	private double weight = 0.0022;
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

	public void addForce(double force, double maxHeight) {
		if(isGrounded() || touchObstacle()){
			this.force = force;
			this.maxHeight = y - maxHeight;
			this.msAirStart = System.currentTimeMillis();
			this.initY = y;
		}
	}
	
	@Override
	public void update() {
	
		x += parent.getScrollSpeed();
		
	}
	
	@Override
	public void paintComponent(Graphics g ) {

		//--JUMP--
		if(isGrounded() || touchObstacle()){
			msAir = 0;
		}
		else{
			msAir = System.currentTimeMillis() - msAirStart;
			 
		}
		
		if(parent.getSpaceState() && y > maxHeight ){
			force += weight * ((y - initY)/(maxHeight - initY));
		}
		else{
			force += weight;
		}
		if(touchObstacle() && force > 0) force = 0;
		y+=force;
		
		if(isGrounded()) y = parent.getGround(); 
		//--/JUMP--
		
		//RENDER
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(Color.black);
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
		g2D.drawRect(parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h));
	

	}
	
}
