package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * Actor class.
 */
public class Actor extends JComponent {
	private double x = 14;
	private double y = 4;
	private double w = 3;
	private double h = 3;
	
	Scene parent;
	
	public Actor(Scene parent) {
		super();
		
		this.parent = parent;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		// x += 1.0;
		
		g2D.setColor(Color.black);
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h));
		g2D.fillRect(parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h));
		
		
	}
}
