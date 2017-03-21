package erasmus.ui;

import javax.swing.*;

public class ErasmusWindow extends JFrame {

	private static final long serialVersionUID = 2334807590779291894L;
	
	private Status status = Status.OFFLINE;
	
	//MARK JPanels
	private ServerPanel channelView = new ServerPanel();
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
				.addComponent(channelView)
				.addGroup(layout.createParallelGroup()
						.addComponent(statusPanel)
						.addComponent(icon)
						.addComponent(runPanel)));
		
		layout.setVerticalGroup(layout.createParallelGroup()
				.addComponent(channelView)
				.addGroup(layout.createSequentialGroup()
						.addComponent(statusPanel)
						.addComponent(icon)
						.addComponent(runPanel)));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
