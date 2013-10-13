package actors;

import core.Actor;
import core.Scene;

public class Player extends Actor {
	
	public Player(Scene parent) {
		super(parent);
		x = 0.1;
	}
	
	@Override
	public void update() {
		x += parent.getScrollSpeed();
	}
	
}
