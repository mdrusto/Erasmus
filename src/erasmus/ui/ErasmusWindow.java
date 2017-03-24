package erasmus.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import erasmus.Erasmus;
import erasmus.ui.infopanel.InfoPanel;

public class ErasmusWindow extends JFrame {

	private static final long serialVersionUID = 2334807590779291894L;
		
	//MARK JPanels
	private InfoPanel infoPanel = new InfoPanel();
	public StatusPanel statusPanel = new StatusPanel();
	private RunPanel runPanel = new RunPanel();
	
	
	//MARK JButtons
	
	
	
	//MARK JTextFields
	
	
	//MARK JLabels
	private Icon icon = new Icon();
	
	public ErasmusWindow() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setAutoCreateContainerGaps(false);
		layout.setAutoCreateGaps(false);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(infoPanel)
				.addGap(100)
				.addGroup(layout.createParallelGroup()
						.addComponent(statusPanel)
						.addComponent(icon)
						.addComponent(runPanel)));
		
		layout.setVerticalGroup(layout.createParallelGroup()
				.addComponent(infoPanel)
				.addGap(100)
				.addGroup(layout.createSequentialGroup()
						.addComponent(statusPanel)
						.addGap(360)
						.addComponent(icon)
						.addComponent(runPanel)));
		pack();
		getContentPane().setBackground(new Color(40, 40, 40));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		setVisible(true);
		setIconImage(new ImageIcon("resources/img/erasmus_icon.png").getImage());
		setTitle("Erasmus v0.0.1");
	}
	
	public void loadGuilds() {
		infoPanel.guildSelector.display(Erasmus.getJDA().getGuilds());
	}
}
