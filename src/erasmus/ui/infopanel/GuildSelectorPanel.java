package erasmus.ui.infopanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import erasmus.bot.ErasmusException;
import erasmus.ui.Loadable;
import erasmus.ui.tabs.DiscordViewPanel;
import net.dv8tion.jda.core.entities.*;

public class GuildSelectorPanel extends JScrollPane {
	
	private static final long serialVersionUID = -3688678325441465448L;
	
	private Map<Guild, JButton> guilds = new HashMap<Guild, JButton>();
	
	
	private JPanel guildsPanel = new JPanel();
	
	private Dimension size = new Dimension(100, 800);
	private Dimension buttonSize = new Dimension(80, 80);
	
	
	public GuildSelectorPanel() {
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		guildsPanel.setLayout(new BoxLayout(guildsPanel, BoxLayout.Y_AXIS));
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		setViewportView(guildsPanel);
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
		guildsPanel.setBackground(new Color(40, 40, 40));
		
	}
	
	public void init(List<Guild> guilds) {
		guilds.forEach(this::addGuild);
	}
	
	public void addGuild(Guild guild) {
		GuildButton button = new GuildButton(guild);
		guildsPanel.add(button);
		guilds.put(guild, button);
		revalidate();
		repaint();
	}
	
	public void removeGuild(Guild guild) {
		guildsPanel.remove(guilds.get(guild));
		guilds.remove(guild);
		revalidate();
		repaint();
	}
	
	private class GuildButton extends JButton implements Loadable {
		
		private static final long serialVersionUID = 8931413215011839464L;
		
		private GuildButton(Guild guild) {
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					((DiscordViewPanel)GuildSelectorPanel.this.getParent()).guildSelected(guild);
				}
			});			
			setMinimumSize(buttonSize);
			setPreferredSize(buttonSize);
			setSize(buttonSize);
			setMaximumSize(buttonSize);
			
			URLConnection conn;
			try {
				URL url = new URL(guild.getIconUrl());
				conn = url.openConnection();
				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
				
				BufferedImage image = ImageIO.read(conn.getInputStream());
				
				setIcon(new ImageIcon(image.getScaledInstance(image.getWidth() / 2, image.getHeight() / 2, Image.SCALE_SMOOTH)));
			}
			catch (IOException e) {
				throw new ErasmusException(e);
			}
			setFocusable(false);
			setAlignmentX(CENTER_ALIGNMENT);
			setToolTipText(guild.getName());
			setVisible(true);
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder());
			setBackground(new Color(40, 40, 40));
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					((DiscordViewPanel)GuildSelectorPanel.this.getParent()).guildSelected(guild);
				}
			});
		}
		
		@Override
		public void startLoading() {
			
		}
		
		@Override
		public void finishLoading() {
			
		}
	}
}
