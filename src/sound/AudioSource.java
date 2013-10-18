package sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioSource {
	
	private Clip clip;
	private AudioInputStream ais;
	
	public AudioSource(String path) throws LineUnavailableException, UnsupportedAudioFileException, IOException{
		ais = AudioSystem.getAudioInputStream(new File(path));
		clip = createClip(ais);
	}
	public AudioSource(InputStream is) throws LineUnavailableException, UnsupportedAudioFileException, IOException{
		ais = AudioSystem.getAudioInputStream(is);
		clip = createClip(ais);
		clip.addLineListener(new LineListener(){
			public void update(LineEvent event) {
				if(event.getType() == LineEvent.Type.START){
					clip.setFramePosition(0);
				}
				if(event.getType() == LineEvent.Type.STOP){
				}
				if(event.getType() == LineEvent.Type.CLOSE){
				}
				if(event.getType() == LineEvent.Type.OPEN){
				}
			}
        	
        });
	}
	private Clip createClip (AudioInputStream ais) throws LineUnavailableException{
		clip = AudioSystem.getClip();
		//Linux schnipp 
		//--- Testen mit WINDOWS / MAC
		AudioFormat format = ais.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        return (Clip)AudioSystem.getLine(info);
        //---
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
		Thread t = new Thread(new Runnable(){
			public void run(){
				clip.stop();
				clip.start();	
				clip.stop();
			}
		});
		t.start();
	}
	public void stop(){
		clip.stop();
	}
	public boolean isRunning(){
		return clip.isRunning();
	}
	public void close(){
		clip.close();
	}
	public boolean isOpen(){
		return clip.isOpen();
	}
}
