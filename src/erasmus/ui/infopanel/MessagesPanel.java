package erasmus.ui.infopanel;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageHistory;

public class MessagesPanel extends JPanel {

	private static final long serialVersionUID = -3860894592704183045L;
		
	private Dimension size;
	
	private int currentHeight = 0;
	
	private Message lastMessage = null;
	private List<Message> continuingMessages = new ArrayList<Message>();
	
	public MessageChannel channel;
	public MessageHistory history;
	
	private MessageGroupPanel last = null;
	
	private boolean hasStarted = false;
	
	MessageChannelPanel container;
	
	public int numMessages = 0;
	
	public MessagesPanel(MessageChannelPanel container) {
		super();
		this.container = container;
		size = new Dimension(container.size.width, container.size.height - container.buttonSize.height);
		
		setMinimumSize(size);
		setPreferredSize(size);
		setSize(size);
		setMaximumSize(size);
		
		setLayout(null);
		setOpaque(false);
		setVisible(true);
	}
	
	public void display(MessageChannel channel) {
		removeAll();
		currentHeight = 0;
		continuingMessages.clear();
		lastMessage = null;
		
		this.channel = channel;
		history = channel.getHistory();
		addPastMessages(40);
		((MessageChannelPanel)getParent().getParent().getParent()).started = true;
	}
	
	public void addPastMessages(int num) {
		numMessages += num;
		List<MessageGroupPanel> newComps = new ArrayList<MessageGroupPanel>();
		Component[] oldComps;
		synchronized(getTreeLock()) {
			oldComps = this.getComponents();
		}
		
		List<Message> pastMessages = history.retrievePast(num).complete();
		
		
		for (Message message: pastMessages) {
			if (!(lastMessage != null && message.getCreationTime().plusMinutes(10).compareTo(lastMessage.getCreationTime()) >= 0 && message.getAuthor().equals(lastMessage.getAuthor()))) {
				if (continuingMessages.size() > 0) {
					MessageGroupPanel messagePanel1 = new MessageGroupPanel(continuingMessages);
					if (!hasStarted) {
						last = messagePanel1;
						hasStarted = true;
					}
					add(messagePanel1);
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

		size = new Dimension(this.getWidth(), currentHeight);
		
		setMinimumSize(size);
		setPreferredSize(size);
		setSize(size);
		setMaximumSize(size);
		
		synchronized(getTreeLock()) {
			for (Component comp: oldComps) {
				if (comp instanceof MessageGroupPanel) comp.setLocation(0, this.getHeight() - ((MessageGroupPanel)comp).height);
			}
		}
		
		for (MessageGroupPanel panel: newComps) {
			panel.setLocation(0, this.getHeight() - panel.height);
		}
	}
	
	public void addNewMessage(Message message) {
		if (last.getAuthor().equals(message.getAuthor()) && message.getCreationTime().plusMinutes(10).compareTo(last.getLastMessage().getCreationTime()) >= 0) last.addMessage(message);
		else {
			List<Message> list = new ArrayList<Message>();
			list.add(message);
			MessageGroupPanel panel = new MessageGroupPanel(list);
			add(panel);
			panel.height = currentHeight;
			currentHeight += panel.getHeight();
			
			size = new Dimension(this.getWidth(), currentHeight);
			
			setMinimumSize(size);
			setPreferredSize(size);
			setSize(size);
			setMaximumSize(size);
			
			panel.setLocation(0, getHeight() - panel.getHeight());
		}
	}
}
