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
		
		try {
			bimage = (BufferedImage) ResourceLoader.load("res/player.png");//ImageIO.read(ResourceLoader.class.getClassLoader().getResource("res/player.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			asJump = (AudioClip) ResourceLoader.load(pathJumpSound);
			asJump.open();
			asDie = (AudioClip) ResourceLoader.load(pathDieSound);
			asDie.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void kill() {
		dead = true;
		asDie.play();
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
		Actor left = new Actor(parent, x, y + force + 0.00025);
		for (Actor child:parent.getActors()){
			if(left.intersects(child)){
				child.surf(this);
				return child;
			}
		}
		return null;
	}

	public boolean addForce(double force, double maxHeight) {
		if(isGrounded() || (getTouchedObstacle()!=null && getTouchedObstacle().isGround)) {
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
			force += weight * ((y - initY)/(maxHeight - initY));
		}
		else{
			force += weight;
		}
		try {
			if(getTouchedObstacle()!=null && force > 0 &&getTouchedObstacle().isGround){
					if(!this.intersects(getTouchedObstacle()))
						y = getTouchedObstacle().y-h-0.0002;
					else {
						// auskommentieren?
						//force = -1;
					}
				force = 0;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		if(!dead)y+=force;
		
		if(isGrounded()) {
			y = parent.getGround();
			force = 0;
		}
		//--/JUMP--
		
		//RENDER
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(Color.black);
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
		//g2D.drawRect(parent.getCoordX(x)+1, parent.getCoordY(y)+1, parent.getWidth(w)-2, parent.getHeight(h)-2);
		
		AffineTransform trans = new AffineTransform();
		trans.setTransform(new AffineTransform());
		
		trans.translate(parent.getCoordX(x)+parent.getWidth(w/2), parent.getCoordY(y)+parent.getHeight(h/2));
		trans.rotate(Math.toRadians(rotate));
		//trans.setToScale(2,2);
		//trans.scale(3, 3);
		
		g2D.drawImage(bimage, trans, null);
		//g2D.drawImage(bimage, parent.getCoordX(x-0.01), parent.getCoordY(y-0.01), parent.getWidth(w+0.02), parent.getHeight(h+0.02), null);

	}
	
}
