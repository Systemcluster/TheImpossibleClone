package sound;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ResourceLoader {
	private static Map<String,Object> loaded = new HashMap<String,Object>();
	
	public static Object load(String path) throws IOException{
		if(loaded.containsKey(path)){
			return loaded.get(path);
		}
		else{
			InputStream is = ResourceLoader.class.getClassLoader().getResourceAsStream(path);
			Object obj;
			try {
				obj = new AudioClip(is);
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
