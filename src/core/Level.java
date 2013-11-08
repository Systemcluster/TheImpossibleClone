package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import actors.Block;

public class Level {
	private double xsize;
	private Scene scene;
	public Level(Scene s,String pathToLevelFile) {
		scene=s;
		add(s,new File(pathToLevelFile));
		
	}
	
	private void add(Scene s,File f) {
		try{
			Scanner in = new Scanner(f);
			in.useDelimiter(";");
			String tokens,tmp[];
			
			double maxwidth = 1;
			
			while(in.hasNextLine()){
				tokens= in.nextLine();
				tmp = tokens.split(";");
				addObstacles(tmp[0],Double.parseDouble(tmp[1]),Double.parseDouble(tmp[2]));
				if(Double.parseDouble(tmp[1]) + 0.3 > maxwidth) {
					maxwidth = Double.parseDouble(tmp[1]) + 0.3;
				}
				scene.xsize = maxwidth;
			}
		}catch(FileNotFoundException e) {
			System.err.println("Error Loading Rescource File!");
			
		}catch(InputMismatchException e) {
			System.err.println("Error with Rescource File!");
			
		}
	}
	
	private void addObstacles(String type, double x, double y){
		
		switch(type){
		
		case "block"	:	scene.addActor(new Block(scene, x, y));
							break;
		default		:		System.out.println("Error: Not defined type of Obstacle");
							break;
		}
		
		
		
	}
}
