package core;

import java.io.InputStream;
import java.util.ArrayList;

import sound.ResourceLoader;

public class LevelLoader {
	private Scene scene;
	private ArrayList<Level> levels;
	private int maxLevel=0;
	private int current=-1;
	public LevelLoader(Scene scene, String pathToLevelFolder){
		this.scene=scene;
		this.levels = new ArrayList<Level>();
		InputStream t;
		int i = 1;
		for(i = 1;(t = (InputStream) ResourceLoader.load(pathToLevelFolder + "level0" + i + ".dat")) != null;i++){
			System.out.println("level loaded: "+pathToLevelFolder + "level0" + i + ".dat");
			levels.add(new Level(scene, t,maxLevel));
		}
		maxLevel = i;
	}
	
	public void start(){
			System.out.println("Level "+current+" end");
			//Load next Level
			if(current<=maxLevel){
				current++;
				try {
					load(levels.get(current));
				} 
				catch(IndexOutOfBoundsException e) {
					System.err.println("IndexOutOfBoundsException as expected in LevelLoader:start, ignoring...");
				}
			}
			else {
				System.out.println("starting random generated level");
				scene.xsize = 200;
				scene.generateObstacles();
			}
		//}
		
	}
	
	private void load(Level level) {
		level.add(scene);
	}

}
