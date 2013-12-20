package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sound.ResourceLoader;
import core.Actor;
import core.Scene;

@SuppressWarnings("serial")
public class Block extends Actor{

	int draw_offset_x = 0;
	int draw_offset_y = 0;
	
	private double overlap_height = 0.005;
	
	static BufferedImage bimage = (BufferedImage) ResourceLoader.load("res/flog.png");
	
	public Block(Scene parent, double x, double y) {
		super(parent,x,y);
		isGround = true;
		h -= overlap_height;
		y += overlap_height;
	}

	@Override
	public void collide(Player p) {
		p.kill();
	}
	
	@Override
	public void surf(Player p) {
		draw_offset_x = 0;
		draw_offset_y = 3;
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2D = (Graphics2D) g;
		
		if(parent.classic_mode) {
			g2D.setColor(Color.red);
			g2D.fillRect(parent.getCoordX(x)+draw_offset_x, parent.getCoordY(y)+draw_offset_y, parent.getWidth(w), parent.getHeight(h));
		}
		else {
			g2D.setColor(Color.red);
			//System.out.println(parent.getCoordX(x)+" "+ parent.getCoordY(y)+" "+ parent.getWidth(w)+" "+ parent.getHeight(h)+" - "+parent.getPosition());
			//g2D.fillRect(parent.getCoordX(x)+draw_offset_x, parent.getCoordY(y)+draw_offset_y, parent.getWidth(w), parent.getHeight(h));
			g2D.drawRect(parent.getCoordX(x)+1, parent.getCoordY(y)+1, parent.getWidth(w)-2, parent.getHeight(h)-2);
			g2D.drawImage(bimage ,parent.getCoordX(x)+draw_offset_x, parent.getCoordY(y-overlap_height)+draw_offset_y, parent.getWidth(w), parent.getHeight(h+overlap_height), null);
		}
		
		
		draw_offset_x = 0;
		draw_offset_y = 0;
	}
}
