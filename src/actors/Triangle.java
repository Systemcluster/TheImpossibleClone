package actors;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
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
		
	@Override 
	public boolean intersects(Actor r){
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
		Rectangle rec = new Rectangle(parent.getCoordX(r.x),parent.getCoordY(r.y),
				parent.getWidth(r.w),parent.getHeight(r.h));
		
		return( 
				new Polygon(a,b,4).intersects(rec)||
				rec.contains(a[0], b[0])||
				rec.contains(a[1], b[1])||
				rec.contains(a[2], b[2])
		);
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
		
		if(parent.classic_mode) {
			g2D.setColor(Color.green);
			g2D.fillPolygon(a,b,4);
		}
		else {
			g2D.setColor(new Color(100, 200, 80));
			g2D.fillPolygon(a,b,4);
			g2D.drawImage(bimage, parent.getCoordX(x), parent.getCoordY(y), parent.getWidth(w), parent.getHeight(h*1.1), null);
		}
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
	}
}