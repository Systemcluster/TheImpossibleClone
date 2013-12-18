package core;

import global.GlobalSettings;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class State extends JPanel {
	
	public GlobalSettings globalSettings;
	public JPanel parent;
	
	public State(JPanel parent, GlobalSettings globalSettings) {
		this.parent = parent;
		this.globalSettings = globalSettings;
	}

}
