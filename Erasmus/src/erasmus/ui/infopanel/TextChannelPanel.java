package erasmus.ui.infopanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.dv8tion.jda.core.entities.TextChannel;

public class TextChannelPanel extends JPanel {

	private static final long serialVersionUID = 7755357843460190781L;
	
	JTextField messageField = new JTextField();
	JButton sendButton = new JButton();
	MessagesPanel messagesPanel = new MessagesPanel();
	
	public TextChannelPanel() {
		super();
		
		sendButton.setText("Send");
		
		Dimension d = new Dimension(80, 40);
		sendButton.setMinimumSize(d);
		sendButton.setSize(d);
		sendButton.setMaximumSize(d);
		
		Dimension newD = new Dimension(320, 40);
		messageField.setMinimumSize(newD);
		messageField.setSize(newD);
		messageField.setMaximumSize(newD);
		
		messagesPanel.setVisible(true);
		messageField.setVisible(true);
		sendButton.setVisible(true);
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(messagesPanel)
				.addGroup(layout.createSequentialGroup()
						.addComponent(messageField)
						.addComponent(sendButton)));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(messagesPanel)
				.addGroup(layout.createParallelGroup()
						.addComponent(messageField)
						.addComponent(sendButton)));
		Dimension dd = new Dimension(400, 820);
		setMinimumSize(dd);
		setSize(dd);
		setPreferredSize(dd);
		setMaximumSize(dd);
	}
	
	public void display(TextChannel channel) {
		
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
		
		messagesPanel.display(channel);
		
		setVisible(true);
		repaint();
	}
	
}