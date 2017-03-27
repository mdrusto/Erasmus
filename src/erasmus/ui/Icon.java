package erasmus.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class Icon extends JLabel {
	
	private static final long serialVersionUID = 8758110233173287590L;
	
	private Dimension size = new Dimension(300, 300);
	
	public Icon() {
		super();
		setIcon(new ImageIcon("resources/img/erasmus_icon.png"));
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
}
