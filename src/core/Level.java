package core;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import actors.Block;
import actors.Hole;
import actors.Triangle;

public class Level {
	private Scene scene;
	private InputStream path;
	public final int nr;
	
	public Level(Scene s,InputStream pathToLevelFile, int nr) {
		this.nr=nr;
		scene=s;
		path=pathToLevelFile;
	}
	
	public void add(Scene s) {
		
		double maxwidth = 1;
		Scanner in = null;
		String tmp[] = {};
		 
		try{
			in = new Scanner(path);
			in.useDelimiter(";");
			String iss = in.nextLine();
			// read level speed
			s.xscrollspeed = Double.parseDouble(iss);
			
			while(in.hasNextLine()){
				tmp = in.nextLine().split(";");
				addObstacles(tmp[0],Double.parseDouble(tmp[1]) + s.getPosition() + s.getXWidth() ,Double.parseDouble(tmp[2]));
				if(Double.parseDouble(tmp[1]) + s.getPosition() > maxwidth) {
					maxwidth = Double.parseDouble(tmp[1]);
				}
				scene.xsize = maxwidth;
				System.out.println("obstcl at "+ Double.parseDouble(tmp[1]) +" "+ s.getPosition() +" "+ s.getXWidth()); 
			}
			scene.xsize += scene.getXWidth() + scene.getPosition();
			
		}catch(InputMismatchException e) {
			System.err.println("Error with Rescource File!");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}finally{
			if(in != null)
				in.close();
		}
	}
	
	private void addObstacles(String type, double x, double y){
		
		switch(type){
		
		case "block"	:	scene.addActor(new Block(scene, x, y));
							break;
		case "triangle"	:	scene.addActor(new Triangle(scene, x, y));
							break;
		case "hole"		:	scene.addActor(new Hole(scene, x, y));
							break;
		default			:	System.out.println("Error: Not defined type of Obstacle: "+type);
							break;
		}
		
		
		
	}
	
	public String getPath(){ //WHATEVER
		System.out.println("DOES NOT WORK");
		return "IS: NOT A PATH" + path.toString();  }
}
