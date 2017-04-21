package erasmus.ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JPanel;

public class RightSidePanel extends JPanel {

	private static final long serialVersionUID = 7503402610421354996L;
	
	public StatusPanel statusPanel = new StatusPanel();
	private EmptySpace emptySpace = new EmptySpace();
	private Icon icon = new Icon();
	private RunPanel runPanel = new RunPanel();	
	
	public RightSidePanel() {
		//BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(statusPanel)
				.addComponent(emptySpace)
				.addComponent(icon)
				.addComponent(runPanel));
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(statusPanel)
				.addComponent(emptySpace)
				.addComponent(icon)
				.addComponent(runPanel));
		
		setOpaque(false);
		
		setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(80, 80, 80)));

		
	}
	
}
