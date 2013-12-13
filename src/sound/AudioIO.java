package sound;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioIO {
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
