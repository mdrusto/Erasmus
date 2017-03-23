package erasmus.ui;

import java.awt.Dimension;

import javax.swing.*;

public class RunPanel extends JPanel {
	
	private static final long serialVersionUID = -341491779860650821L;
	
	private JButton startButton = new JButton();
	private JButton stopButton = new JButton();
	private JButton restartButton = new JButton();
	
	public RunPanel() {
		super();
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(false);
		layout.setAutoCreateContainerGaps(false);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(startButton)
				.addComponent(stopButton)
				.addComponent(restartButton));
		layout.setVerticalGroup(layout.createParallelGroup()
				.addComponent(startButton)
				.addComponent(stopButton)
				.addComponent(restartButton));
		setSize(300, 60);
		startButton.setMinimumSize(new Dimension(100, 60));
		startButton.setSize(100, 60);
		startButton.setMaximumSize(new Dimension(100, 60));
		startButton.setText("Start");
		startButton.setFocusable(false);
		stopButton.setMinimumSize(new Dimension(100, 60));
		stopButton.setSize(100, 60);
		stopButton.setMaximumSize(new Dimension(100, 60));
		stopButton.setText("Stop");
		stopButton.setFocusable(false);
		restartButton.setMinimumSize(new Dimension(100, 60));
		restartButton.setSize(100, 60);
		restartButton.setMaximumSize(new Dimension(100, 60));
		restartButton.setText("Restart");
		restartButton.setFocusable(false);

	}
	
	public JButton getStartButton() {
		return startButton;
	}
	
	public JButton getStopButton() {
		return stopButton;
	}
	
	public JButton getRestartButton() {
		return restartButton;
	}
	
	public void switchStates(boolean on) {
		startButton.setEnabled(!on);
		stopButton.setEnabled(on);
		restartButton.setEnabled(on);
	}
}
