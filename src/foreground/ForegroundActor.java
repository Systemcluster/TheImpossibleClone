package foreground;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sound.ResourceLoader;
import core.Actor;
import core.State;


@SuppressWarnings("serial")
public class ForegroundActor extends Actor {
	
	private static BufferedImage bimage_default = (BufferedImage) ResourceLoader.load("res/fg/treehugetrans.png");
	private static BufferedImage bimage_secret = (BufferedImage) ResourceLoader.load("res/fg/busch.png");
	
	private BufferedImage bimage;

	private double speed;
	
	
	public ForegroundActor(State parent, double x, double y) {
		this(parent, x, y, 0.30, 0.30, -0.0024, 0);
	}
	
	public ForegroundActor(State parent, double x, double y, double w, double h, double speed, double magicNumber) {
		this(parent, x, y, w, h, speed, magicNumber, 0.0);
	}
	
	public ForegroundActor(State parent, double x, double y, double w, double h, double speed, double magicNumber, double secretProbability) {
		super(parent, x, y, w, h);
		this.speed = speed;
		if(magicNumber < secretProbability) {
			bimage = bimage_secret;
		}
		else {
			bimage = bimage_default;
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
