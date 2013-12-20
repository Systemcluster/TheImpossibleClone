package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sound.ResourceLoader;
import core.Actor;
import core.Scene;

@SuppressWarnings("serial")
public class Star extends Actor {
	
	static private BufferedImage bimage = (BufferedImage) ResourceLoader.load("res/star.png");
	private long score = 50;

	public Star(Scene parent, double x, double y) {
		super(parent, x-0.01, y-0.01);
		
		w += 0.02;
		h += 0.02;
	}

	@Override
	public void collide(Player p) {
		if(score != 0) {
			parent.addScore(score);
			score = 0;
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		if(score != 0) {
			if(parent.classic_mode) {
				g2D.setColor(Color.yellow);
				g2D.fillRect(parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h));
			}
			else {
				g2D.setColor(Color.yellow);
				//g2D.drawRect(parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h));
				g2D.drawImage(bimage ,parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h), null);
			}
		}
	}
	
}
