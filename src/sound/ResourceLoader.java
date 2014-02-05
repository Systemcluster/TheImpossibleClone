package sound;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Handles the Resources
 */
public abstract class ResourceLoader {
	private static Map<String,Object> loaded = new HashMap<String,Object>();
	
	
	public static Object load(String path) {
		return load(path, true);
	}
	/**
	 * Loads a file and stores its content. 
	 * Returns its content if it was already loaded before
	 * 
	 * @param path Path to the file
	 * @param cache Determines if the file should be cached or not 
	 */
	public static Object load(String path, boolean cache) {
		if(cache && loaded.containsKey(path)){
			return loaded.get(path);
		}
		else{
			ClassLoader cl = ResourceLoader.class.getClassLoader();
			BufferedInputStream bis = new BufferedInputStream(cl.getResourceAsStream(path));
			Object obj;
			try{
				if((obj = AudioIO.read(bis)) == null)
					if((obj = ImageIO.read(bis)) == null)
						if((obj = cl.getResourceAsStream(path)) == null)
							throw new FileNotFoundException("Couldn't read file: "+path); 
			}catch(IOException ioe){
				System.err.println(ioe.getMessage());
				obj = null;
			}
			if(cache) loaded.put(path, obj);
			return obj;
		}
	}
}
