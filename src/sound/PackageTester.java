package sound;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class PackageTester {
	public static void main(String args[]){
		try {
			AudioClip clip = (AudioClip) ResourceLoader.load("res/Jump.wav");
			clip.open();
			clip.play();
			SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "You know the rules ?"))
	            		JOptionPane.showMessageDialog(null, "And so do i");
	            	
	            }
	        });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
