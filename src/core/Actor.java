package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import actors.Player;

/**
 * Actor class. Base class for all screen actors like obstacles or the player.
 */
public class Actor extends JComponent {
	
	private static final long serialVersionUID = -8778184236273407234L;
	
	public double x = 1;
	public double y = 1;
	public double w = 0.04;
	public double h = 0.04;
	
	public boolean isGround = false;
	
	protected State parent;
	
	public Actor(State parent) {
		super();
		this.parent = parent;
	}
	
	public Actor(State parent, double x, double y, double w, double h) {
		this(parent, x, y);
		this.w = w;
		this.h = h;
	}
	public Actor(State parent, double x, double y) {
		this(parent);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns if this actor intersects with another.
	 * Intersection test is performed rectangular.
	 * @param r 
	 * The actor to test the collision with.
	 * @return
	 * If the two actors intersect.
	 */
	public boolean intersects(Actor r) {
		double tw = this.w;
		double th = this.h;
		double rw = r.w;
		double rh = r.h;
	    if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
	        return false;
	    }
	    double tx = this.x;
	    double ty = this.y;
	    double rx = r.x;
	    double ry = r.y;
	    rw += rx;
	    rh += ry;
	    tw += tx;
	    th += ty;
	    //      overflow || intersect
	    return ((rw < rx || rw > tx) &&
	            (rh < ry || rh > ty) &&
	            (tw < tx || tw > rx) &&
	            (th < ty || th > ry));
	}
	
	/**
	 * Fixed update once per frame.
	 */
	public void fixedUpdate() {
		
	}
	
	/**
	 * Multiple updates per frame dependent on speed.
	 */
	public void update() {
		
	}
	
	/**
	 * This method is called if a collision with a player happened.
	 * @param p
	 * The player that intersects.
	 */
	public void collide(Player p) {
		
	}
	/**
	 * This method is called if a player surfs the actor.
	 * @param p
	 * The player that surfs.
	 */
	public void surf(Player p) {
		 
	}
	
	@Override
	public String toString() {
		return x+" "+y+" "+w+" "+h+" ";
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(Color.black);
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
		g2D.fillRect(parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h));
		
		
	}
}
