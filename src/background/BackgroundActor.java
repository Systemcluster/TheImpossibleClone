package background;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sound.ResourceLoader;
import core.Actor;
import core.Scene;

/**
 * Holds a background and its function to alter the movement
 * @author Vinzenz Boening
 */
public class BackgroundActor extends Actor{

	private static final long serialVersionUID = -7327728536198764908L;
	
	private double resetX, resetY;
	private double speed = 0.0012;
	private BufferedImage bimage;
	
	public BackgroundActor(Scene parent, double x, double y) {
		super(parent, x, y);
		try{
			bimage = (BufferedImage) ResourceLoader.load("res/bg/treehuge.png");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void update() {
		x+=speed;
	}

	public void setSpeed(double speed){
		this.speed = speed;
	}
	public double getSpeed(){
		return speed;
	}

	public void setRelWidth(double w){
		this.w = w;
	}
	public double getRelWidth(){
		return w;
	}
	
	public void setRelHeight(double h){
		this.h = h;
	}
	public double getRelHeight(){
		return h;
	}
	
	public double getRelX() {
		return x;
	}
	public void setRelX(double x) {
		this.x = x;
	}
	
	public double getRelY() {
		return y;
	}
	public void setRelY(double y) {
		this.y = y;
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2D = (Graphics2D) g;		
		g2D.drawImage(bimage, parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h), null);
	}
	
}
