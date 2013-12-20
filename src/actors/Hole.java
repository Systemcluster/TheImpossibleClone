package actors;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import core.Actor;
import core.Scene;

@SuppressWarnings("serial")
public class Hole extends Actor{
	public Hole(Scene parent, double x, double y) {
		super(parent,x,y);
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(Color.green);
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
		//g2D.fillRect(parent.getCoordX(x), parent.getCoordY(y+0.04), parent.getWidth(w), parent.getHeight(h));
		//TODO: implement hole
	}
}