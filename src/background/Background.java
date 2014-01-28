package background;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JComponent;

import core.State;

/**
 * Represents the background behind the main scene.
 */
public class Background extends JComponent{

	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer,HashSet<BackgroundActor>> mLayers;
	private HashMap<Integer,HashSet<BackgroundActor>> mLayers2;
	private State p;
	
	private double lYOffset = 0.05;
	private double lSpeedOffset = 0.00045;
	private double lSize = 6;
	
	private double floorpos = 0.030;
	
	private int layerUsed = 5;
	private double objectsPerLayer = 2;
	
	private int floorscale = 6;
	
	private double lastWidth = 0;
	
	Random rand = new Random();
	
	private interface Callable{
		public void call(BackgroundActor b);
	}
	
	public Background(State p){
		this.p = p;
		mLayers = new HashMap<>();
		mLayers2 = new HashMap<>();
		
		rand.setSeed(726341+System.currentTimeMillis()); // magic number
		
		for(int i = 0; i < layerUsed * objectsPerLayer; ++i) {
			addBackgroundActor(new BackgroundActor(p, p.getXWidth() + (p.getXWidth() / objectsPerLayer) * (i % objectsPerLayer) + rand.nextDouble()/2,
					1, BackgroundActor.Type.TREE), (int)(i / objectsPerLayer));
		}
	}
	
	private void generateFloor() {
		mLayers2.clear();
		for(int i = 0; i < layerUsed * objectsPerLayer; ++i) {
			for(int j = 0; j <= floorscale; ++j ) {
				addFloor(i,j);
			}
			addFloor(i,floorscale+1);
			addFloor(i,floorscale+2);
		}
	}
	
	private void addFloor(int i, int j) {
		BackgroundActor ba = new BackgroundActor(p, 
				p.getPosition() + (p.getXWidth() / floorscale) * j, 
				(p.getGround() - (lYOffset * (int)(i / objectsPerLayer)) + floorpos), 
				BackgroundActor.Type.DIRT);
		ba.w = p.getXWidth() / floorscale + 0.02;
		ba.h = 1.0/floorscale;
		ba.setSpeed(ba.getSpeed() + (lSpeedOffset * ((int)(i / objectsPerLayer))));
		if(!mLayers2.containsKey(i))
			mLayers2.put(i,new HashSet<BackgroundActor>());
		mLayers2.get((int)(i / objectsPerLayer)).add(ba);
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
	
	private Callable c1 = new Callable(){
		public void call(BackgroundActor b){
			b.update();
			if(b.x + b.w <= p.getPosition()){
				b.x = (p.getPosition() + p.getXWidth())+(rand.nextDouble()/4);
			}
		}
	};
	private Callable c2 = new Callable(){
		public void call(BackgroundActor b){
			b.update();
			if(b.x + b.w <= p.getPosition()){
				b.x += (p.getXWidth()+b.w);
			}
		}
	};
	
	/**
	 * 
	 * Calls update() on every BackgroundActor held by the Background 
	 */
	public void update(){
		iterateBoth(c1, c2);
	}
	
	/**
	 * 
	 * Calls paintComponent(Graphics g) on every BackgroundActor held by the Background 
	 */
	public void paintComponent(final Graphics g){
		// fix floor on window resize
		if (new Double(p.getWidth()).compareTo(lastWidth) != 0) {
			System.out.println("generating new floor");
			generateFloor();
			lastWidth = p.getWidth();
		}
		
		Callable cal = new Callable() {
			public void call(BackgroundActor b) {
				b.paintComponent(g);
			}
		};
		iterateBoth(cal,cal);
	}
	
	/**
	 * Provides a method to call a function for every BackgroundActor on this Background
	 * @param c1,c2
	 * A function wrapped in the Callable interface
	 */
	private void iterateBoth(Callable c1, Callable c2){
		for(int i = mLayers.size() > mLayers2.size() ? mLayers.size() : mLayers2.size() ; i >= 0; i--){
			if(mLayers2.containsKey(i)){
				for(BackgroundActor b : mLayers2.get(i)){
					c2.call(b);
				}
			}
			if(mLayers.containsKey(i)){
				for(BackgroundActor b : mLayers.get(i)){
					c1.call(b);
				}
			}
		}
	}
}
