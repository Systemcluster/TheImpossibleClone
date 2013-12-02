package sound;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioClip {
	private Clip clip;
	private AudioInputStream ais;
	
	public AudioClip(InputStream is) throws UnsupportedAudioFileException{
		try{
			clip = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(is); 
		}catch(IOException | LineUnavailableException e){
			System.err.println("AudioClip: Init failed");
		}
	}
	
	public void open(){
		try{
			if(!clip.isOpen())
				clip.open(ais);
		}catch(Exception e){
			System.err.println("AudioClip: Sound couldnt be opened");
		}
	}
	
	public void play(){
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void loop(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void loop(int count){
		clip.loop(count);
	}
}
