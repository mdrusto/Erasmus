package erasmus.ui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import erasmus.Erasmus;

public class StatusPanel extends JPanel {
	
	private static final long serialVersionUID = -9097462012706026062L;
	
	JLabel label = new JLabel();
	
	ImageIcon onlineIcon = new ImageIcon("online.jpg");
	ImageIcon loadingIcon = new ImageIcon("loading.gif");
	
	public StatusPanel() {
		super();
		setLayout(null);
		
		label.setSize(new Dimension(300, 60));
		add(label);
		
		setSize(300, 60);
	}
	public void setStatus(Erasmus.Status status, boolean done) {
		switch(status) {
			case ONLINE:
			if (!done) label.setIcon(loadingIcon);
			else label.setIcon(onlineIcon);
		}
	}
}
