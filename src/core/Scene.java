package core;

import foreground.Foreground;
import global.GlobalSettings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JPanel;

import actors.Block;
import actors.Player;
import actors.Triangle;
import background.Background;
import background.BackgroundActor;

/**
 * Scene class. -/-Base class for Scenes to hold and display actors.-/-
 * update 1: Class to represent the main panel.
 */
@SuppressWarnings("serial")
public class Scene extends State {

	// screen aspect ratio (calculated by screen w/h) 
	private double ytiles = 1.3;
	
	// classic mode
	public boolean classic_mode = false;
	
	// current scroll position
	private double xposition = 0;
	
	// initial scroll speed
	//0.012
	public double xscrollspeed = 0.010;
	// scroll increment each round
	// 0.0025
	private double xscrollinc = 0.0000;
	// steps to perform scrolling in (for collision)
	private double xscrollsteps = 0.002;
	// value that holds current scroll speed (for stepwise movement)
	private double xscrolltmp = 0;
	
	// with of the scene
	public double xsize = 2.3;
	// position of the ground (0 to 1)
	private double ground = 0.8;
	
	private long score = 0;
	private int scoredivisor = 10;
	
	// level nr.
	private int round = 1;
	// if game is paused (actually if player is dead)
	private boolean paused = false;
	// if the game is actually paused
	private boolean stopped = false;

	private boolean isSpacePressed = false;
	
	
	private double update_sec = 0;
	private int frames = 0;
	private double five_sec = 0;
	private double diff = 0;

	
	public HashSet<Actor> childs;
	private Background bg;
	private Foreground fg;
	private Actor player;
	private LevelLoader lloader = null;
	
	
	public Scene(JPanel parent, GlobalSettings settings) {
		super(parent, settings);
		
		childs = new HashSet<Actor>();
		
		//fix aspect ratio
		ytiles = Math.round(
				new Double(globalSettings.getResolution()[0]) / new Double(globalSettings.getResolution()[1]) * 100
				) / 100.0;
		System.out.println("Using resolution "+globalSettings.getResolution()[0] +"x"+ globalSettings.getResolution()[1]);
		System.out.println("Set aspect ratio to "+ytiles);
		
		
		this.setFocusable(true);
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_P) {
					if(!stopped)
						stopped = true;
					else stopped = false;
					//System.out.println(((Player)player).getTouchedObstacle()!=null);
				}
				else if(e.getKeyCode() == KeyEvent.VK_C) {
					if(!classic_mode)
						classic_mode = true;
					else classic_mode = false;
				}
				else{
					isSpacePressed = true;
					if(paused||stopped) {
					}
					else {
						((Player)player).jump();
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				isSpacePressed = false;
			}
		});
		
		player = new Player(this);
		
		lloader = new LevelLoader(this, "res/levels/");
		lloader.start();
		
		bg = new Background(this);
		bg.addBackgroundActor(new BackgroundActor(this,1.0,1, BackgroundActor.Type.TREE), 1);
		bg.addBackgroundActor(new BackgroundActor(this,1.7,1, BackgroundActor.Type.TREE), 1);
		bg.addBackgroundActor(new BackgroundActor(this,1.1,1, BackgroundActor.Type.TREE), 2);
		bg.addBackgroundActor(new BackgroundActor(this,1.8,1, BackgroundActor.Type.TREE), 2);
		bg.addBackgroundActor(new BackgroundActor(this,1.2,1, BackgroundActor.Type.TREE), 3);
		bg.addBackgroundActor(new BackgroundActor(this,1.9,1, BackgroundActor.Type.TREE), 3);
		bg.addBackgroundActor(new BackgroundActor(this,1.3,1, BackgroundActor.Type.TREE), 4);
		bg.addBackgroundActor(new BackgroundActor(this,2.0,1, BackgroundActor.Type.TREE), 4);
		
		fg = new Foreground(this);
	}
	
	/**
	 * Returns the grid position of the ground.
	 * @return
	 * The grid position of the ground.
	 */
	public double getGround() {
		return ground;
	}
	
	/**
	 * Returns the real position from grid position x.
	 * @param x
	 * The grid position to get the real position from.
	 * @return
	 * The real position calculated from grid position x.
	 */
	public int getCoordX(double x) {
		//return  (int) (-getPosition()*getWidth() + ((this.getWidth() / (ytiles * ((double)getWidth()/(double)getHeight()))) * x));
		double coord = getWidth() * (x / ytiles);
		double scroll = getWidth() * (xposition / ytiles);
		return (int)(coord - scroll + 0.5);
	}
	public int getCoordXFixed(double x) {
		double coord = getWidth() * (x / ytiles) + 0.5;
		return (int)(coord);
	}
	
	/**
	 * Returns the real position from grid position y.
	 * @param y
	 * The grid position to get the real position from.
	 * @return
	 * The real position calculated from grid position y.
	 */
	public int getCoordY(double y) {
		return (int) ((this.getHeight() / 1) * y + 0.5);
	}
	
	/**
	 * Returns the real width from grid width w.
	 * @param w
	 * The grid width to get the real width from.
	 * @return
	 * The real width calculated from grid width w.
	 */
	public int getWidth(double w) {
		//return (int) ((this.getWidth() / (ytiles * ((double)getWidth()/(double)getHeight()))) * w);
		double coord = (double) getWidth() * (w / ytiles) + 0.5;
		return (int) coord;
	}
	/**
	 * Returns the real height from grid height h.
	 * @param h
	 * The grid height to get the real height from.
	 * @return
	 * The real height calculated from grid width w.
	 */
	public int getHeight(double h) {
		return (int) ((this.getHeight() / 1) * h + 0.5);
	}
	
	/**
	 * Returns the width of one screen.
	 * @return
	 * The width of one screen.
	 */
	public double getXWidth() {
		return ytiles;
	}
	
	/**
	 * Returns the x position of the scene (the scroll). 
	 * @return
	 * The x position of the scene (the scroll).
	 */
	public double getPosition() { return xposition; }
	
	/**
	 * Returns the x scroll speed.
	 * @return
	 * The x scroll speed.
	 */
	public double getScrollSpeed() { return xscrolltmp<xscrollsteps?xscrolltmp:xscrollsteps; }
	
	/**
	 * Adds an actor to the scene.
	 * @param a
	 * The actor to be added to the scene.
	 */
	public void addActor(Actor a) {
		childs.add(a);
	}
	
	public HashSet<Actor> getActors(){
		return childs;
	}

	public boolean getSpaceState(){
		return isSpacePressed;
	}
	
	/**
	 * Resets the background, changes xscrollspeed and increments round.
	 * Does not reset the player.
	 */
	public void resetPlayer() {
		xscrollspeed += xscrollinc;
		round += 1;
		bg.reset();
	}
	
	public boolean getPaused() {
		return paused;
	}
	
	//TODO: add explanation?
	public void generateObstacles(){
		boolean level_gen = false;
		
		int obstacleCount =  (int) ((int) Math.random() * ((xsize*5.0) - (xsize*4.0)) + (xsize*4.0));
		Random r = new Random();
		String obstacleName = new String();
		PrintStream levelGen = null;
		if(level_gen) {
			try {
				levelGen = new PrintStream(new File("res/randLevel.dat"));
			} catch (FileNotFoundException e) {
				System.out.println("Error generating level");
			}
		}
		double xValArr[] = new double[obstacleCount];
		double yValArr[] = new double[obstacleCount];
		//System.out.println("Length of x = "+xValArr.length+" "+"obstaclecount = "+obstacleCount);
		
		for(int i = 0;i<obstacleCount;i++){
			double randomX = 0.5 + ((xsize-2.0) - 0.5) * r.nextDouble();
			double randomY = 0.7 + (0.8 - 0.7) * r.nextDouble();
			
			randomX = (double)Math.round(randomX * 100) / 100;
			randomY = (double)Math.round(randomY*10)/10;
			
			xValArr[i] = randomX;
			yValArr[i] = randomY;		
		}
		Arrays.sort(xValArr);
		//System.out.println(Arrays.toString(xValArr));
		for(int j=0;j<obstacleCount;j++){
			if(j>0){ // value of j has to be bigger than 0 bc. of the comparison	
					if((xValArr[j]-xValArr[j-1])<0.8){
						if((xValArr[j]+2.5)>xsize){/* do nothing*/}
						else{
							if(j<xValArr.length/2)
								xValArr[j]=+1.5;
							else
								xValArr[j]=+0.8;
						}
					}	
			}
			xValArr[j]+=getXWidth();
			double temp = Math.random()*2+0.5;
			int randObstacle = (int) temp;
			switch(randObstacle){
				case 0 : {
					obstacleName = "block";
					addActor(new Block(this, xValArr[j] + getPosition(),yValArr[j]));
					
				}
				break;
				case 1 : {
					obstacleName = "triangle"; 
					addActor(new Triangle(this, xValArr[j] + getPosition(),yValArr[j]));
				}
				break;
			}
			if(level_gen) {
				levelGen.println(obstacleName+";"+xValArr[j]+";"+yValArr[j]);
			}
			
		}
	}

	
	@Override
	public void paintComponent(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);	
		
		
		//--clear bg--
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		//--/clear bg--
		
		//--update--
		if(!paused && !stopped) {
			
			score += 1;
			
			if(xposition + getXWidth() >= xsize) {
				lloader.start();
				resetPlayer();
			}
		
			// smooth fast movement so intersections aren't skipped
			// (notice this might cause lag at high movement speed)
			if(!classic_mode) bg.update();
			for(xscrolltmp = xscrollspeed; xscrolltmp > 0.000001 && !paused; xscrolltmp -= xscrollsteps) {
				xposition += getScrollSpeed();
			
				// update the actors (movement)
				player.update();			
				
				for(Actor c: childs) {
					if(c.x > xposition - 1 && c.x < xposition + getXWidth()) { // only update near actors
						c.update();
					}
				}
			}
			xscrolltmp = 0;
			if(!classic_mode) fg.update();
			
			// fixed update
			{
				// update the actors (movement)
				player.fixedUpdate();
				for(Actor c: childs) {
					if(c.x > xposition - 1 && c.x < xposition + getXWidth()) { // only update near actors
						c.fixedUpdate();
					}
					
				}
			}
		}
		//--/update--
		
		if(((Player)player).dead == true) {
			paused = true;
		}
		
		//--paint--
		{
			{
				
				g.setColor(Color.red);
				((Graphics2D)g).drawString("Round: "+round, 10, 20);
				((Graphics2D)g).drawString("Speed:  "+xscrollspeed, 10, 40);
				((Graphics2D)g).drawString("Score:   "+new Double(score) / scoredivisor, 10, 60);
			}
			
			if(!classic_mode) bg.paintComponent(g); // paint background
			
			player.paintComponent(g); // paint player
			{
				ArrayList<Actor> removees = new ArrayList<>();
				for(Actor c: childs) {
					if(c.x + c.w < xposition) {
						removees.add(c);
					}
					else if(c.x > xposition - getXWidth() && c.x < xposition + getXWidth() + c.w) // only paint near actors
						c.paintComponent(g); // paint level
					
					//TODO: FIX JITTER!!!
				}
				for(Actor rem: removees) {
					childs.remove(rem); // remove actors that are out of view (<|)
				}
				removees.clear();
			}
			
			if(!classic_mode) fg.paintComponent(g); // paint foreground
			
			((Graphics2D) g).drawString("0.0.2-indev", getCoordXFixed(0.85*getXWidth()), getCoordY(0.9));
			if(paused) {
				g.setColor(Color.red);
				((Graphics2D) g).drawString("GAME OVER", getCoordXFixed(0.45*getXWidth()), getCoordY(0.48));
			}
			
			
			double ct = System.currentTimeMillis();
			diff += (ct - update_sec);
			update_sec = ct;
			++frames;
			if(frames == 5) {
				five_sec = diff / 5;
				diff = 0;
				frames = 0;
			}
			g.setColor(Color.red);
			((Graphics2D)g).drawString("FPS:      "+(double)((int)((1000/five_sec)*100))/100, 10, 80);
			
		}
		
		//--/paint--
	}
}
