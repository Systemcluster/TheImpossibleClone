package core;

import global.GlobalSettings;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class State extends JPanel {
	
	// screen aspect ratio (calculated by screen w/h) 
	private double ytiles = 1.3;
	
	public GlobalSettings globalSettings;
	public JPanel parent;
	
	public State(JPanel parent, GlobalSettings globalSettings) {
		this.parent = parent;
		this.globalSettings = globalSettings;
		
		//fix aspect ratio
		ytiles = Math.round(
				new Double(globalSettings.getResolution()[0]) / new Double(globalSettings.getResolution()[1]) * 100
				) / 100.0;
		System.out.println("Using resolution "+globalSettings.getResolution()[0] +"x"+ globalSettings.getResolution()[1]);
		System.out.println("Set aspect ratio to "+ytiles);
		
	}
	
	
	public int getCoordXFixed(double x) {
		double coord = getWidth() * (x / ytiles) + 0.5;
		return (int)(coord);
	}
	
	/**
	 * Returns the real position from grid position y.
	 * @param y
	 * The grid position to get the real position from.
	 * @return
	 * The real position calculated from grid position y.
	 */
	public int getCoordY(double y) {
		return (int) ((this.getHeight() / 1) * y + 0.5);
	}
	
	/**
	 * Returns the real width from grid width w.
	 * @param w
	 * The grid width to get the real width from.
	 * @return
	 * The real width calculated from grid width w.
	 */
	public int getWidth(double w) {
		//return (int) ((this.getWidth() / (ytiles * ((double)getWidth()/(double)getHeight()))) * w);
		double coord = (double) getWidth() * (w / ytiles) + 0.5;
		return (int) coord;
	}
	/**
	 * Returns the real height from grid height h.
	 * @param h
	 * The grid height to get the real height from.
	 * @return
	 * The real height calculated from grid width w.
	 */
	public int getHeight(double h) {
		return (int) ((this.getHeight() / 1) * h + 0.5);
	}
	
	/**
	 * Returns the width of one screen.
	 * @return
	 * The width of one screen.
	 */
	public double getXWidth() {
		return ytiles;
	}

}
