package core;

import java.util.HashSet;

import global.GlobalSettings;

import javax.swing.JPanel;

/**
 * Base class for Scenes.
 *
 */
@SuppressWarnings("serial")
public class State extends JPanel {
	
	// screen aspect ratio (calculated by screen w/h) 
	private double ytiles = 1.3;
	
	// current scroll position
	protected double xposition = 0;
	
	// position of the ground (0 to 1)
	private double ground = 0.78;
	
	public HashSet<Actor> childs;
	
	public GlobalSettings settings;
	public StateManager parent;
	
	public static String versionstring = "1.0.2-final";
		
	public State(StateManager parent, GlobalSettings settings) {
		this.parent = parent;
		this.settings = settings;
		
		ytiles = Math.round(
				new Double(settings.getResolution()[0]) / new Double(settings.getResolution()[1]) * 10000.0
				) / 10000.0;
	}
	
	public void resize() {
		//fix aspect ratio
		ytiles = Math.round(
				new Double(parent.getWindow().getWidth()) / new Double(parent.getWindow().getHeight()) * 10000.0
				) / 10000.0;
		System.out.println("Using resolution "+parent.getWindow().getWidth() +"x"+ parent.getWindow().getHeight());
		System.out.println("Set aspect ratio to "+ytiles);
	}
	
	/**
	 * Adds an actor to the scene.
	 * @param a
	 * The actor to be added to the scene.
	 */
	public void addActor(Actor a) {
		childs.add(a);
	}
	
	/**
	 * Return all actors.
	 * @return
	 * All current actors.
	 */
	public HashSet<Actor> getActors(){
		return childs;
	}
	
	/**
	 * Returns the x position of the scene (the scroll). 
	 * @return
	 * The x position of the scene (the scroll).
	 */
	public double getPosition() { return xposition; }
	
	/**
	 * Returns the real x coordinate from screen position x (0-1). 
	 * @param x
	 * The screen position x (0-1).
	 * @return
	 * The real (pixel) position of x.
	 */
	public int getCoordXFixed(double x) {
		double coord = getWidth()  * x + 0.5;
		return (int)(coord);
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
		double coord = getWidth() * (x / getXWidth());
		double scroll = getWidth() * (xposition / getXWidth());
		return (int)(coord - scroll + 0.5);
	}
	
	/**
	 * Returns the real position from grid position y.
	 * @param y
	 * The grid position to get the real position from.
	 * @return
	 * The real position calculated from grid position y.
	 */
	public int getCoordY(double y) {
		return (int) ((this.getHeight() / 1) * y + 0.5);
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
		double coord = (double) getWidth() * (w / ytiles) + 0.5;
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
		return (int) ((this.getHeight() / 1) * h + 0.5);
	}
	
	/**
	 * Returns the width of one screen.
	 * @return
	 * The width of one screen.
	 */
	public double getXWidth() {
		return ytiles;
	}

}
