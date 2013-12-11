package background;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JComponent;

import core.Scene;

/**
 * Represents the background behind the main scene
 * @author Vinzenz Boening
 */
public class Background extends JComponent{

	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer,HashSet<BackgroundActor>> mLayers;
	private Scene p;
	
	private double lYOffset = 0.05;
	private double lSpeedOffset = 0.0005;
	private double lSize = 6;
	
	private interface Callable{
		public void call(BackgroundActor b);
	}
	
	public Background(Scene p){
		this.p = p;
		mLayers = new HashMap<>();
	}
	
	/**
	 * Adds a BackgroundActor on a specific layer
	 *  
	 * @param ba BackgroundActor to add 
	 * @param layer Layer where the BackgroundActor sits 
	 */
	public void addBackgroundActor(BackgroundActor ba, int layer){
		
		//Resize and move the BackgroundActor according to its layer
		ba.setRelWidth(0.1 * (lSize - (layer)));
		ba.setRelHeight(0.12 * (lSize - (layer)));
		ba.setRelY(p.getGround() + 0.1 - ba.getRelHeight() - (lYOffset * layer));
		ba.setSpeed(ba.getSpeed() + (lSpeedOffset * (layer)));
		//--
		
		if(!mLayers.containsKey(layer))
			mLayers.put(layer,new HashSet<BackgroundActor>());
		mLayers.get(layer).add(ba);
	}
	
	/**
	 * 
	 * Calls update() on every BackgroundActor held by the Background 
	 */
	public void update(){
		iterate(new Callable(){
			public void call(BackgroundActor b){
				b.update();
				if(b.getRelX() + b.getRelWidth() < p.getPosition()){
					b.setRelX(p.getPosition() + 1);
				}
			}
		});
	}
	
	/**
	 * 
	 * Calls paintComponent(Graphics g) on every BackgroundActor held by the Background 
	 */
	public void paintComponent(final Graphics g){
		iterate(new Callable(){
			public void call(BackgroundActor b){
				b.paintComponent(g);
			}
		});
	}
	
	/**
	 * 
	 * Resets the background so it will run smooth while the level gets changed
	 */
	public void reset(){
		iterate(new Callable(){
			public void call(BackgroundActor b){
				//Do someting when next level is loaded
				System.out.print("");
			}
		});
	}
	
	/**
	 * Provides a method to call a function for every BackgroundActor on this Background
	 * @param c A function wrapped in the Callableinterface
	 */
	private void iterate(Callable c){
		for(int i = mLayers.size(); i >= 0; i--){
			if(mLayers.containsKey(i)){
				for(BackgroundActor b : mLayers.get(i)){
					c.call(b);	
				}
			}
		} 
	}
}
