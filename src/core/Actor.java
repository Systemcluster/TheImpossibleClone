package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import actors.Player;

/**
 * Actor class.
 */
public class Actor extends JComponent {
	protected double x = 1;
	public double y = 0.8;
	protected double w = 0.04;
	protected double h = 0.04;
	
	public boolean isGround = false;
	
	protected Scene parent;
	
	public Actor(Scene parent) {
		super();
		this.parent = parent;
	}
	public Actor(Scene parent, double x, double y) {
		this(parent);
		this.x = x;
		this.y = y;
	}
	
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
	
	public void update() {
		
	}
	
	public void collide(Player p) {
		
	}
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
