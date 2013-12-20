package core;

import global.GlobalSettings;

import java.awt.Graphics;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
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
		
		Menu scene = new Menu(this, settings);
		scene.setSize(settings.getResolution()[0], settings.getResolution()[1]);
		this.setSize(settings.getResolution()[0], settings.getResolution()[1]);
		parent.add(scene);
		states.add(scene);
		initState(scene);
		
		parent.getContentPane().addHierarchyBoundsListener(new HierarchyBoundsListener() {
			@Override
			public void ancestorMoved(HierarchyEvent e) {
				
			}
			@Override
			public void ancestorResized(HierarchyEvent e) {
				onResize();
			}
		});
	}
	
	public void onResize() {
		setSize(parent.getWidth(), parent.getHeight());
		states.get(0).setSize(parent.getWidth(), parent.getHeight());
		states.get(0).resize();
	}
	
	private void initState(State state) {
		state.setSize(parent.getWidth(), parent.getHeight());
		state.resize();
		state.setFocusable(true);
		state.requestFocus();
	}
	
	/**
	 * Push a state to the front.
	 * @param state
	 * The state to be active.
	 */
	public void pushState(State state) {
		parent.remove(states.get(0));
		states.add(0, state);
		parent.add(state);
		initState(state);
	}
	/**
	 * Replace the current state.
	 * @param state
	 * The state to be active.
	 */
	public void replaceState(State state) {
		parent.remove(states.get(0));
		states.set(0, null);
		states.remove(0);
		states.add(0, state);
		parent.add(state);
		initState(state);
	}
	/**
	 * Replace all states.
	 * @param state
	 * The state to be active.
	 */
	public void setState(State state) {
		for(State s: states) {
			parent.remove(s);
		}
		parent.add(state);
		states.add(state);
		initState(state);
	}
	
	/**
	 * Pop the state.
	 */
	public void popState() {
		parent.remove(states.get(0));
		states.set(0, null);
		states.remove(0);
		parent.add(states.get(0));
		initState(states.get(0));
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		
	}
}
