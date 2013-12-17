package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import sound.AudioClip;
import sound.ResourceLoader;
import core.Actor;
import core.Scene;

public class Player extends Actor {
	
	//TODO : CHANGE VALUES
	private double maxHeight = 0;
	//-- TODO
	
	private double msAirStart = 0;
	private double initY = 0;
	
	//w = 0.0018
	private double weight = 0.0022;
	private double force = 0;
	
	private double rotate = 0;
	
	private String pathDieSound = "res/sound.wav";
	private String pathJumpSound = "res/Jump.wav";
	private AudioClip asJump;
	private AudioClip asDie;
	
	public boolean dead = false;
	
	private BufferedImage bimage;
	
	// force to add to the player on button press (jump)
	private double jumpforce = -0.017;
	
	public Player(Scene parent) {
		super(parent);
		x = 0.1;
		
		bimage = (BufferedImage) ResourceLoader.load("res/player.png");//ImageIO.read(ResourceLoader.class.getClassLoader().getResource("res/player.png"));
		
		asJump = (AudioClip) ResourceLoader.load(pathJumpSound);
		asJump.open();
		asDie = (AudioClip) ResourceLoader.load(pathDieSound);
		asDie.open();
		//x = 0.035;
		//y = 0.035;
		w = 0.035;
		h = 0.035;
		
	}
	
	public void kill() {
		dead = true;
		asDie.start();
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
	 * the touched obstacle
	 */
	public Actor getTouchedObstacle() {
		//0.00025
		Actor left = new Actor(parent, x, y + force + 0.00025);
		for (Actor child:parent.getActors()){
			if(left.intersects(child)){
				child.surf(this);
				return child;
			}
			else {
			}
		}
		return null;
	}

	public boolean addForce(double force, double maxHeight) {
		//SURFJUMPFIX
		this.w += 0.05;
		this.x -= 0.01;
		if(isGrounded() || (getTouchedObstacle()!=null && getTouchedObstacle().isGround)) {
			asJump.start();
			
			//SURFJUMPFIX
			this.w -= 0.05;
			this.x += 0.01;
			
			this.force = force;
			this.maxHeight = y - maxHeight;
			this.msAirStart = System.currentTimeMillis();
			this.initY = y;
			return true;
		}
		
		//SURFJUMPFIX
		this.w -= 0.05;
		this.x += 0.01;
		
		return false;
	}
	
	@Override
	public void update() {
	
		x += parent.getScrollSpeed();
		/*double ax=trans.getTranslateX();
		double ay=trans.getTranslateY();
		trans.translate(x, y);
		trans.rotate(0.02, x, y);
		trans.translate(ax, ay);*/
		rotate += 2;
	}

	@Override
	public void paintComponent(Graphics g ) {

		//--JUMP--
	
		if(parent.getSpaceState() && y > maxHeight ){
			double meh = (y - initY) <= 0 ? (y - initY) : -0.5; // surfjump fix
			force += weight * (meh/(maxHeight - initY));
			//System.out.println(weight * ((y - initY)/(maxHeight - initY)));
		}
		else{
			force += weight;
		}
		
		//-- SURF --
		try {
			if(getTouchedObstacle()!=null && force > 0 && getTouchedObstacle().isGround){
				System.out.println(force);
				if(!this.intersects(getTouchedObstacle()))
					//0.0002
					y = getTouchedObstacle().y-h-0.0001;
				else {
					// auskommentieren?
					//force = -1;
				}
				force = 0;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		//-- /SURF --
		if(!dead)y+=force;
		
		//-- GROUND
		if(isGrounded()) {
			y = parent.getGround();
			force = 0;
		}
		//-- /GROUND --
		//--/JUMP--
		
		//RENDER
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(Color.LIGHT_GRAY);
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
		g2D.drawRect(parent.getCoordX(x)+1, parent.getCoordY(y)+1, parent.getWidth(w)-2, parent.getHeight(h)-2);
		
		//AffineTransform trans = new AffineTransform();
		//trans.setTransform(new AffineTransform());
		
		//trans.translate(parent.getCoordX(x)+parent.getWidth(w/2), parent.getCoordY(y)+parent.getHeight(h/2));
		//trans.rotate(Math.toRadians(rotate));
		//trans.setToScale(2,2);
		//trans.scale(3, 3);
		
		//g2D.drawImage(bimage, trans, null);
		g2D.drawImage(bimage, parent.getCoordX(x-0.005), parent.getCoordY(y-0.005), parent.getWidth(w+0.01), parent.getHeight(h+0.01), null);

	}
	
}
