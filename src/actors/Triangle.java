package actors;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sound.ResourceLoader;
import core.Actor;
import core.Scene;

@SuppressWarnings("serial")
public class Triangle extends Actor{
	
	static BufferedImage bimage = (BufferedImage) ResourceLoader.load("res/bush.png");
	
	public Triangle(Scene parent, double x, double y) {
		super(parent,x,y-0.015);
		w = 0.06;
		h = 0.06;
	}
	
	// TODO: add triangular collision calculation
	
	@Override
	public void collide(Player p) {
		p.kill();
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		if(parent.classic_mode) {
			g2D.setColor(Color.green);
			int[] a = {
					parent.getCoordX(x),
					parent.getCoordX(x)+parent.getWidth(w),
					parent.getCoordX(x)+parent.getWidth(w/2),
					parent.getCoordX(x)
					};
			int[] b = {
					parent.getCoordY(y)+parent.getHeight(h),
					parent.getCoordY(y)+parent.getHeight(h),
					parent.getCoordY(y),
					parent.getCoordY(y)+parent.getHeight(h)
					};
			g2D.fillPolygon(a,b,4);
		}
		else {
			g2D.setColor(Color.green);
			g2D.drawRect(parent.getCoordX(x)+1, parent.getCoordY(y)+1, parent.getWidth(w)-2, parent.getHeight(h)-2);
			g2D.drawImage(bimage, parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h), null);
		}
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
	}
}