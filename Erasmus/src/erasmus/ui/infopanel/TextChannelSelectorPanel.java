package erasmus.ui.infopanel;

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
		
		setSize(210, 400);
		
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
			button.setSize(100, 50);
			button.setText(channel.getName());
			
			textChannelPanel.add(button);
			button.setLocation(0, 50 * placing);
			
			button.setVisible(true);
			placing++;
		}
	}
}
