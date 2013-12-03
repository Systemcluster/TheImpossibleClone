package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import core.Actor;
import core.Scene;

public class Block extends Actor{

	int draw_offset_x = 0;
	int draw_offset_y = 0;
	
	public Block(Scene parent, double x, double y) {
		super(parent,x,y);
		isGround = true;
	}

	@Override
	public void collide(Player p) {
		p.kill();
	}
	
	@Override
	public void surf(Player p) {
		draw_offset_x = 3;
		draw_offset_y = 3;
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(Color.black);
		//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
		g2D.fillRect(parent.getCoordX(x)+draw_offset_x, parent.getCoordY(y)+draw_offset_y, parent.getWidth(w), parent.getHeight(h));
		
		
		draw_offset_x = 0;
		draw_offset_y = 0;
	}
}
