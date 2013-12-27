package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import sound.ResourceLoader;
import states.Scene;
import core.Actor;

@SuppressWarnings("serial")
public class Star extends Actor {
	
	private ArrayList<BufferedImage> runnimation = new ArrayList<>();
	
	private long score = 50;
	
	private double rotate = 0;
	private double rotationspeed = 0.22;

	public Star(Scene parent, double x, double y) {
		super(parent, x-0.01, y-0.01);
		
		w += 0.02;
		h += 0.02;
		
		runnimation.add((BufferedImage) ResourceLoader.load("res/star1.png"));
		runnimation.add((BufferedImage) ResourceLoader.load("res/star2.png"));
		runnimation.add((BufferedImage) ResourceLoader.load("res/star3.png"));
		runnimation.add((BufferedImage) ResourceLoader.load("res/star4.png"));
		runnimation.add((BufferedImage) ResourceLoader.load("res/star5.png"));
	 	runnimation.add((BufferedImage) ResourceLoader.load("res/star6.png"));
	 	
	}

	@Override
	public void collide(Player p) {
		if(score != 0) {
			((Scene)parent).addScore(score);
			score = 0;
		}
	}
	
	@Override
	public void fixedUpdate() {
		rotate += rotationspeed;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(score != 0) {
			Graphics2D g2D = (Graphics2D) g;
			g2D.setColor(Color.yellow);
			if(((Scene)parent).classic_mode) {
				g2D.fillRect(parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h));
			}
			else {
				g2D.drawImage(runnimation.get((int) (rotate%runnimation.size())) ,parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h), null);
			}
		}
	}

}
