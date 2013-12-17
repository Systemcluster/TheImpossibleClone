package foreground;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sound.ResourceLoader;
import core.Actor;
import core.Scene;

public class ForegroundActor extends Actor {
	
	private BufferedImage bimage;

	private double speed = -0.0024;
	
	public ForegroundActor(Scene parent, double x, double y) {
		super(parent, x, y);
		w = 0.30;
		h = 0.30;
		bimage = (BufferedImage) ResourceLoader.load("res/fg/busch.png");
	}
	
	public void update(){
		x += speed;
	}
	
	public void paintComponent(final Graphics g){
		Graphics2D g2D = (Graphics2D) g;		
		g2D.drawImage(bimage, parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h), null);
	}

}
