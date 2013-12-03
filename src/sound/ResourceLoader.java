package sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ResourceLoader {
	private static Map<String,Object> loaded = new HashMap<String,Object>();
	
	public static Object load(String path) {
		if(loaded.containsKey(path)){
			return loaded.get(path);
		}
		else{
			ClassLoader cl = ResourceLoader.class.getClassLoader();
			InputStream is = cl.getResourceAsStream(path);
			//is.mark(0);
			Object obj;
			try {
				obj = new AudioClip(is);
			} catch (UnsupportedAudioFileException e) {
				try{
					if((obj = ImageIO.read(is)) == null){
						is.reset();
						obj = new File(cl.getSystemResource(path).toURI());
					}
				}catch(URISyntaxException use){
					System.err.println(use.getMessage());
					obj = null;
				}catch(IOException ie){
					obj = null;
				}
			} catch (Exception e) {	
				obj = null;
			}
			loaded.put(path, obj);
			return obj;
		}
	}	
}
