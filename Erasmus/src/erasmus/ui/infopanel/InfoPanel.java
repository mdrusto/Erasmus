package erasmus.ui.infopanel;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

import erasmus.ErasmusMain;
import net.dv8tion.jda.core.entities.*;

public class InfoPanel extends JPanel {
	
	private static final long serialVersionUID = 3445035251121393603L;
	
	public GuildSelectorPanel guildSelector;
	TextChannelSelectorPanel textChannelSelector;
	TextChannelPanel channelPanel;
	
	public InfoPanel() {
		//super();
		setMinimumSize(new Dimension(400, 420));
		setSize(400, 420);
		setMaximumSize(new Dimension(400, 420));
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		guildSelector = new GuildSelectorPanel(this);
		textChannelSelector = new TextChannelSelectorPanel(this);
		channelPanel = new TextChannelPanel();
		
		add(guildSelector);
		add(textChannelSelector);
		add(channelPanel);
		
		guildSelector.setLocation(0, 0);
		guildSelector.setVisible(true);
		
		
		
		setVisible(true);
	}
	public void guildSelected(Guild guild) {
		textChannelSelector.setLocation(200, 0);
		textChannelSelector.display(guild.getTextChannels());
	}
	
	public void textChannelSelected(TextChannel channel) {
		guildSelector.setVisible(false);
		textChannelSelector.setVisible(false);
		
		channelPanel.display(channel);
	}
}
