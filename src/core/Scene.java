package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import javax.swing.JPanel;

import actors.Player;

/**
 * Scene class. Base class for Scenes to hold and display actors.
 */
public class Scene extends JPanel {

	private double ytiles = 1;
	private HashSet<Actor> childs;
	private Actor player;
	
	// current scroll position
	private double xposition = 0;
	
	// initial scroll speed
	private double xscrollspeed = 0.012;
	// scroll increment each round
	private double xscrollinc = 0.0025;
	// steps to perform scrolling in (for collision)
	private double xscrollsteps = 0.01;
	// value that holds current scroll speed (for stepwise movement)
	private double xscrolltmp = 0;
	
	// with of the scene
	private double xsize = 2.3;
	// position of the ground (0 to 1)
	private double ground = 0.8;
	
	// force to add to the player on button press (jump)
	private double jumpforce = -0.026;
	
	private int round = 1;
	private boolean paused = false;

	public Scene() {
		super();
		
		childs = new HashSet<Actor>();
		
		this.setFocusable(true);
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(paused) paused = false;
				else {
					((Player)player).addForce(jumpforce);
					// TODO: change jump mechanic (long press = higher jump)
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		
		player = new Player(this);
		
		// -- test --
		addActor(new Actor(this, 1.0, 0.8));
		addActor(new Actor(this, 2.0, 0.8));
		addActor(new Actor(this, 1.24, 0.7));
		addActor(new Actor(this, 1.9, 0.8));
		// -- /test --
	}
	
	/**
	 * Returns the grid position of the ground.
	 * @return
	 * The grid position of the ground.
	 */
	public double getGround() {
		return ground;
	}
	
	/**
	 * Returns the real position from grid position x.
	 * @param x
	 * The grid position to get the real position from.
	 * @return
	 * The real position calculated from grid position x.
	 */
	public int getCoordX(double x) {
		//return  (int) (-getPosition()*getWidth() + ((this.getWidth() / (ytiles * ((double)getWidth()/(double)getHeight()))) * x));
		double coord = getWidth() * (x / ytiles);
		double scroll = getWidth() * (xposition / ytiles);
		return (int)(coord - scroll);
	}
	/**
	 * Returns the real position from grid position y.
	 * @param y
	 * The grid position to get the real position from.
	 * @return
	 * The real position calculated from grid position y.
	 */
	public int getCoordY(double y) {
		return (int) ((this.getHeight() / ytiles) * y);
	}
	
	/**
	 * Returns the real width from grid width w.
	 * @param w
	 * The grid width to get the real width from.
	 * @return
	 * The real width calculated from grid width w.
	 */
	public int getWidth(double w) {
		//return (int) ((this.getWidth() / (ytiles * ((double)getWidth()/(double)getHeight()))) * w);
		double coord = (double) getWidth() * (w / ytiles);
		return (int) coord;
	}
	/**
	 * Returns the real height from grid height h.
	 * @param h
	 * The grid height to get the real height from.
	 * @return
	 * The real height calculated from grid width w.
	 */
	public int getHeight(double h) {
		return (int) ((this.getHeight() / ytiles) * h);
	}
	
	/**
	 * Returns the x position of the scene (the scroll). 
	 * @return
	 * The x position of the scene (the scroll).
	 */
	public double getPosition() { return xposition; }
	
	/**
	 * Returns the x scroll speed.
	 * @return
	 * The x scroll speed.
	 */
	public double getScrollSpeed() { return xscrolltmp<xscrollsteps?xscrolltmp:xscrollsteps; }
	
	/**
	 * Adds an actor to the scene.
	 * @param a
	 * The actor to be added to the scene.
	 */
	public void addActor(Actor a) {
		//add(a);
		childs.add(a);
	}
	
	public HashSet<Actor> getActors(){
		return childs;
	}

	@Override
	public void paintComponent(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//--paint--
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.red);
		((Graphics2D)g).drawString("Round: "+round, 10, 20);
		((Graphics2D)g).drawString("Speed: "+xscrollspeed, 10, 40);
		
		player.paintComponent(g);
		for(Actor c: childs) {
			c.paintComponent(g);
		}
		//--/paint--
		
		//--update--
		
		if(!paused) {
			if(xposition >= xsize) {
				xposition = 0.0;
				xscrollspeed += xscrollinc;
				player.x = 0.1;
				round += 1;
				// TODO: fix player position reset bug
			}
		
			// smooth fast movement so intersections aren't skipped
			for(xscrolltmp = xscrollspeed; xscrolltmp > 0.000001 && !paused; xscrolltmp -= xscrollsteps) {
				xposition += getScrollSpeed();
			
				// update the actors (movement)
				player.update();
				for(Actor c: childs) {
					c.update();
				}
				// check if the player intersects with an obstacle
				for(Actor c: childs) {
					if(player.intersects(c)) {
						//System.out.println(player+" intersects with "+c);
						paused = true;
					}
				}
				
			}
			xscrolltmp = 0;
			
		}
		//--/update--
	}
}
