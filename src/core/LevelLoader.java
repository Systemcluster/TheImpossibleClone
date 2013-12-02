package core;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class LevelLoader {
	private Scene scene;
	private ArrayList<Level> levels;
	private int maxLevel=0;
	private int current=0;
	public LevelLoader(Scene scene, File pathToLevelFolder){
		this.scene=scene;
		this.levels = new ArrayList<Level>();
		if(pathToLevelFolder.isDirectory())
		{
			for(File f : pathToLevelFolder.listFiles()){
				System.out.println(scene + "#" + f.getAbsolutePath());
				levels.add(new Level(scene, f,maxLevel));
				maxLevel++;
			}
		}else {
			System.err.println("Error Loading Levels from Level-Folder!");
		}
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
