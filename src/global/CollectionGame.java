package global;

import java.awt.image.BufferedImage;

public interface CollectionGame {
    public void runGame(GlobalSettings globalSettings, MainMenuInterface mainMenuRef);
    public Score[] getHighscore();
    public BufferedImage[] getGamePics();
}
