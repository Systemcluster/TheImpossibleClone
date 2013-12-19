package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import sound.AudioClip;
import sound.ResourceLoader;
import core.Actor;
import core.Scene;

@SuppressWarnings("serial")
public class Player extends Actor {
	
	private double maxHeight = 0;
	
	private double initY = 0;
	
	//w = 0.0018
	private double weight = 0.0022;
	private double force = 0;
	
	private double rotate = 0;
	private double rotationspeed = 0.18;
	
	private double defaultx = 0.1;
	
	private String pathDieSound = "res/sound.wav";
	private String pathJumpSound = "res/Jump.wav";
	private AudioClip asJump;
	private AudioClip asDie;
	private ArrayList<BufferedImage> runnimation = new ArrayList<>();
	
	public boolean dead = false;
	
	// force to add to the player on button press (jump)
	private double jumpforce = -0.017;
	
	public Player(Scene parent) {
		super(parent);
		x = defaultx;
		
		asJump = (AudioClip) ResourceLoader.load(pathJumpSound);
		asJump.open();
		asDie = (AudioClip) ResourceLoader.load(pathDieSound);
		asDie.open();
		w = 0.035;
		h = 0.035;
		
		runnimation.add((BufferedImage) ResourceLoader.load("res/player/p_01.png"));
		runnimation.add((BufferedImage) ResourceLoader.load("res/player/p_02.png"));
		runnimation.add((BufferedImage) ResourceLoader.load("res/player/p_03.png"));
		runnimation.add((BufferedImage) ResourceLoader.load("res/player/p_04.png"));
		
	}
	
	public void kill() {
		dead = true;
		System.out.println("Player died");
		asDie.start();
	}
	
	public boolean jump() {
		return addForce(jumpforce, 0.21);
	}
	
	public boolean isGrounded() {
		return y >= parent.getGround();
		// TODO: add check if player is on an obstacle? (may be redundant)
	}
	
	/**
	 * Checks if player is beneath or slightly over an obstacle.
	 * @return
	 * the touched obstacle
	 */
	public ArrayList<Actor> getTouchedObstacle() {
		//0.00025
		ArrayList<Actor> touched = new ArrayList<>();
		Actor left = new Actor(parent, x, y + force);// + 0.00025);
		for (Actor child:parent.getActors()){
			if(child.x > parent.getPosition() - 1 && child.x < parent.getPosition() + parent.getXWidth()) { //only test near actors
				if(left.intersects(child)){
					touched.add(child);
				}
				else {
				}
			}
		}
		return touched;
	}

	public boolean addForce(double force, double maxHeight) {
		//SURFJUMPFIX
		this.w += 0.05;
		this.x -= 0.01;
		boolean dojump = false;
		if(isGrounded()) dojump = true;
		for(Actor a:getTouchedObstacle()) {
			if((a.isGround && !a.intersects(this))) {
				dojump = true;
			}
		}
		
		if(dojump) {
			asJump.start();	
			this.force = force;
			this.maxHeight = y - maxHeight;
			System.currentTimeMillis();
			this.initY = y;
		}
		
		//SURFJUMPFIX
		this.w -= 0.05;
		this.x += 0.01;
		
		return dojump;
	}
	
	@Override
	public void update() {
	
		//x += parent.getScrollSpeed();
		x = parent.getPosition() + defaultx;
		/*double ax=trans.getTranslateX();
		double ay=trans.getTranslateY();
		trans.translate(x, y);
		trans.rotate(0.02, x, y);
		trans.translate(ax, ay);*/
		
	}
	
	@Override
	public void fixedUpdate() {
		
		//System.out.println(force);
		
		if(parent.getSpaceState() && y > maxHeight && initY != 0 && maxHeight != 0){
			double meh = (y - initY) <= 0 ? (y - initY) : -0.5; // surfjump fix
			force += weight * (meh/(maxHeight - initY));
			//System.out.println(weight * ((y - initY)/(maxHeight - initY)));
			//System.out.println((maxHeight - initY));
		}
		else{
			force += weight;
		}
		
		//-- SURF --
		try {
			for(Actor touch:getTouchedObstacle()) {
				if(touch!=null && force > 0 && touch.isGround && !isGrounded()){
					if(!touch.intersects(this)) {
						touch.surf(this);
						y = touch.y-h-0.0001; //0.0002
						force = 0;
					}
				}
				if(touch!=null&&touch.intersects(this)) {
					touch.collide(this);
				}
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
		
		if(isGrounded() || getTouchedObstacle().size() != 0)
			rotate += rotationspeed;
	}

	@Override
	public void paintComponent(Graphics g ) {
		Graphics2D g2D = (Graphics2D) g;
		
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
		if(parent.classic_mode) {
			g2D.setColor(Color.WHITE);
			g2D.fillRect(parent.getCoordX(x)+1, parent.getCoordY(y)+1, parent.getWidth(w)-2, parent.getHeight(h)-2);
			g2D.setColor(Color.black);
			g2D.drawRect(parent.getCoordX(x)+1, parent.getCoordY(y)+1, parent.getWidth(w)-2, parent.getHeight(h)-2);
		}
		else {
			g2D.setColor(Color.LIGHT_GRAY);
			g2D.drawRect(parent.getCoordX(x)+1, parent.getCoordY(y)+1, parent.getWidth(w)-2, parent.getHeight(h)-2);
			g2D.drawImage(runnimation.get((int) (rotate%4)), parent.getCoordX(x-0.009), parent.getCoordY(y-0.005), parent.getWidth(w+0.02), parent.getHeight(h+0.01), null);
		}

	}
	
}
