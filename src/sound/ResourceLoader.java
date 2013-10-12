import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ResourceLoader {

	private Map<String,Object> loaded;
	
	private List<String> supportedImageTypes;
	private List<String> supportedAudioTypes;
	
	public ResourceLoader(){
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
			File f = new File(path);
			String fileType = path.substring(path.lastIndexOf(".") + 1,path.length());
			if(supportedImageTypes.contains(fileType)){
				BufferedImage img = ImageIO.read(f);
				loaded.put(path, img);
				return img;
			}
			else if(supportedAudioTypes.contains(fileType)){
				AudioSource as = new AudioSource(path);
				loaded.put(path, as);
				return as;
			}
			else{
				throw new IOException("UnsupportedFileType");
			}
		}
	}
}
