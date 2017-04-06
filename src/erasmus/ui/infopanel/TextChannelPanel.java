package erasmus.ui.infopanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.TextChannel;

public class TextChannelPanel extends JPanel {

	private static final long serialVersionUID = 7755357843460190781L;
	
	JTextField messageField = new JTextField();
	JButton sendButton = new JButton();
	MessagesPanel messagesPanel;
	JScrollPane channelScroll = new JScrollPane();
	
	public Dimension size = new Dimension(700, 800);
	
	public Dimension buttonSize = new Dimension(80, 40);
	
	public TextChannelPanel() {
		super();
		
		messagesPanel = new MessagesPanel(this);
		channelScroll.setViewportView(messagesPanel);
		channelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		channelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		channelScroll.setOpaque(false);
		channelScroll.getViewport().setOpaque(false);
		channelScroll.getVerticalScrollBar().setUnitIncrement(10);
		channelScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent event) {
				int oldValue = channelScroll.getVerticalScrollBar().getMinimum();
				if (event.getValue() == oldValue && messagesPanel.channel != null) {
					channelScroll.getVerticalScrollBar().setValue(oldValue + 100);
					messagesPanel.addMessages(10);
				}
			}
		});
		
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
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		setOpaque(false);

	}
	
	public void display(TextChannel channel) {
		if (!channel.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE)) {
			messageField.setEnabled(false);
			messageField.setText("Erasmus is not able to send messages in this channel");
			sendButton.setEnabled(false);
		}
		else {
			messageField.setEnabled(true);
			messageField.setText("");
			sendButton.setEnabled(true);
			sendButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					channel.sendMessage(messageField.getText()).queue();
					messageField.setText("");
				}
			});
			sendButton.setEnabled(false);
			messageField.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void changedUpdate(DocumentEvent event) {
					changed();
				}
				
				@Override
				public void removeUpdate(DocumentEvent event) {
					changed();
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					changed();
				}
				
				public void changed() {
					sendButton.setEnabled(!messageField.getText().equals(""));
				}
			});
		}
		
		messagesPanel.display(channel);
		
		setVisible(true);
		repaint();
	}
	
	public void hideThis() {
		messagesPanel.removeAll();
		setVisible(false);
		messagesPanel.revalidate();
	}
	
}
