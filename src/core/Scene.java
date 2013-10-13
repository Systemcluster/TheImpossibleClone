package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
	
	private double xposition = 0;
	private double xscrollspeed = 0.005;
	
	private double xsize = 2.3;

	public Scene() {
		super();
		
		childs = new HashSet<Actor>();
		
		// -- test --
		player = new Player(this);
		addActor(player);
		addActor(new Actor(this, 1.0, 0.8));
		addActor(new Actor(this, 2.0, 0.8));
		addActor(new Actor(this, 1.24, 0.7));
		addActor(new Actor(this, 1.9, 0.8));
		// -- /test --
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
		double coord = (double) getWidth() * (x / ytiles);
		double scroll = (double) getWidth() * (xposition / ytiles);
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
	public double getScrollSpeed() { return xscrollspeed; }
	
	/**
	 * Adds an actor to the scene.
	 * @param a
	 * The actor to be added to the scene.
	 */
	public void addActor(Actor a) {
		//add(a);
		childs.add(a);
	}

	@Override
	public void paintComponent(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//--update--
		xposition += xscrollspeed;
		
		if(xposition > xsize) {
			xposition = 0;
			xscrollspeed += 0.002;
			player.x = 0.1;
		}
		
		for(Actor c: childs) {
			c.update();
		}
		//--/update--
		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		for(Actor c: childs) {
			c.paintComponent(g);
		}
	}
}
