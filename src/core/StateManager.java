package core;

import global.GlobalSettings;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StateManager extends JPanel {
	JFrame parent;
	GlobalSettings settings;
	
	ArrayList<State> states = new ArrayList<>();
	
	public StateManager(JFrame parent, GlobalSettings settings) {
		this.parent = parent;
		this.settings = settings;
		
		Scene scene = new Scene(this, settings);
		scene.setSize(settings.getResolution()[0], settings.getResolution()[1]);
		this.setSize(settings.getResolution()[0], settings.getResolution()[1]);
		parent.add(scene);
		states.add(scene);
	}
	
	/**
	 * Push a state to the front.
	 * @param state
	 * The state to be active.
	 */
	public void pushState(State state) {
		
	}
	/**
	 * Replace the current state.
	 * @param state
	 * The state to be active.
	 */
	public void replaceState(State state) {
		
	}
	/**
	 * Replace all states.
	 * @param state
	 * The state to be active.
	 */
	public void setState(State state) {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
	}
}
