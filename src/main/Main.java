package main;

import java.util.Scanner;

import javax.swing.JFrame;

import core.Scene;

public class Main extends JFrame {

	public static void main(String[] args) {
			
		new Main();
		
	}
	
	public Main() {
		super();
		
		setTitle("The Impossible Test");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 800);
		
		//add(new TestParent());
		add(new Scene());


		setVisible(true);
		
		final Main obj = this;
		Thread t = new Thread() {
			@Override 
			public void run() {

				while(true) {
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
