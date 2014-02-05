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

/**
 * A level.
 * This class generates a level from an input stream.
 * The input stream has to contain a correctly formatted level, see add() for details.
 */
public class Level {
	private Scene scene;
	private InputStream path;
	
	/**
	 * Constructor.
	 * @param s
	 * The scene the level should be loaded in.
	 * @param pathToLevelFile
	 * The input stream containing the level.
	 */
	public Level(Scene s, InputStream pathToLevelFile) {
		this.scene=s;
		path=pathToLevelFile;
		
	}
	
	/**
	 * Adds the level to the scene.
	 * @return
	 * A textual representation of the level.
	 */
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
			scene.setStaticScrollSpeed(Double.parseDouble(iss));
			
			while(in.hasNextLine()){
				/**
				 * Level format (after first line):
				 * string    ;double    ;double
				 * block_type;x_position;y_position;[width;height]
				 */
				String nLine = in.nextLine();
				text += nLine + "\n";    
				tmp = nLine.split(";");
				Actor a = addObstacle(tmp[0], Double.parseDouble(tmp[1]) + scene.getPosition() + scene.getXWidth(), Double.parseDouble(tmp[2]));
				if(Double.parseDouble(tmp[1]) + scene.getPosition() > maxwidth) {
					maxwidth = Double.parseDouble(tmp[1]);
				}
				if(a != null && tmp.length >= 5) {
					a.w = Double.parseDouble(tmp[3]);
					a.h = Double.parseDouble(tmp[4]);
				}
				scene.xsize = maxwidth;
				//System.out.println("obstcl at "+ Double.parseDouble(tmp[1]) +" "+ s.getPosition() +" "+ s.getXWidth()); 
			}
			scene.xsize += scene.getXWidth() + scene.getPosition();
			
		}catch(InputMismatchException e) {
			System.err.println("Error reading level, wrong input: "+e.getLocalizedMessage());
		}catch(NumberFormatException e) {
			System.err.println("Error reading level, wrong number format: "+e.getLocalizedMessage());
		}catch(NoSuchElementException e) {
			System.err.println("Error reading level, no such element: "+e.getLocalizedMessage());
		}finally{
			if(in != null) {
				in.close();
			}
		}
		return text;
	}
	
	/**
	 * Adds an actor to the scene.
	 * @param type
	 * The textual representation of the obstacle type, lowercase.
	 * @param x
	 * The x coordinate of the obstacle.
	 * @param y
	 * The y coordinate of the obstacle.
	 * @return
	 * The actor that has been added.
	 */
	private Actor addObstacle(String type, double x, double y){
		Actor a = null;
		switch(type){
		case "block"	:	a = new Block(scene, x, y);
							break;
		case "triangle"	:	a = new Triangle(scene, x, y);
							break;
		case "hole"		:	a = new Hole(scene, x, y);
							break;
		case "star"		:	a = new Star(scene, x, y);
							break;
		default			:	System.out.println("Error: Not defined type of Obstacle: "+type);
							return null;
		}
		scene.addActor(a);
		return a;
	}
	
}
