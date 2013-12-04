package sound;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioClip {
	private Clip clip;
	private AudioInputStream ais;
	
	public AudioClip(BufferedInputStream is) throws UnsupportedAudioFileException, IOException{
		try{
			clip = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(is); 
		}catch(LineUnavailableException le){
			System.err.println("AudioClip: Init : LUE -- " + le.getMessage());
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
	public void close(){
		clip.close();
	}
}
