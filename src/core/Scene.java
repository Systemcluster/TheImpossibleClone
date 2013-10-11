package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashSet;

import javax.swing.JPanel;

/**
 * Scene class. Base class for Scenes to hold and display actors.
 */
public class Scene extends JPanel {

	private double ytiles = 12;
	private HashSet<Actor> childs;
	
	private double xposition = 0;
	private double xscrollspeed = 2;

	public Scene() {
		super();
		
		childs = new HashSet<Actor>();
		
		addActor(new Actor(this));
	}
	
	/**
	 * Returns the real position from grid position x.
	 * @param x
	 * The grid position to get the real position from.
	 * @return
	 * The real position calculated from grid position x.
	 */
	public int getCoordX(double x) {
		return  (int) (-getPosition() + ((this.getWidth() / (ytiles * ((double)getWidth()/(double)getHeight()))) * x));
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
		return (int) ((this.getWidth() / (ytiles * ((double)getWidth()/(double)getHeight()))) * w);
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
	public int getPosition() { return (int) xposition; }
	
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
		//--/update--
		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		for(Actor c: childs) {
			c.paintComponent(g);
		}
	}
}
