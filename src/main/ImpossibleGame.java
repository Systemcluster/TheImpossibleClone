package main;

import global.CollectionGame;
import global.GlobalSettings;
import global.MainMenuInterface;
import global.Score;

import java.awt.image.BufferedImage;

import sound.ResourceLoader;
import core.ScoreManager;

/**
 * Class that provides the interface implementation for the host. 
 */
public class ImpossibleGame implements CollectionGame{

	/**
	 * This runs the game.
	 */
	@Override
	public void runGame(GlobalSettings globalSettings,
			MainMenuInterface mainMenuRef) {
		new Main(globalSettings, mainMenuRef);
		
	}

	/**
	 * Returns the highscore list as an array.
	 */
	@Override
	public Score[] getHighscore() {
		/*return new Score[]{
			new Score("Peter Lustig", 5), 
			new Score("Rick Astley",10),
			new Score("Roland Kaiser", 9001)
		};*/
		return ScoreManager.getScore();
	}

	/**
	 * Returns game images.
	 */
	@Override
	public BufferedImage[] getGamePics() {
			return new BufferedImage[] {
					(BufferedImage)ResourceLoader.load("res/screenshot.png")
					};
			
	}
	
}
