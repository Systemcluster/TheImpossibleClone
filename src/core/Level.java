package core;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import states.Scene;
import actors.Block;
import actors.Hole;
import actors.Star;
import actors.Triangle;

public class Level {
	private Scene scene;
	private InputStream path;
	public final int nr;
	
	public Level(Scene s,InputStream pathToLevelFile, int nr) {
		this.nr=nr;
		this.scene=s;
		path=pathToLevelFile;
		
	}
	
	public String add() {
		
		double maxwidth = 1;
		Scanner in = null;
		String text = "";
		String tmp[] = {};
		 
		try{
			in = new Scanner(path);
			in.reset();    
			in.useDelimiter(";");
			String iss = in.nextLine();
			text += iss + "\n";
			/**
			 * read level speed
			 * (first line, double)
			 */
			scene.xscrollspeed = Double.parseDouble(iss);
			
			while(in.hasNextLine()){
				/**
				 * Level format (after first line):
				 * string    ;double    ;double
				 * block_type;x_position;y_position
				 */
				String nLine = in.nextLine();
				text += nLine + "\n";    
				tmp = nLine.split(";");
				addObstacles(tmp[0],Double.parseDouble(tmp[1]) + scene.getPosition() + scene.getXWidth() ,Double.parseDouble(tmp[2]));
				if(Double.parseDouble(tmp[1]) + scene.getPosition() > maxwidth) {
					maxwidth = Double.parseDouble(tmp[1]);
				}
				scene.xsize = maxwidth;
				//System.out.println("obstcl at "+ Double.parseDouble(tmp[1]) +" "+ s.getPosition() +" "+ s.getXWidth()); 
			}
			scene.xsize += scene.getXWidth() + scene.getPosition();
			
		}catch(InputMismatchException e) {
			System.err.println("Error with Rescource File!");
		}catch(NoSuchElementException e) {
			System.err.println("LEVEL :" + nr + "-"+ e.getMessage());
		}finally{
			if(in != null) {
				in.close();
			}
		}
		return text;
	}
	
	private void addObstacles(String type, double x, double y){

		switch(type){
		
		case "block"	:	scene.addActor(new Block(scene, x, y));
							break;
		case "triangle"	:	scene.addActor(new Triangle(scene, x, y));
							break;
		case "hole"		:	scene.addActor(new Hole(scene, x, y));
							break;
		case "star"		:	scene.addActor(new Star(scene, x, y));
							break;
		default			:	System.out.println("Error: Not defined type of Obstacle: "+type);
							break;
		}
		
		
		
	}
	
}
