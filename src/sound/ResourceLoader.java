package sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;

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
			BufferedInputStream bis = new BufferedInputStream(cl.getResourceAsStream(path));
			Object obj;
			try {
				obj = new AudioClip(bis);
			} catch (UnsupportedAudioFileException | IOException e) {
				System.out.println(e.getMessage());
				try{
					/*if((obj = ImageIO.read(is)) == null){
						obj = cl.getResourceAsStream(path);
					}*/
					if(path.endsWith(".dat"))
						obj = cl.getResourceAsStream(path);
					else
						obj = ImageIO.read(is);
				}
				catch(IOException ie){
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
