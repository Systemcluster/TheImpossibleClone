package main;

import global.CollectionGame;
import global.GlobalSettings;
import global.MainMenuInterface;
import global.Score;

import java.awt.image.BufferedImage;

import sound.ResourceLoader;

public class ImpossibleGame implements CollectionGame{

	@Override
	public void runGame(GlobalSettings globalSettings,
			MainMenuInterface mainMenuRef) {
		new Main(globalSettings, mainMenuRef);
		
	}

	@Override
	public Score[] getHighscore() {
		return new Score[]{
			new Score("Peter Lustig", 5), 
			new Score("Rick Astley",10),
			new Score("Roland Kaiser", 9001)
		};
	}

	@Override
	public BufferedImage[] getGamePics() {
		// TODO Auto-generated method stub
		try{
			return new BufferedImage[]{
					(BufferedImage)ResourceLoader.load("res/screenshot.jpg")
			};
		} catch (Exception e){
			return null;
		}
	}
	
}
