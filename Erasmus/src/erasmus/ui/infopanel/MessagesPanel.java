package erasmus.ui.infopanel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class MessagesPanel extends JPanel {

	private static final long serialVersionUID = -3860894592704183045L;
	
	private List<JLabel> messageLabels = new ArrayList<JLabel>();
	
	public MessagesPanel() {
		super();
		Dimension d = new Dimension(400, 380);
		
		setMinimumSize(d);
		setSize(d);
		setMaximumSize(d);
	}
	
	public void display(TextChannel channel) {
		try {
			int placing = 16;
			List<Message> reverse = new ArrayList<Message>();
			List<Message> main = channel.getHistory().retrievePast(16).block();
			for (Message message: main) {
				reverse.add(0, message);
			}
			for (Message message: reverse) {
				JLabel label = new JLabel();
				label.setText(message.getAuthor().getName() + ": " + message.getContent());
				Dimension d = new Dimension(400, 16);
				label.setMinimumSize(d);
				label.setSize(d);
				label.setPreferredSize(d);
				label.setMaximumSize(d);
				messageLabels.add(label);
				add(label);
				label.setLocation(0, 16 * placing);
				placing--;
				label.setHorizontalAlignment(SwingConstants.LEFT);
			}
		}
		catch (RateLimitedException e) {
			e.printStackTrace();
		}
		
	}
	
}
