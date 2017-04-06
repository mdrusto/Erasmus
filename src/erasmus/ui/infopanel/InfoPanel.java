package erasmus.ui.infopanel;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

import erasmus.bot.Erasmus;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;

public class InfoPanel extends JPanel {
	
	private static final long serialVersionUID = 3445035251121393603L;
	
	public GuildSelectorPanel guildSelector;
	TextChannelSelectorPanel textChannelSelector;
	TextChannelPanel channelPanel;
	
	private Dimension size = new Dimension(1000, 800);
	
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
		
		setOpaque(false);
		textChannelSelector.setVisible(false);
	}
	
	public void start(JDA jda) {
		textChannelSelector.hideThis();
		channelPanel.hideThis();
		guildSelector.display(jda.getGuilds());
	}
	
	public void guildSelected(Guild guild) {
		textChannelSelector.setLocation(100, 0);
		textChannelSelector.display(guild);
		channelPanel.setLocation(300, 0);
		channelPanel.display(guild.getPublicChannel());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				channelPanel.channelScroll.getVerticalScrollBar().setValue(channelPanel.channelScroll.getVerticalScrollBar().getMaximum());
			}
		});
	}
	
	public void textChannelSelected(TextChannel channel) {
		textChannelSelector.setLocation(100, 0);
		channelPanel.setLocation(300, 0);
		
		channelPanel.display(channel);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				channelPanel.channelScroll.getVerticalScrollBar().setValue(channelPanel.channelScroll.getVerticalScrollBar().getMaximum());
			}
		});

	}
}
