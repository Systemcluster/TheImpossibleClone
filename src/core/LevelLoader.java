package core;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import sound.ResourceLoader;
import states.Scene;

public class LevelLoader {
	private static HashMap<Integer,String> loadedLevels = null;
	
	private Scene scene;
	private String pathToLevel;
	private int current;
	
	public LevelLoader(Scene scene, String pathToLevelFolder){
		this.scene=scene;
		this.pathToLevel = pathToLevelFolder;
		current = -1;
		if(loadedLevels == null){
			loadedLevels = new HashMap<>();
		}
	}
	
	public void start(){
		InputStream t = null;
		try {
			++current;
			System.out.println("Loading level "+current);
			if(!loadedLevels.containsKey(current)){
				t = (InputStream) ResourceLoader.load(pathToLevel + "level0" + current + ".dat");
				if(t != null){
					loadedLevels.put(current, new Level(scene, t).add());
				}
				else{
					System.out.println("Starting random generated level");
					//scene.xsize = 5;
					//scene.generateObstacles();
					LevelGenerator.generateLevel(scene).add();
					//scene.xscrollspeed += 0.001;
				}
			}else{
				t = new ByteArrayInputStream(loadedLevels.get(current).getBytes());
				if(t != null){
					new Level(scene, t).add();
				}
			}
		} 
		catch(IndexOutOfBoundsException e) {
			System.err.println("Could not load level "+current+", error in LevelLoader:start");
			e.printStackTrace();
		}
		finally {
			try{
				if(t != null){
					t.close();
				}
			}
			catch(IOException e){
					
			}
		}
	}
}
