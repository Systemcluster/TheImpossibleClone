package foreground;

import java.awt.Graphics;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JComponent;

import core.Scene;

public class Foreground extends JComponent {
	
	Scene parent;
	
	double currpos = 0;
	Random rand = new Random();
	
	private HashSet<ForegroundActor> childs = new HashSet<>();
	
	public Foreground(Scene parent) {
		this.parent = parent;
		rand.setSeed(913247914); // magic number
	}

	public void update(){
		HashSet<ForegroundActor> removees = new HashSet<>();
		for(ForegroundActor child: childs) {
			if(child.x + child.w < parent.getPosition())
				removees.add(child);
			else child.update();
		}
		for(ForegroundActor rem: removees) {
			childs.remove(rem);
		}
		removees.clear();
		
		if(parent.getPosition() > currpos) {
			childs.add(new ForegroundActor(parent, parent.getPosition() + parent.getXWidth(), 0.75));
			currpos += 0.2 + rand.nextDouble() / 2;
		}
	}
	
	public void paintComponent(final Graphics g){
		for(ForegroundActor child: childs) {
			child.paintComponent(g);
		}
	}
	
}
