package erasmus.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import erasmus.Erasmus;

public class RunPanel extends JPanel {
	
	private static final long serialVersionUID = -341491779860650821L;
	
	private JButton startButton = new JButton();
	private JButton stopButton = new JButton();
	private JButton restartButton = new JButton();
	
	private Dimension startButtonSize = new Dimension(100, 40);
	private Dimension stopButtonSize = new Dimension(100, 40);
	private Dimension restartButtonSize = new Dimension(100, 40);
	
	private Dimension size = new Dimension(300, 60);
	
	public RunPanel() {
		super();
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGap(20)
				.addGroup(layout.createSequentialGroup()
				.addComponent(startButton)
				.addComponent(stopButton)
				.addComponent(restartButton)));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(20)
				.addGroup(layout.createParallelGroup()
				.addComponent(startButton)
				.addComponent(stopButton)
				.addComponent(restartButton)));
		
		startButton.setMinimumSize(startButtonSize);
		startButton.setSize(startButtonSize);
		startButton.setPreferredSize(startButtonSize);
		startButton.setMaximumSize(startButtonSize);
		startButton.setText("Start");
		startButton.setFocusable(false);
		startButton.setEnabled(false);
		
		stopButton.setMinimumSize(stopButtonSize);
		stopButton.setSize(stopButtonSize);
		stopButton.setPreferredSize(stopButtonSize);
		stopButton.setMaximumSize(stopButtonSize);
		stopButton.setText("Stop");
		stopButton.setFocusable(false);
		
		restartButton.setMinimumSize(restartButtonSize);
		restartButton.setSize(restartButtonSize);
		restartButton.setMaximumSize(restartButtonSize);
		restartButton.setMaximumSize(restartButtonSize);
		restartButton.setText("Restart");
		restartButton.setFocusable(false);
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Erasmus.start();
				switchStates(true);
			}
		});
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Erasmus.stop();
				switchStates(false);
			}
		});
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Erasmus.stop();
				switchStates(false);
				
				Erasmus.start();
				switchStates(true);
			}
		});
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
