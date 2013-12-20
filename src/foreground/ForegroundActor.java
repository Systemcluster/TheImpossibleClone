package foreground;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sound.ResourceLoader;
import core.Actor;
import core.Scene;


@SuppressWarnings("serial")
public class ForegroundActor extends Actor {
	
	private BufferedImage bimage;

	private double speed;
	
	public ForegroundActor(Scene parent, double x, double y) {
		this(parent, x, y, 0.30, 0.30, -0.0024, 0);
	}
	
	public ForegroundActor(Scene parent, double x, double y, double w, double h, double speed, double magicNumber) {
		super(parent, x, y, w, h);
		this.speed = speed;
		if(magicNumber > 0.92) {
			bimage = (BufferedImage) ResourceLoader.load("res/fg/busch.png");
		}
		else {
			bimage = (BufferedImage) ResourceLoader.load("res/fg/treehugetrans.png");
		}
	}
	
	public void update(){
		x += speed;
	}
	
	public void paintComponent(final Graphics g){
		Graphics2D g2D = (Graphics2D) g;		
		g2D.drawImage(bimage, parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h), null);
	}

}
