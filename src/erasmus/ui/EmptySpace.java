package erasmus.ui;

import java.awt.Dimension;

import javax.swing.JPanel;

public class EmptySpace extends JPanel {
	
	private static final long serialVersionUID = -7055317331610378248L;
	
	private Dimension size = new Dimension(300, 320);
	
	public EmptySpace() {
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		setOpaque(false);
	}
	
}
