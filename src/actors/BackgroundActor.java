package actors;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sound.ResourceLoader;
import core.Actor;
import core.Scene;

public class BackgroundActor extends Actor{
	private double resetX, resetY;
	private double speed = 0.0012;
	private int layer;
	private BufferedImage bimage;
	
	public BackgroundActor(Scene parent, int layer, double x) {
		super(parent);
		this.parent = parent;
		this.layer = layer;
		this.x = x;
		try{
			bimage = (BufferedImage) ResourceLoader.load("res/bg/treehuge.png");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//The higher the layer, the smaller the model and the slower the speed
		w = 0.1 * (4 - layer);
		h = 0.12 * (4 - layer);
		this.y = 0.9 - h - (0.08 * layer);
		//this.x = x;
		
		speed += 0.0005 * (layer);
		
		resetX = this.x;
		resetY = this.y;
		
	}
	
	public void update() {
		x+=speed;
		System.out.println("BI - Actor x :" + x + "; Scene x" + parent.getPosition());
		System.out.println(parent.getCoordX(x));
		if(x+h < parent.getPosition()){
			x = parent.getPosition() + 1;
		}
	}
	
	public double getResetX(){
		return resetX;
	}
	
	public double getResetY(){
		return resetY;
	}
	
	public void setWidth(double w){
		this.w = w;
	}
	
	public void setHeigth(double h){
		this.h = h;
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2D = (Graphics2D) g;		
		g2D.drawImage(bimage, parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h), null);
	}
	
}
