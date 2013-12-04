package core;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import actors.Block;
import actors.Hole;
import actors.Triangle;

public class Level {
	private double xsize;
	private Scene scene;
	private InputStream path;
	public final int nr;
	public Level(Scene s,InputStream pathToLevelFile, int nr) {
		this.nr=nr;
		scene=s;
		path=pathToLevelFile;
	}
	
	public void add(Scene s) {
		try{
			Scanner in = new Scanner(path);
			in.useDelimiter(";");
			String tokens,tmp[];
			
			double maxwidth = 1;
			
			// read level speed
			{
				String a = in.nextLine();
				double x = Double.parseDouble(a);
				s.xscrollspeed = x;
			}
			
			while(in.hasNextLine()){
				tokens= in.nextLine();
				tmp = tokens.split(";");
				addObstacles(tmp[0],Double.parseDouble(tmp[1]),Double.parseDouble(tmp[2]));
				if(Double.parseDouble(tmp[1]) + 0.3 > maxwidth) {
					maxwidth = Double.parseDouble(tmp[1]) + 0.3;
				}
				scene.xsize = maxwidth;
			}
		}catch(InputMismatchException e) {
			System.err.println("Error with Rescource File!");
		}
		catch(NoSuchElementException e) {
			e.printStackTrace();
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
		default			:	System.out.println("Error: Not defined type of Obstacle");
							break;
		}
		
		
		
	}
	
	public String getPath(){ //WHATEVER
		System.out.println("DOES NOT WORK");
		return "IS: NOT A PATH" + path.toString();  }
}
