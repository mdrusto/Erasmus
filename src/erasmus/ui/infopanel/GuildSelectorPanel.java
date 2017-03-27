package erasmus.ui.infopanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import net.dv8tion.jda.core.entities.*;

public class GuildSelectorPanel extends JScrollPane {
	
	private static final long serialVersionUID = -3688678325441465448L;
	
	private Map<JButton, Guild> guilds = new HashMap<JButton, Guild>();
	
	private InfoPanel container;
	
	private JPanel guildsPanel = new JPanel();

	
	public GuildSelectorPanel(InfoPanel container) {
		this.container = container;
		
		Dimension d = new Dimension(200, 420);
		
		guildsPanel.setMinimumSize(d);
		guildsPanel.setSize(d);
		guildsPanel.setMaximumSize(d);
		guildsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		guildsPanel.setVisible(true);
		
		setMinimumSize(d);
		setSize(d);
		setMaximumSize(d);
		
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		
		setViewportView(guildsPanel);
	}
	
	public void display(List<Guild> guilds) {
		int placing = 0;
		for (Guild guild: guilds) {
			JButton button = new JButton();
			this.guilds.put(button, guild);
			
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					container.guildSelected(guild);
				}
			});
			
			Dimension d = new Dimension(200, 50);
			
			button.setMinimumSize(d);
			button.setSize(d);
			button.setMaximumSize(d);
			
			button.setText(guild.getName());
			
			guildsPanel.add(button);
			button.setLocation(0, 50 * placing);
			
			button.setVisible(true);
			placing++;
		}
		
		guildsPanel.setVisible(true);
		guildsPanel.repaint();
		setVisible(true);
		
	}
	public void hideThis() {
		removeAll();
		setVisible(false);
	}
}
