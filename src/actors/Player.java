package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import sound.AudioClip;
import sound.AudioSource;
import sound.ResourceLoader;
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
	
	private String pathDieSound = "res/sound.wav";
	private String pathJumpSound = "res/Jump.wav";
	private AudioClip asJump;
	private AudioSource asDie;
	
	private BufferedImage bimage;
	
	// force to add to the player on button press (jump)
	private double jumpforce = -0.017;
	
	public Player(Scene parent) {
		super(parent);
		x = 0.1;
		
		try {
			bimage = (BufferedImage) ResourceLoader.load("res/player.png");//ImageIO.read(ResourceLoader.class.getClassLoader().getResource("res/player.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			asJump = (AudioClip) ResourceLoader.load(pathJumpSound);
			//if(!asJump.isOpen()) {
				asJump.open();
			//}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean jump() {
		return addForce(jumpforce, 0.21);
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
		Actor left = new Actor(parent, x, y + force + 0.00025);
		for (Actor child:parent.getActors()){
			if(left.intersects(child))
				return true;
		}
		return false;
	}
	public Actor getTouchedObstacle() {
		Actor left = new Actor(parent, x, y + force + 0.00025);
		for (Actor child:parent.getActors()){
			if(left.intersects(child))
				return child;
		}
		return null;
	}

	public boolean addForce(double force, double maxHeight) {
		if(isGrounded() || touchObstacle()){
			asJump.play();
			
			this.force = force;
			this.maxHeight = y - maxHeight;
			this.msAirStart = System.currentTimeMillis();
			this.initY = y;
			return true;
		}
		return false;
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
		if(touchObstacle() && force > 0){
			if(getTouchedObstacle()!=null) {
				if(!this.intersects(getTouchedObstacle()))
					y = getTouchedObstacle().y-h-0.0002;
				else {
					//force = -1;
				}
			}
			force = 0;
		}
		y+=force;
		
		if(isGrounded()) {
			y = parent.getGround();
			force = 0;
		}
		//--/JUMP--
		
		//RENDER
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(Color.black);
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
		g2D.drawRect(parent.getCoordX(x)+1, parent.getCoordY(y)+1, parent.getWidth(w)-2, parent.getHeight(h)-2);
		
		g2D.drawImage(bimage, parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h), null);

	}
	
}
