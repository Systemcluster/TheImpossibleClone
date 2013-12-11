package global;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class GlobalSettings {
    // Attributes
    private static final File GLOBALSETTINGSFILE = new File(
            "src/global/globalsettings.ini");
    
    private boolean musicMuted;
    private boolean soundeffectsMuted;
    private int[] resolution;
    
    // Constructors
    public GlobalSettings() {
        resolution = new int[2];
        readGlobalSettingsFile();
    }
    
    // Methods
    public boolean getMusicMuted() {
        return musicMuted;
    }

    public void setMusicMuted(boolean musicMuted) {
        this.musicMuted = musicMuted;
    }

    public boolean getSoundeffectsMuted() {
        return soundeffectsMuted;
    }

    public void setSoundeffectsMuted(boolean soundeffectsMuted) {
        this.soundeffectsMuted = soundeffectsMuted;
    }

    public int[] getResolution() {
        return resolution;
    }

    // perhaps check for allowed resolutions (4:3 format or "black bars"!!!!!!)
    public void setResolution(int x, int y) {
        resolution[0] = x;
        resolution[1] = y;
    }
    
    public void resetSettings() {
        musicMuted = false;
        soundeffectsMuted = false;
        resolution[0] = 800;
        resolution[1] = 600;
    }

    private void readGlobalSettingsFile() {

        if (!GLOBALSETTINGSFILE.exists()) {
            System.out.println("Globalsettings file doesn't exist.");
            System.out.println("New globalsettings file will be created.");

            // create new globalsettings file
            resetSettings();
            save();
            System.out.println("New globalsettings file created.");
        } else {
            try {
                Scanner input = new Scanner(GLOBALSETTINGSFILE);

                // read in!
                musicMuted = input.nextBoolean();
                soundeffectsMuted = input.nextBoolean();
                resolution[0] = input.nextInt();
                resolution[1] = input.nextInt();
                
                input.close();

            } catch (FileNotFoundException e) {
                System.out.println("Error: No permission to read the globalsettings file!");
                e.printStackTrace();
            }
        }
    }
    
    public void save() {
     // save GlobalSettings in file
        try {
            PrintStream output = new PrintStream(GLOBALSETTINGSFILE);
            output.println(musicMuted);
            output.println(soundeffectsMuted);
            output.println(resolution[0]);
            output.println(resolution[1]);
            output.close();
        } catch (FileNotFoundException e) {
            System.out
                    .println("Error: No permission to override the globalsettings file!");
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString(){
        return "(" + musicMuted + ", " + soundeffectsMuted + ", " + resolution[0] + ", " + resolution[1] + ")";
    }
}
