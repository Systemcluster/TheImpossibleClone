import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioSource {
	
	private Clip clip;
	private AudioInputStream ais;
	
	public AudioSource(String path) throws LineUnavailableException, UnsupportedAudioFileException, IOException{
		clip = AudioSystem.getClip();
		ais = AudioSystem.getAudioInputStream(new File(path));
	}
	public void open() throws LineUnavailableException, IOException{
		clip.open(ais);
	}
	public void loop(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void loop(int count){
		clip.loop(count);
	}
	public void start(){
		clip.start();
	}
	public void stop(){
		clip.stop();
	}
	public boolean isRunning(){
		return clip.isRunning();
	}
}
