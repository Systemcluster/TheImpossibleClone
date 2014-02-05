package sound;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Provides a method to read an audiofile
 */
public class AudioIO {
	/**
	 * Read an audiofile
	 * 
	 * @param in File to read
	 */
	public static AudioClip read (BufferedInputStream in){
		try{
			return new AudioClip(in);
		} catch (UnsupportedAudioFileException uae){
			return null;
		} catch (IOException ioe) {
			return null;
		} catch (LineUnavailableException e) {
			return null;
		}
	}
}
