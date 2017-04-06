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
import net.dv8tion.jda.core.entities.*;

public class GuildSelectorPanel extends JScrollPane {
	
	private static final long serialVersionUID = -3688678325441465448L;
	
	private Map<JButton, Guild> guilds = new HashMap<JButton, Guild>();
	
	private InfoPanel container;
	
	private JPanel guildsPanel = new JPanel();
	
	private Dimension size;
	private Dimension buttonSize = new Dimension(80, 80);
	
	
	public GuildSelectorPanel(InfoPanel container) {
		this.container = container;
		setBorder(BorderFactory.createEmptyBorder());
				
		size = new Dimension(100, container.getSize().height);
		
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		guildsPanel.setLayout(new BoxLayout(guildsPanel, BoxLayout.Y_AXIS));
		
		
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		
		setViewportView(guildsPanel);
		
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
		
		//guildsPanel.setOpaque(false);
		//setOpaque(false);
		//this.getViewport().setOpaque(false);
		//this.setBorder(null);
		guildsPanel.setBackground(new Color(40, 40, 40));
		
	}
	
	public void display(List<Guild> guilds) {
		guildsPanel.removeAll();
		guildsPanel.revalidate();
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
			button.setPreferredSize(buttonSize);
			button.setSize(buttonSize);
			button.setMaximumSize(buttonSize);
			
			URLConnection conn;
			
			try {
				URL url = new URL(guild.getIconUrl());
				conn = url.openConnection();
				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
				
				BufferedImage image = ImageIO.read(conn.getInputStream());
				
				button.setIcon(new ImageIcon(image.getScaledInstance(image.getWidth() / 2, image.getHeight() / 2, Image.SCALE_DEFAULT)));
			}
			catch (IOException e) {
				throw new ErasmusException(e);
			}
			
			button.setFocusable(false);
			
			guildsPanel.add(button);
			
			button.setAlignmentX(CENTER_ALIGNMENT);
			
			button.setToolTipText(guild.getName());
			
			
			button.setVisible(true);
			
			button.setOpaque(false);
			button.setBorder(BorderFactory.createEmptyBorder());
			button.setBackground(new Color(40, 40, 40));
		}
		
		//guildsPanel.setOpaque(false);
		//setOpaque(false);
		
		//guildsPanel.setVisible(true);
		guildsPanel.repaint();
		//setVisible(true);
		
		//guildsPanel.setBackground(new Color(40, 40, 40));
		
	}

}
