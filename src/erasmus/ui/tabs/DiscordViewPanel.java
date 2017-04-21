package erasmus.ui.tabs;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import erasmus.ui.infopanel.GuildSelectorPanel;
import erasmus.ui.infopanel.GuildViewPanel;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;

public class DiscordViewPanel extends JPanel {
	
	private static final long serialVersionUID = 8592305740116559621L;
	
	public GuildSelectorPanel guildSelector = new GuildSelectorPanel();
	
	private Map<Guild, GuildViewPanel> guilds = new HashMap<Guild, GuildViewPanel>();
	private GuildViewPanel lastPanel = null;
	
	private Dimension size = new Dimension(1000, 800);
	
	public DiscordViewPanel() {
		setMinimumSize(size);
		setPreferredSize(size);
		setSize(size);
		setMaximumSize(size);
		
		setLayout(null);
				
		add(guildSelector);
		
		guildSelector.setLocation(0, 0);
		guildSelector.setVisible(true);
		
		
		setOpaque(false);
		
		setBorder(BorderFactory.createEmptyBorder());
	}
	
	
	public GuildViewPanel getGuildView(Guild guild) {
		return guilds.get(guild);
	}
	
	public void init(JDA jda) {
		jda.getGuilds().forEach((guild) -> {
			GuildViewPanel panel = new GuildViewPanel(guild);
			guilds.put(guild, panel);
			add(panel);
			panel.setLocation(100, 0);
			panel.setVisible(false);
		});
		guildSelector.init(jda.getGuilds());
	}
	
	public void guildSelected(Guild guild) {
		if (lastPanel != null) lastPanel.setVisible(false);
		guilds.get(guild).setVisible(true);
		lastPanel = guilds.get(guild);
	}
	
	public void addGuild(Guild guild) {
		guilds.put(guild, new GuildViewPanel(guild));
		guildSelector.addGuild(guild);
	}
	public void removeGuild(Guild guild) {
		guilds.remove(guild);
		guildSelector.removeGuild(guild);
	}
	
}
