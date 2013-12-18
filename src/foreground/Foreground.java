package foreground;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JComponent;

import core.Scene;

@SuppressWarnings("serial")
public class Foreground extends JComponent {
	
	private double anti_frequency = 0.5;
	
	Scene parent;
	
	Random rand = new Random();
	
	private HashMap<Integer,HashSet<ForegroundActor>> layers = new HashMap<>();
	private HashMap<Integer, Double> currpos = new HashMap<>();
	
	public Foreground(Scene parent) {
		this.parent = parent;
		rand.setSeed(913247914+System.currentTimeMillis()); // magic number
		layers.put(new Integer(1), new HashSet<ForegroundActor>());
		currpos.put(new Integer(1), new Double(0));
		layers.put(new Integer(2), new HashSet<ForegroundActor>());
		currpos.put(new Integer(2), new Double(0));
	}

	public void update(){
		for(Entry<Integer, HashSet<ForegroundActor>> e: layers.entrySet()) {
			HashSet<ForegroundActor> childs = e.getValue();
			int key = e.getKey();
			ArrayList<ForegroundActor> removees = new ArrayList<>();
			for(ForegroundActor child: childs) {
				if(child.x + child.w < parent.getPosition()) {
					removees.add(child);
				}
				else child.update();
			}
			for(ForegroundActor rem: removees) {
				childs.remove(rem);
			}
			removees.clear();
			
			if(parent.getPosition() > currpos.get(key)) {
				childs.add(new ForegroundActor(parent, 
					parent.getPosition() + parent.getXWidth(), 
					0.65 + (double)key/10, 
					0.40 * (1 + key /4),
					0.40 * (1 + key /4),
					- 0.0015 - (0.0009 * key),
					rand.nextDouble()));
				currpos.put(key, currpos.get(key) + anti_frequency + rand.nextDouble() / (key*2));
			}
			
		}
	}
	
	public void paintComponent(final Graphics g){
		for(Entry<Integer, HashSet<ForegroundActor>> e: layers.entrySet()) {
			HashSet<ForegroundActor> childs = e.getValue();
			for(ForegroundActor child: childs) {
				child.paintComponent(g);
			}
		}
	}
	
}
