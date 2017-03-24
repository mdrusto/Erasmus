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
	
	private Dimension size;
	private Dimension buttonSize = new Dimension(200, 50);
	
	
	public GuildSelectorPanel(InfoPanel container) {
		this.container = container;
		
		guildsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		guildsPanel.setVisible(true);
		
		size = new Dimension(container.getSize().width / 4, container.getSize().height);
		
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		guildsPanel.setLayout(new BoxLayout(guildsPanel, BoxLayout.Y_AXIS));
		
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
						
			button.setMinimumSize(buttonSize);
			button.setSize(buttonSize);
			button.setMaximumSize(buttonSize);
			
			button.setText(guild.getName());
			
			button.setFocusable(false);
			
			guildsPanel.add(button);
			
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
