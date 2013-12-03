package actors;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import sound.ResourceLoader;
import core.Actor;
import core.Scene;
public class Triangle extends Actor{
	public Triangle(Scene parent, double x, double y) {
		super(parent,x,y-0.015);
		w = 0.06;
		h = 0.06;
	}
	
	@Override
	public void collide(Player p) {
		p.kill();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
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
		g2D.setColor(Color.green);
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
		//g2D.drawPolygon(a,b,4);
		g2D.drawRect(parent.getCoordX(x)+1, parent.getCoordY(y)+1, parent.getWidth(w)-2, parent.getHeight(h)-2);
		g2D.drawImage((BufferedImage) ResourceLoader.load("res/bush.png"),parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h), null);
	}
	
	//TODO: collision
}