package erasmus.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import erasmus.Erasmus;

public class StatusPanel extends JPanel {
	
	private static final long serialVersionUID = -9097462012706026062L;
	
	JLabel label = new JLabel();
	
	ImageIcon onlineIcon = new ImageIcon("resources/img/online.png");
	ImageIcon loadingIcon = new ImageIcon("resources/img/loading.gif");
	
	private Dimension size = new Dimension(300, 80);
	
	public StatusPanel() {
		super();
		setLayout(null);
		
		label.setSize(new Dimension(80, 80));
		label.setLocation(0, 0);
		add(label);
		
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	public void setStatus(Erasmus.Status status, boolean done) {
		switch(status) {
			case ONLINE:
			if (!done) label.setIcon(loadingIcon);
			else label.setIcon(onlineIcon);
		}
	}
}
