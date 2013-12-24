package actors;

import java.awt.Graphics;

import states.Scene;
import core.Actor;
import core.State;

@SuppressWarnings("serial")
public class Trigger extends Actor {
	
	private abstract class Callable {
		public abstract void call(Scene parent, Player player);
	}

	private Callable callback;
	
	public Trigger(State parent, double x, double y, double w, double h, Callable callback) {
		super(parent, x, y, w, h);
		this.callback = callback;
	}
	
	@Override
	public void collide(Player p) {
		callback.call((Scene)parent, p);
	}
	
	@Override
	public void paintComponent(Graphics g) {		
	}

}
