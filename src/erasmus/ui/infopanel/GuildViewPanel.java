package erasmus.ui.infopanel;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class GuildViewPanel extends JPanel {
	
	private static final long serialVersionUID = 8592305740116559621L;
	
	private Guild guild;
	
	public TextChannelSelectorPanel textChannelSelector = new TextChannelSelectorPanel();
	
	private Dimension size = new Dimension(900, 800);
	
	private transient Map<TextChannel, TextChannelPanel> textChannels = new HashMap<TextChannel, TextChannelPanel>();
	private TextChannelPanel lastPanel;
	
	public GuildViewPanel(Guild guild) {
		setLayout(null);
		setOpaque(false);
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		add(textChannelSelector);
		textChannelSelector.setLocation(0, 0);
		this.guild = guild;
		guild.getTextChannels().forEach((channel) -> {
			TextChannelPanel panel = new TextChannelPanel(channel);
			textChannels.put(channel, panel);
			add(panel);
			panel.setLocation(200, 0);
			panel.setVisible(false);
		});
		textChannelSelector.init(guild.getTextChannels());
	}
	
	public Guild getGuild() {
		return guild;
	}
	
	public TextChannelPanel getChannelView(TextChannel channel) {
		return textChannels.get(channel);
	}
	
	public void textChannelSelected(TextChannel channel) {
		if (lastPanel != null) lastPanel.setVisible(false);
		textChannels.get(channel).setVisible(true);
		lastPanel = textChannels.get(channel);
	}
	
	public void textChannelCreated(TextChannel channel) {
		textChannels.put(channel, new TextChannelPanel(channel));
		textChannelSelector.addChannel(channel);
	}
	
	public void textChannelDeleted(TextChannel channel) {
		textChannels.remove(channel);
	}
}
