package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import global.GlobalSettings;
import global.MainMenuInterface;

import javax.swing.JFrame;

import core.Scene;

public class Main extends JFrame {
	
	public Main(GlobalSettings gs, final MainMenuInterface mmi) {
		super();
		
		setTitle("The Impossible Test");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				mmi.returnToMainMenu();
			    dispose();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}
		});
		setSize(gs.getResolution()[0], gs.getResolution()[1]);
		
		//add(new TestParent());
		add(new Scene(gs));


		setVisible(true);
		
		final Main obj = this;
		Thread t = new Thread() {
			@Override 
			public void run() {

				while(obj.isDisplayable()) {
					try {
						obj.run();
						this.sleep(16);
					} catch (InterruptedException e) {
						e.printStackTrace();
						System.out.println("Hurr Durr MF");
					}
				}
			}
		};
		t.start();
	}
	
	private void run() {
		repaint();
	}

	

}
