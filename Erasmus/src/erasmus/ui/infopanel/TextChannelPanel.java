package erasmus.ui.infopanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import net.dv8tion.jda.core.entities.TextChannel;

public class TextChannelPanel extends JPanel {

	private static final long serialVersionUID = 7755357843460190781L;
	
	JTextField messageField = new JTextField();
	JButton sendButton = new JButton();
	
	public TextChannelPanel() {
		sendButton.setText("Send");
		sendButton.setSize(40, 20);
		messageField.setSize(200, 20);
		
	}
	
	public void display(TextChannel channel) {
		
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				channel.sendMessage(messageField.getText());
				messageField.setText("");
			}
		});
		messageField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				sendButton.setEnabled(!messageField.getText().equals(""));
			}
		});
		
		setVisible(true);
	}
	
}
