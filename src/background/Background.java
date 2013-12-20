package background;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JComponent;

import core.Scene;

/**
 * Represents the background behind the main scene
 * @author Vinzenz Boening
 */
public class Background extends JComponent{

	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer,HashSet<BackgroundActor>> mLayers;
	private HashMap<Integer,HashSet<BackgroundActor>> mLayers2;
	private Scene p;
	
	private double lYOffset = 0.05;
	private double lSpeedOffset = 0.0005;
	private double lSize = 6;
	
	private int layerUsed = 5;
	private double objectsPerLayer = 2;
	
	private int floorscale = 6;
	
	Random rand = new Random();
	
	private interface Callable{
		public void call(BackgroundActor b);
	}
	
	public Background(Scene p){
		this.p = p;
		mLayers = new HashMap<>();
		mLayers2 = new HashMap<>();
		
		rand.setSeed(726341+System.currentTimeMillis()); // magic number
		
		//floor = new Floor(p);
		
		for(int i = 0; i < layerUsed * objectsPerLayer; ++i) {
			
			for(int j = 0; j <= floorscale; ++j ) {
				BackgroundActor ba = new BackgroundActor(p, 
						(p.getXWidth() / floorscale) * j, 
						(p.getGround() - (lYOffset * (int)(i / objectsPerLayer)) +0.016), 
						BackgroundActor.Type.DIRT);
				ba.w = p.getXWidth() / floorscale + 0.01;
				ba.h = 1.0/floorscale;
				ba.setSpeed(ba.getSpeed() + (lSpeedOffset * ((int)(i / objectsPerLayer))));
				if(!mLayers2.containsKey(i))
					mLayers2.put(i,new HashSet<BackgroundActor>());
				mLayers2.get((int)(i / objectsPerLayer)).add(ba);
			}
			
			addBackgroundActor(new BackgroundActor(p, p.getXWidth() + (p.getXWidth() / objectsPerLayer) * (i % objectsPerLayer) + rand.nextDouble()/2,
					1, BackgroundActor.Type.TREE), (int)(i / objectsPerLayer));
			
		}
	}
	
	/**
	 * Adds a BackgroundActor on a specific layer
	 *  
	 * @param ba BackgroundActor to add 
	 * @param layer Layer where the BackgroundActor sits 
	 */
	public void addBackgroundActor(BackgroundActor ba, int layer){
		
		//Resize and move the BackgroundActor according to its layer
		ba.w = (0.1 * (lSize - (layer)));
		ba.h = (0.12 * (lSize - (layer)));
		ba.y = (p.getGround() + 0.1 - ba.h - (lYOffset * layer));
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
				if(b.x + b.w < p.getPosition()){
					b.x = (p.getPosition() + p.getXWidth())+(rand.nextDouble()/4);
				}
			}
		});
		iterate2(new Callable(){
			public void call(BackgroundActor b){
				b.update();
				if(b.x + b.w < p.getPosition()){
					b.x = (p.getPosition() + p.getXWidth());
				}
			}
		});
	}
	
	/**
	 * 
	 * Calls paintComponent(Graphics g) on every BackgroundActor held by the Background 
	 */
	public void paintComponent(final Graphics g){
		/*iterate2(new Callable(){
			public void call(BackgroundActor b){
				b.paintComponent(g);
			}
		});
		iterate(new Callable(){
			public void call(BackgroundActor b){
				b.paintComponent(g);
			}
		});*/
		Callable cal = new Callable() {
			public void call(BackgroundActor b) {
				b.paintComponent(g);
			}
		};
		iterateBoth(cal,cal);
	}
	
	/**
	 * 
	 * Resets the background so it will run smooth while the level gets changed
	 */
	public void reset(){
		iterate(new Callable(){
			public void call(BackgroundActor b){
				// new level loaded
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
	private void iterate2(Callable c){
		for(int i = mLayers.size(); i >= 0; i--){
			if(mLayers.containsKey(i)){
				for(BackgroundActor b : mLayers2.get(i)){
					c.call(b);	
				}
			}
		} 
	}
	private void iterateBoth(Callable c1, Callable c2){
		for(int i = mLayers.size() > mLayers2.size() ? mLayers.size() : mLayers2.size() ; i >= 0; i--){
			if(mLayers2.containsKey(i)){
				for(BackgroundActor b : mLayers2.get(i)){
					c1.call(b);
				}
			}
			if(mLayers.containsKey(i)){
				for(BackgroundActor b : mLayers.get(i)){
					c2.call(b);
				}
			}
		}
	}
}
