package erasmus.ui.infopanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.dv8tion.jda.core.entities.*;

public class TextChannelSelectorPanel extends JScrollPane {

	private static final long serialVersionUID = 3461070594233407645L;
	
	public HashMap<JButton, TextChannel> channels = new HashMap<JButton, TextChannel>();
	
	InfoPanel container;
	
	JPanel textChannelPanel = new JPanel();
	
	JButton currentButton, lastButton;
	
	Dimension size;
	
	public TextChannelSelectorPanel(InfoPanel container) {
		this.container = container;
		
		size = new Dimension(200, container.getSize().height);
		
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
		
		textChannelPanel.setVisible(true);
		
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		textChannelPanel.setLayout(new BoxLayout(textChannelPanel, BoxLayout.Y_AXIS));
		
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		
		
		setViewportView(textChannelPanel);
		
		textChannelPanel.setBackground(new Color(40, 40, 40));

	}
	
	public void display(Guild guild) {
		textChannelPanel.removeAll();
		textChannelPanel.revalidate();
		for (TextChannel channel: guild.getTextChannels()) {
			JButton button = new JButton();
			this.channels.put(button, channel);
			
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					container.textChannelSelected(channel);
					
					lastButton = currentButton;
					currentButton = button;
					if (lastButton != null) {
						lastButton.setEnabled(true);
						lastButton.setBackground(Color.GRAY);
					}
					button.setBackground(Color.DARK_GRAY);
					
					button.setEnabled(false);
				}
			});
			
			Dimension d = new Dimension(200, 30);
			
			button.setMinimumSize(d);
			button.setSize(d);
			button.setMaximumSize(d);
			
			button.setText(channel.getName());
			
			button.setFocusable(false);
			
			//button.setBorder(BorderFactory.createEmptyBorder());
			
			button.setBackground(Color.GRAY);
			
			textChannelPanel.add(button);
			
			button.setForeground(Color.WHITE);
			button.setBorder(BorderFactory.createEmptyBorder());
			button.setOpaque(false);
			
			button.setVisible(true);
		}
		textChannelPanel.setVisible(true);
		
		setVisible(false);
		setVisible(true);
		
	}
	
	public void hideThis() {
		textChannelPanel.removeAll();
		setVisible(false);
		textChannelPanel.revalidate();
	}
}
