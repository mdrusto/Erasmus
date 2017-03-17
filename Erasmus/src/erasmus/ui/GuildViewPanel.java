package erasmus.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.*;

import net.dv8tion.jda.core.entities.*;

public class GuildViewPanel extends JScrollPane {

	private static final long serialVersionUID = 3461070594233407645L;
	
	private HashMap<JButton, TextChannel> map = new HashMap<JButton, TextChannel>();
	
	public GuildViewPanel(Guild guild) {
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		
		List<TextChannel> channels = guild.getTextChannels();
		for (TextChannel channel: channels) {
			map.put(new JButton(channel.getName()), channel);
		}
		
		for (Entry<JButton, TextChannel> entry: map.entrySet()) {
			entry.getKey().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					
				}
			});
		}
	}
	
}
