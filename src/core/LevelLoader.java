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
		int i = 1;
		for(i = 1;(t = (InputStream) ResourceLoader.load(pathToLevelFolder + "level0" + i + ".dat")) != null;i++){
			System.out.println(t);
			levels.add(new Level(scene, t,maxLevel));
		}
		maxLevel = i;
	}
	
	public void start(){
		//Level is running?
		if(current>-1 && !scene.getPaused()){
			//Load first level
			load(levels.get(0));
		}
		if(scene.getPosition()>scene.xsize) {
			//Level end, destroyCurrent
			System.out.println("### LEVEL "+current+" END  ####");
			destroyCurrent();
			//Load next Level
			if(current<=maxLevel){
				current++;
				load(levels.get(current));
			}
			else {
				System.out.println("KEINE LEVEL MEHR DA!!!");
				//scene.paused=true;
				scene.xsize = 10000;
			}
		}
		
	}
	
	private void load(Level level) {
		level.add(scene);
	}
	private void destroyCurrent() {
		scene.childs.clear();
	}

}
