package erasmus.ui.infopanel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class MessagesPanel extends JPanel {

	private static final long serialVersionUID = -3860894592704183045L;
	
	private List<JLabel> messageLabels = new ArrayList<JLabel>();
	
	Dimension size = new Dimension(400, 380);
	
	public MessagesPanel() {
		super();		
		setMinimumSize(size);
		setSize(size);
		setMaximumSize(size);
		
		setLayout(null);
	}
	
	public void display(TextChannel channel) {
		try {
			int placing = 1;
		
			for (Message message: channel.getHistory().retrievePast(18).block()) {
				JLabel label = new JLabel();
				label.setText(message.getAuthor().getName() + ": " + message.getContent());
				Dimension labelSize = new Dimension(400, 16);
				label.setMinimumSize(labelSize);
				label.setSize(labelSize);
				label.setPreferredSize(labelSize);
				label.setMaximumSize(labelSize);
				
				messageLabels.add(label);
				label.setLocation(0, (int)size.getHeight() - (int)labelSize.getHeight() * placing);
				add(label);
				placing++;
				label.setHorizontalAlignment(SwingConstants.LEFT);
			}
			
		}
		catch (RateLimitedException e) {
			e.printStackTrace();
		}
		
	}
	
}
