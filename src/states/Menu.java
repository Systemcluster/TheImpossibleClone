package states;

import foreground.Foreground;
import global.GlobalSettings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import sound.ResourceLoader;
import background.Background;
import core.ScoreManager;
import core.State;
import core.StateManager;

@SuppressWarnings("serial")
public class Menu extends State {
	
	private BufferedImage img_menu = (BufferedImage) ResourceLoader.load("res/menu/l_menu.png");
	
	double offset = 0;
	double count = 1.7;
	
	Background bg = new Background(this);
	Foreground fg = new Foreground(this);

	public Menu(StateManager parent, GlobalSettings settings) {
		super(parent, settings);

		final Menu m = this;
		
		fg.setSecretProbability(0.18);
		
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_N: {
					m.parent.pushState(new Scene(m.parent,m.settings));
				}break;
				case KeyEvent.VK_ESCAPE:
				case KeyEvent.VK_E: {
					m.parent.getWindow().dispose();
				}break;
				case KeyEvent.VK_S: {
					JOptionPane.showMessageDialog(m.parent, ScoreManager.getFormatted(), "Scores", JOptionPane.INFORMATION_MESSAGE);
				}break;
				case KeyEvent.VK_C: {
					
					if(JOptionPane.showConfirmDialog(m.parent, "Really clear the scoreboard?", "Scores", JOptionPane.YES_NO_OPTION)
							== JOptionPane.YES_OPTION){
						if(ScoreManager.clearScore()) {
							JOptionPane.showMessageDialog(m.parent, "Scores cleared!", "Scores", JOptionPane.INFORMATION_MESSAGE);
						}
						else JOptionPane.showMessageDialog(m.parent, "No scores to clear!", "Scores", JOptionPane.INFORMATION_MESSAGE);
					}
				}break;
				default: {
					
				}break;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		count += 0.03;
		//offset = Math.sin(count)/40;
		
		xposition += 0.0035;
		
		//--clear bg--
		g.setColor(new Color(180, 220, 250));
		g.fillRect(0, 0, getWidth(), getHeight());
		//--/clear bg--
		
		
		bg.update();
		fg.update();
		
		bg.paintComponent(g);
		fg.paintComponent(g);
		
		((Graphics2D) g).drawImage(img_menu, this.getCoordXFixed(0.5)-getWidth(1)/2, getCoordY(-0.01+offset), getWidth(0.95), getHeight(0.95), null);
	}

}
