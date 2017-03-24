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
	
	private Dimension size;
	private Dimension labelSize = new Dimension(600, 30);
	
	public MessagesPanel(TextChannelPanel container) {
		super();
		size = new Dimension(container.size.width, container.size.height - container.buttonSize.height);
		setMinimumSize(size);
		setPreferredSize(size);
		setSize(size);
		setMaximumSize(size);
		
		setLayout(null);
	}
	
	public void display(TextChannel channel) {
		removeAll();
		try {
			int placing = 1;
		
			for (Message message: channel.getHistory().retrievePast(18).block()) {
				JLabel label = new JLabel();
				label.setText("<html>" + message.getAuthor().getName() + ": " + message.getContent() + "</html>");
				label.setSize(labelSize);
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
