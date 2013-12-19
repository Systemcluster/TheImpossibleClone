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
	
	public void open(){
		try{
			if(!clip.isOpen())
				clip.open(ais);
		}catch(Exception e){
			System.err.println("AudioClip: Sound couldn't be opened");
		}
	}
	public void start(){
		open();
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void loop(){
		open();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void loop(int count){
		open();
		clip.loop(count);
	}
	public void close(){
		clip.close();
	}
}
