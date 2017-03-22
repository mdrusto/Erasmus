package erasmus.ui.infopanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import net.dv8tion.jda.core.entities.*;

public class TextChannelSelectorPanel extends JScrollPane {

	private static final long serialVersionUID = 3461070594233407645L;
	
	private HashMap<JButton, TextChannel> channels = new HashMap<JButton, TextChannel>();
	
	InfoPanel container;
	
	JPanel textChannelPanel = new JPanel();
	
	public TextChannelSelectorPanel(InfoPanel container) {
		this.container = container;
		
		Dimension d = new Dimension(200, 420);
		
		textChannelPanel.setMinimumSize(d);
		textChannelPanel.setSize(d);
		textChannelPanel.setMaximumSize(d);
		textChannelPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		textChannelPanel.setVisible(true);
		
		setMinimumSize(d);
		setSize(d);
		setMaximumSize(d);
		
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		
		
		setViewportView(textChannelPanel);
	}
	
	public void display(List<TextChannel> channels) {
		int placing = 0;
		for (TextChannel channel: channels) {
			JButton button = new JButton();
			this.channels.put(button, channel);
			
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					container.textChannelSelected(channel);
				}
			});
			
			Dimension d = new Dimension(200, 50);
			
			button.setMinimumSize(d);
			button.setSize(d);
			button.setMaximumSize(d);
			
			button.setText(channel.getName());
			
			textChannelPanel.add(button);
			button.setLocation(0, 50 * placing);
			
			button.setVisible(true);
			placing++;
		}
		textChannelPanel.setVisible(true);
		setVisible(true);
	}
	
	public void hideThis() {
		removeAll();
		setVisible(false);
	}
}
