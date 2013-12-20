package sound;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ResourceLoader {
	private static Map<String,Object> loaded = new HashMap<String,Object>();
	
	public static Object load(String path) {
		if(loaded.containsKey(path)){
			return loaded.get(path);
		}
		else{
			//CHANGED : CODEMONKEYSTILISH CODE
			/*	if(path.endsWith(".dat"))
					obj = cl.getResourceAsStream(path);
				else
					obj = ImageIO.read(is);
			*/
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
			loaded.put(path, obj);
			return obj;
		}
	}
}
