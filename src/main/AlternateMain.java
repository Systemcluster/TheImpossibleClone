package main;

import global.GlobalSettings;
import global.MainMenuInterface;

/**
 * Alternate main class to start the application without a host.
 */
public class AlternateMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GlobalSettings gs = new GlobalSettings();
		MainMenuInterface mmi = new MainMenuInterface(){
			@Override
			public void returnToMainMenu() {
				// TODO Auto-generated method stub
			}
		};
		ImpossibleGame ig = new ImpossibleGame();
		ig.runGame(gs, mmi);
	}

}
