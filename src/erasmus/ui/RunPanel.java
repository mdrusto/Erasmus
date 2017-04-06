package erasmus.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import erasmus.bot.Erasmus;
import erasmus.bot.ErasmusBot;

public class RunPanel extends JPanel {
	
	private static final long serialVersionUID = -341491779860650821L;
	
	private JButton startButton = new JButton();
	private JButton stopButton = new JButton();
	private JButton restartButton = new JButton();
	
	private Dimension startButtonSize = new Dimension(80, 50);
	private Dimension stopButtonSize = new Dimension(80, 50);
	private Dimension restartButtonSize = new Dimension(80, 50);
	
	private Dimension size = new Dimension(300, 100);
	
	public RunPanel() {
		super();
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGap(25)
				.addGroup(layout.createSequentialGroup()
						.addGap(30)
						.addComponent(startButton)
						.addComponent(stopButton)
						.addComponent(restartButton)
						.addGap(30))
				.addGap(25));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(25)
				.addGroup(layout.createParallelGroup()
						.addComponent(startButton)
						.addComponent(stopButton)
						.addComponent(restartButton)
						.addGap(20))
				.addGap(25));
		
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
				setOn();
			}
		});
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Erasmus.stop();
				setOff();
			}
		});
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Erasmus.stop();
				setLoading();
				Erasmus.start();
				setOn();
			}
		});
		
		this.setBackground(new Color(40, 40, 40));
	}

	
	public void switchStates(boolean on) {
		startButton.setEnabled(!on);
		stopButton.setEnabled(on);
		restartButton.setEnabled(on);
	}
	
	public void setOn() {
		startButton.setEnabled(false);
		stopButton.setEnabled(true);
		restartButton.setEnabled(true);
	}
	
	public void setOff() {
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
		restartButton.setEnabled(false);
	}
	
	public void setLoading() {
		startButton.setEnabled(false);
		stopButton.setEnabled(false);
		restartButton.setEnabled(false);
	}
}
