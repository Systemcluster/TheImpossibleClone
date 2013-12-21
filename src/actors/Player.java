package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import sound.AudioClip;
import sound.ResourceLoader;
import states.Scene;
import core.Actor;

@SuppressWarnings("serial")
public class Player extends Actor {
	
	private double maxHeight = 0;
	
	private double initY = 0;
	
	//w = 0.0018
	private double weight = 0.0022;
	private double force = 0;
	
	private double rotate = 0;
	private double rotationspeed = 0.22;
	
	private double defaultx = 0.1;
	
	private String pathDieSound = "res/sound.wav";
	private String pathJumpSound = "res/Jump.wav";
	private AudioClip asJump;
	private AudioClip asDie;
	private ArrayList<BufferedImage> runnimation = new ArrayList<>();
	
	private boolean canjump = false;
	
	public boolean dead = false;
	
	// force to add to the player on button press (jump)
	private double jumpforce = -0.017;
	// height the player cna jump
	private double jumpheight = 0.21;
	
	public Player(Scene parent) {
		super(parent);
		x = defaultx;
		
		asJump = (AudioClip) ResourceLoader.load(pathJumpSound);
		asJump.open();
		asDie = (AudioClip) ResourceLoader.load(pathDieSound);
		asDie.open();
		w = 0.040;
		h = 0.050;
		
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
		return addForce(jumpforce, jumpheight);
	}
	
	public boolean isGrounded() {
		return y >= parent.getGround();
	}
	
	/**
	 * Checks if player is beneath or slightly over an obstacle.
	 * @return
	 * the touched obstacle
	 */
	public ArrayList<Actor> getTouchedObstacle() {
		//0.00025
		ArrayList<Actor> touched = new ArrayList<>();
		Actor left = new Actor(parent, x, y + force, w, h);// + 0.00025);
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
		if(canjump) {
			asJump.start();	
			this.force = force;
			this.maxHeight = y - maxHeight;
			System.currentTimeMillis();
			this.initY = y;
		}
		
		return canjump;
	}
	
	@Override
	public void update() {
		x = parent.getPosition() + defaultx;
	}
	
	@Override
	public void fixedUpdate() {
		canjump = false;
		
		if(parent.getSpaceState() && y > maxHeight && initY != 0 && maxHeight != 0){
			double meh = (y - initY) <= 0 ? (y - initY) : -0.5; // surfjump fix
			force += weight * (meh/(maxHeight - initY));
		}
		else{
			force += weight;
		}
		
		//-- SURF --
		try {
			//surfjump fix
			this.x -= 0.015;
			this.w += 0.02;
			for(Actor touch:getTouchedObstacle()) {
				if(touch!=null && force > 0 && touch.isGround && !isGrounded()){
					if(!touch.intersects(this)) {
						touch.surf(this);
						y = touch.y-h-0.0001; //0.0002
						force = 0;
						canjump = true;
						/*if(this.x-0.01<touch.x+touch.w)
							canjump = true;
							*/
					}
				}
				//surfjump fix
				this.x += 0.015;
				this.w -= 0.02;
				if(touch!=null&&touch.intersects(this)) {
					touch.collide(this);
				}
				//surfjump fix
				this.x -= 0.015;
				this.w += 0.02;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		finally {
			//surfjump fix
			this.x += 0.015;
			this.w -= 0.02;
		}
		//-- /SURF --
		
		if(!dead)
			y+=force;
		
		//-- GROUND
		if(isGrounded()) {
			y = parent.getGround();
			force = 0;
			canjump = true;
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
			g2D.drawImage(runnimation.get((int) (rotate%4)), parent.getCoordX(x-0.020), parent.getCoordY(y-0.012), parent.getWidth(w+0.04), parent.getHeight(h+0.01), null);
		}

	}
	
}
