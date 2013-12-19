package core;

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

import sound.AudioClip;
import sound.ResourceLoader;
import actors.Block;
import actors.Player;
import actors.Triangle;
import background.Background;
import foreground.Foreground;
import global.GlobalSettings;

/**
 * Scene class. -/-Base class for Scenes to hold and display actors.-/-
 * update 1: Class to represent the main panel.
 */
@SuppressWarnings("serial")
public class Scene extends State {

	
	
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
	
	private AudioClip backgroundmusic = (AudioClip) ResourceLoader.load("res/sound/background01.wav");
	
	
	public Scene(JPanel parent, GlobalSettings settings) {
		super(parent, settings);
		
		this.setFocusable(true);
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_P: {
					// pause/unpause the game
					if(!stopped)
						stopped = true;
					else stopped = false;
				}break;
				case KeyEvent.VK_C: {
					// toggle classic mode
					if(!classic_mode)
						classic_mode = true;
					else classic_mode = false;
				}break;
				case KeyEvent.VK_R: {
					// reset
					resetGame();
				}break;
				case KeyEvent.VK_M: {
					// music mute
					if(backgroundmusic.isRunning())
						backgroundmusic.stop();
					else backgroundmusic.loop();
				}break;
				default: {
					// jump
					isSpacePressed = true;
					if(paused||stopped) {
					}
					else {
						((Player)player).jump();
					}
				}break;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				isSpacePressed = false;
			}
		});
		
		initGame();
		
		bg = new Background(this);
		fg = new Foreground(this);
		
		backgroundmusic.loop();
	}
	
	/**
	 * Initialize the game.
	 */
	public void initGame() {
		childs = new HashSet<Actor>();
		setScore(0);
		lloader = new LevelLoader(this, "res/levels/");
		lloader.start();
		player = new Player(this);
		xposition = 0;
		paused = false;
	}
	/**
	 * Reset the game.
	 */
	public void resetGame() {
		// method 1?
		//((StateManager)parent).replaceState(new Scene(parent, settings));
		// method 2
		initGame(); // TODO: Fix LevelLoader reset
	}
	
	/**
	 * Add to the score.
	 * @param add
	 * The value to be added to the score.
	 */
	public void addScore(long add) {
		score += add * scoredivisor;
		((AudioClip) ResourceLoader.load("res/coin.wav")).start();
	}
	/**
	 * Returns the score.
	 * @return
	 * The current score.
	 */
	public double getScore() {
		return new Double(score) / scoredivisor;
	}
	/**
	 * Sets the score.
	 * @param score
	 * The current score.
	 */
	public void setScore(long score) {
		this.score = score * scoredivisor;
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
		double coord = getWidth() * (x / getXWidth());
		double scroll = getWidth() * (xposition / getXWidth());
		return (int)(coord - scroll + 0.5);
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
	
	/**
	 * Return all actors.
	 * @return
	 * All current actors.
	 */
	public HashSet<Actor> getActors(){
		return childs;
	}

	/**
	 * Returns if space (jump) is pressed.
	 * @return
	 * If space (jump) is pressed.
	 */
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
	
	/**
	 * Returns if game is paused/over.
	 * @return
	 * If game is paused/over.
	 */
	public boolean getPaused() {
		return paused;
	}
	
	//TODO: Fix generator (+ add explanation?)
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
		g.setColor(new Color(180, 220, 250));
		g.fillRect(0, 0, getWidth(), getHeight());
		//--/clear bg--
		
		//--update--
		if(!paused && !stopped) {
			
			score += 1;
			
			// Load next level when scrolled past the current one.
			if(xposition + getXWidth() >= xsize + 0.1) {
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
				((Graphics2D)g).drawString("Score:   "+getScore(), 10, 60);
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
