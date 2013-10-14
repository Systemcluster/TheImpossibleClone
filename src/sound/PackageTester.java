package sound;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class PackageTester {
	public static void main(String args[]){
		ResourceLoader rl = new ResourceLoader();
		try {
			AudioSource clip = (AudioSource) rl.altLoad("res/sound.wav");
			clip.open();
			clip.start();
			SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                // A GUI element to prevent the Clip's daemon Thread
	                // from terminating at the end of the main()
	            	// Not needed for further implementations
	                JOptionPane.showMessageDialog(null, "Yep");
	            }
	        });
		} catch (IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
