package erasmus.ui.infopanel;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

import erasmus.Erasmus;
import net.dv8tion.jda.core.entities.*;

public class InfoPanel extends JPanel {
	
	private static final long serialVersionUID = 3445035251121393603L;
	
	public GuildSelectorPanel guildSelector;
	TextChannelSelectorPanel textChannelSelector;
	TextChannelPanel channelPanel;
	
	private Dimension size = new Dimension(800, 800);
	
	public InfoPanel() {
		setMinimumSize(size);
		setPreferredSize(size);
		setSize(size);
		setMaximumSize(size);
		
		setLayout(null);
		
		guildSelector = new GuildSelectorPanel(this);
		textChannelSelector = new TextChannelSelectorPanel(this);
		channelPanel = new TextChannelPanel();
		
		add(guildSelector);
		add(textChannelSelector);
		add(channelPanel);
		
		guildSelector.setLocation(0, 0);
		guildSelector.setVisible(true);
		
		channelPanel.setVisible(false);
		
		setVisible(true);
	}
	public void guildSelected(Guild guild) {
		textChannelSelector.setLocation(200, 0);
		textChannelSelector.display(guild);
	}
	
	public void textChannelSelected(TextChannel channel) {
		guildSelector.hideThis();
		textChannelSelector.hideThis();
		
		channelPanel.display(channel);
	}
}
