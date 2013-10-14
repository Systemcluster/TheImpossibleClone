package sound;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ResourceLoader {
	ClassLoader cl;
	
	private Map<String,Object> loaded;
	
	private List<String> supportedImageTypes;
	private List<String> supportedAudioTypes;
	
	public ResourceLoader(){
		cl = this.getClass().getClassLoader();
		
		loaded = new HashMap<String,Object>();
		
		supportedImageTypes = new Vector<String>();
		supportedAudioTypes = new Vector<String>();
	
		Collections.addAll(supportedImageTypes, "png", "jpg", "gif", "bmp");
		Collections.addAll(supportedAudioTypes, "wav", "au", "snd", "aiff", "aif", "aifc");
	}
	public Object load(String path) throws IOException, LineUnavailableException, UnsupportedAudioFileException{
		if(loaded.containsKey(path)){
			System.out.println("Loaded");
			return loaded.get(path);
		}
		else{
			InputStream is = cl.getResourceAsStream(path);
			String fileType = path.substring(path.lastIndexOf(".") + 1,path.length());
			if(supportedImageTypes.contains(fileType)){
				BufferedImage img = ImageIO.read(is);
				loaded.put(path, img);
				return img;
			}
			else if(supportedAudioTypes.contains(fileType)){
				AudioSource as = new AudioSource(is);
				loaded.put(path, as);
				return as;
			}
			else{
				//throw new IOException("UnsupportedFileType");
				return null;
			}
		}
	}
	
	public Object altLoad(String path) throws IOException{
		if(loaded.containsKey(path)){
			return loaded.get(path);
		}
		else{
			InputStream is = cl.getResourceAsStream(path);
			Object obj;
			try {
				obj = new AudioSource(is);
			} catch (UnsupportedAudioFileException e) {
				obj = ImageIO.read(is);
			} catch (Exception e) {
				return null;
			}
			loaded.put(path, obj);
			return obj;
		}
	}	
}
