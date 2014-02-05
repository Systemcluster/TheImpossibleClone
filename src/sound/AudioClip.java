package sound;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * Represents a AudioClip 
 * Could've extended DirectClip because it basically is one, but its not visible.
 * 
 * @author Vinzenz
 */
public class AudioClip{
	private Clip clip;
	private AudioInputStream ais;
	
	public AudioClip(BufferedInputStream is) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		clip = AudioSystem.getClip();
		ais = AudioSystem.getAudioInputStream(is); 				
	}
	
	/**
	 * Opens the sound to operate with it
	 */
	public void open(){
		try{
			if(!clip.isOpen())
				clip.open(ais);
		}catch(Exception e){
			System.err.println("AudioClip: Sound couldn't be opened");
		}
	}
	
	/**
	 * Plays the sound immediately once
	 */
	public void start(){
		open();
		clip.setFramePosition(0);
		clip.start();
	}
	
	/**
	 * Plays the sound immediately in an infinite loop
	 */
	public void loop(){
		open();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * Plays the sound multiple times
	 * 
	 * @param count The count how often the sound is played
	 */
	public void loop(int count){
		open();
		clip.loop(count);
	}
	
	/**
	 * Close the sound
	 */
	public void close(){
		clip.close();
	}
	
	/**
	 * Returns whether the sound is running or not
	 */
	public boolean isRunning() {
		return clip.isRunning();
	}
	
	/**
	 * Stops the playback
	 */
	public void stop() {
		clip.stop();
	}
}
