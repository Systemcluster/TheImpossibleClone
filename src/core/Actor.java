package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * Actor class.
 */
public class Actor extends JComponent {
	protected double x = 1;
	protected double y = 0.8;
	protected double w = 0.05;
	protected double h = 0.05;
	
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
	
	public void update() {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(Color.black);
		System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
		g2D.fillRect(parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h));
		
		
	}
}
