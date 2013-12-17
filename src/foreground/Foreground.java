package foreground;

import java.awt.Graphics;
import java.util.HashSet;

import javax.swing.JComponent;

import core.Scene;

public class Foreground extends JComponent {
	
	Scene parent;
	
	private HashSet<ForegroundActor> childs;
	
	public Foreground(Scene parent) {
		this.parent = parent;
	}

	public void update(){
		
	}
	
	public void paintComponent(final Graphics g){
		
	}
}
