package actors;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import core.Actor;
import core.Scene;
public class Triangle extends Actor{
	public Triangle(Scene parent, double x, double y) {
		super(parent,x,y);
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
		g2D.setColor(Color.blue);
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
		g2D.fillPolygon(a,b,4);
	}
}