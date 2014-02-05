package actors;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sound.ResourceLoader;
import core.Actor;
import core.State;

/**
 * An actor introducing the story.
 * Represents a man on a bike quickly leaving the visible screen.
 */
@SuppressWarnings("serial")
public class Bike extends Actor {
	
	static private BufferedImage imagus = (BufferedImage) ResourceLoader.load("res/bike.png");
	
	public Bike(State parent, double x, double y) {
		super(parent, x, y);
		w+=0.02;h+=0.02;
	}
	@Override
	public void update() {
		x += 0.0045;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		((Graphics2D)g).drawImage(imagus, parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h), null);
	}

}
