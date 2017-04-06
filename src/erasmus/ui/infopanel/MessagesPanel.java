package erasmus.ui.infopanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;

public class MessagesPanel extends JPanel {

	private static final long serialVersionUID = -3860894592704183045L;
		
	private Dimension size;
	
	private int currentHeight = 0;
	
	private Message lastMessage = null;
	private List<Message> continuingMessages = new ArrayList<Message>();
	
	public TextChannel channel;
	
	public int numMessages = 0;
	
	public MessagesPanel(TextChannelPanel container) {
		super();
		size = new Dimension(container.size.width, container.size.height - container.buttonSize.height);
		
		setMinimumSize(size);
		setPreferredSize(size);
		setSize(size);
		setMaximumSize(size);
		
		setLayout(null);
		setOpaque(false);
		setVisible(true);
	}
	
	public void display(TextChannel channel) {
		System.out.println(channel == null);

		removeAll();
		currentHeight = 0;
		continuingMessages.clear();
		lastMessage = null;
		
		this.channel = channel;
		
		
		addMessages(40);
	}
	
	public void addMessages(int num) {
		numMessages += num;
		List<IndividualMessagePanel> newComps = new ArrayList<IndividualMessagePanel>();
		Component[] oldComps = this.getComponents();
		
		System.out.println(channel == null);
		MessageHistory history = channel.getHistory();
		List<Message> all = history.retrievePast(numMessages).complete();
		List<Message> pastMessages = all.subList(numMessages - num, all.size() - 1);
		for (Message message: pastMessages) {
			if (!(lastMessage != null && message.getCreationTime().plusMinutes(10).compareTo(lastMessage.getCreationTime()) >= 0 && message.getAuthor().getId().equals(lastMessage.getAuthor().getId()))) {
				if (continuingMessages.size() > 0) {
					IndividualMessagePanel messagePanel1 = new IndividualMessagePanel(continuingMessages);
					add(messagePanel1);
					messagePanel1.setSize((int)size.getWidth(), messagePanel1.getHeight());
					currentHeight += messagePanel1.getHeight();
					messagePanel1.height = currentHeight;
					newComps.add(messagePanel1);
				}
				continuingMessages.clear();
				continuingMessages.add(0, message);
			}
			else continuingMessages.add(0, message);
			lastMessage = message;			
		}
		System.out.println("hi");

		size = new Dimension(this.getWidth(), currentHeight);
		
		setMinimumSize(size);
		setPreferredSize(size);
		setSize(size);
		setMaximumSize(size);
		
		synchronized(getTreeLock()) {
			for (Component comp: oldComps) {
				if (comp instanceof IndividualMessagePanel) comp.setLocation(0, this.getHeight() - ((IndividualMessagePanel)comp).height);
			}
		}
		
		for (IndividualMessagePanel panel: newComps) {
			panel.setLocation(0, this.getHeight() - panel.height);
		}
	}
}
