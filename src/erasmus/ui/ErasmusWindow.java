package erasmus.ui;

import javax.swing.*;

import erasmus.ErasmusMain;
import erasmus.ui.infopanel.InfoPanel;

public class ErasmusWindow extends JFrame {

	private static final long serialVersionUID = 2334807590779291894L;
	
	private Status status = Status.OFFLINE;
	
	//MARK JPanels
	private InfoPanel infoPanel = new InfoPanel();
	private StatusPanel statusPanel = new StatusPanel();
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
						.addComponent(icon)
						.addComponent(runPanel)));
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		setVisible(true);
		setIconImage(new ImageIcon("erasmus_icon.png").getImage());
		setTitle("Erasmus v0.0.1");
	}
	
	public void loadGuilds() {
		infoPanel.guildSelector.display(ErasmusMain.jda.getGuilds());
	}
	
	public Status getStatus() {
		return status;
	}
	
	public static enum Status {
		
		ERROR(-1),
		
		OFFLINE(0),
		
		ONLINE(1);
		
		int number;

		Status(int n) {
			this.number = n;
		}
		
		public int getNumber() {
			return number;
		}
	}
}
