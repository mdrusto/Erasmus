package erasmus.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StatusPanel extends JPanel {
	
	private static final long serialVersionUID = -9097462012706026062L;
	
	public StatusPanel() {
		super();
		JButton button = new JButton("Hi");
		add(button);
		setLayout(null);
		button.setLocation(100, 50);
		button.setVisible(true);
		setSize(300, 60);
	}
	
}
