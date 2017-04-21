package erasmus.ui.infopanel;

import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.*;

import erasmus.ui.Colours;
import net.dv8tion.jda.core.entities.MessageChannel;

public class MessageChannelPanel extends JPanel {

	private static final long serialVersionUID = 7755357843460190781L;
	
	JTextField messageField = new JTextField();
	JButton sendButton = new JButton();
	public MessagesPanel messagesPanel;
	public JScrollPane channelScroll = new JScrollPane();
	
	public boolean started = false;
	
	public Dimension size = new Dimension(700, 800);
	
	public Dimension buttonSize = new Dimension(80, 40);
	
	public MessageChannelPanel(MessageChannel channel) {
		super();
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		messagesPanel = new MessagesPanel(this);
		channelScroll.setViewportView(messagesPanel);
		channelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		channelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		channelScroll.setOpaque(false);
		channelScroll.getViewport().setOpaque(false);
		channelScroll.getVerticalScrollBar().setUnitIncrement(10);
		/*channelScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent event) {
				if (!started) return;
				int oldValue = channelScroll.getVerticalScrollBar().getMinimum();
				if (event.getValue() == oldValue && messagesPanel.channel != null) {
					int oldHeight = messagesPanel.getHeight();
					messagesPanel.addPastMessages(10);
					int newHeight = messagesPanel.getHeight();
					channelScroll.getVerticalScrollBar().setValue(channelScroll.getVerticalScrollBar().getMaximum() - channelScroll.getVerticalScrollBar().getMaximum() * oldHeight / newHeight);

				}
			}
		});*/
		channelScroll.getVerticalScrollBar().setOpaque(false);
		channelScroll.getVerticalScrollBar().setForeground(Colours.BACKGROUND);
		
		channelScroll.getVerticalScrollBar().setValue(channelScroll.getVerticalScrollBar().getMaximum());
		
		sendButton.setText("Send");
		
		sendButton.setMinimumSize(buttonSize);
		sendButton.setSize(buttonSize);
		sendButton.setPreferredSize(buttonSize);
		sendButton.setMaximumSize(buttonSize);
		
		Dimension messageFieldSize = new Dimension(size.width - buttonSize.width, buttonSize.height);
		messageField.setMinimumSize(messageFieldSize);
		messageField.setSize(messageFieldSize);
		messageField.setPreferredSize(messageFieldSize);
		messageField.setMaximumSize(messageFieldSize);
		
		messagesPanel.setVisible(true);
		messageField.setVisible(true);
		sendButton.setVisible(true);
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(channelScroll)
				.addGroup(layout.createSequentialGroup()
						.addComponent(messageField)
						.addComponent(sendButton)));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(channelScroll)
				.addGroup(layout.createParallelGroup()
						.addComponent(messageField)
						.addComponent(sendButton)));
		
		
		setOpaque(false);

		messagesPanel.display(channel);
	}
}
