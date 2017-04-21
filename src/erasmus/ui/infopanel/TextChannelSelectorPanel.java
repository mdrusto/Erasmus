package erasmus.ui.infopanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import erasmus.ui.Colours;
import net.dv8tion.jda.core.entities.*;

public class TextChannelSelectorPanel extends JScrollPane {

	private static final long serialVersionUID = 3461070594233407645L;
	
	public HashMap<TextChannel, JButton> channels = new HashMap<TextChannel, JButton>();
	
	
	JPanel textChannelPanel = new JPanel();
	
	JButton lastButton;
	
	Dimension size = new Dimension(200, 800);
	
	public TextChannelSelectorPanel() {
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
		
		textChannelPanel.setBackground(Colours.BACKGROUND);
		
		
	}
	
	public void init(List<TextChannel> channels) {
		channels.forEach(this::addChannel);
	}
	
	public void addChannel(TextChannel channel) {
		TextChannelButton button = new TextChannelButton(channel);
		textChannelPanel.add(button);
		channels.put(channel, button);
		revalidate();
		repaint();
	}
	
	public void textChannelDeleted(TextChannel channel) {
		textChannelPanel.remove(channels.get(channel));
		channels.remove(channel);
		revalidate();
		repaint();
	}
	
	public void selectChannel(TextChannel channel) {
		JButton button = channels.get(channel);
		button.setEnabled(false);
		button.setBackground(Color.DARK_GRAY);
		if (lastButton != null) {
			lastButton.setEnabled(true);
			lastButton.setBackground(Color.GRAY);
		}
		((GuildViewPanel)getParent()).textChannelSelected(channel);
		
		lastButton = button;
	}
	
	private class TextChannelButton extends JButton {
		
		
		private static final long serialVersionUID = 64027815538733805L;
		
		private Dimension size = new Dimension(200, 60);
		
		private TextChannelButton(TextChannel channel) {
			
			setMinimumSize(size);
			setSize(size);
			setPreferredSize(size);
			setMaximumSize(size);
			
			setText(channel.getName());
			
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					((GuildViewPanel)TextChannelSelectorPanel.this.getParent()).textChannelSelected(channel);
				}
			});
		}
	}
}
