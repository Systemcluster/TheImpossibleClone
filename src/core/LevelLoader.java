package core;

import java.io.InputStream;
import java.util.ArrayList;

import sound.ResourceLoader;

public class LevelLoader {
	private Scene scene;
	private ArrayList<Level> levels;
	private int maxLevel=0;
	private int current=0;
	public LevelLoader(Scene scene, String pathToLevelFolder){
		this.scene=scene;
		this.levels = new ArrayList<Level>();
		InputStream t;
		while((t = (InputStream) ResourceLoader.load(pathToLevelFolder + "level0" + maxLevel + ".dat")) != null){
			System.out.println("level loaded: "+pathToLevelFolder + "level0" + maxLevel + ".dat");
			levels.add(new Level(scene, t,maxLevel));
			++maxLevel;
		}
		System.out.println("Level found: "+maxLevel);
	}
	
	public void start(){
			//Load next Level
			if(current<maxLevel){
				System.out.println("Loading level "+current);
				try {
					load(levels.get(current));
				} 
				catch(IndexOutOfBoundsException e) {
					System.err.println("Could not load level "+current+", error in LevelLoader:start");
				}
				finally {
					current++;
				}
			}
			else {
				System.out.println("Starting random generated level");
				scene.xsize = 200;
				scene.generateObstacles();
			}
		//}
		
	}
	
	private void load(Level level) {
		level.add(scene);
	}

}
