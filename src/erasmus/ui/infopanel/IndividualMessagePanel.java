package erasmus.ui.infopanel;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import erasmus.bot.ErasmusException;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class IndividualMessagePanel extends JPanel {
	
	private static final long serialVersionUID = 6935897330723885326L;
	
	public int numLines = 0;
	String textString = "";
	public int height = 0;
	
	public IndividualMessagePanel(List<Message> messages) {
		User author = messages.get(0).getAuthor();
		Guild guild = messages.get(0).getGuild();
		TextChannel channel = messages.get(0).getTextChannel();
		
		JLabel picture = new JLabel();
		JLabel text = new JLabel();
		JButton name = new JButton();
		
		setLayout(null);
		this.setOpaque(false);
		
		add(picture);
		add(name);
		
		URLConnection conn;
		
		if (author.getAvatarUrl() != null) {
			try {
				URL url = new URL(author.getAvatarUrl());
				conn = url.openConnection();
				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
				
				BufferedImage image = ImageIO.read(conn.getInputStream());
				
				picture.setIcon(new ImageIcon(image.getScaledInstance(image.getWidth() / 3, image.getHeight() / 3, Image.SCALE_DEFAULT)));
			}
			catch (IOException e) {
				throw new ErasmusException(e);
			}
		}
		
		picture.setSize(40, 40);
		picture.setLocation(15, 15);
		
		FontMetrics metrics = name.getFontMetrics(name.getFont());
		
		String nameText = author.getName();
		name.setText(nameText);
		name.setSize(metrics.stringWidth(nameText) + 20, 20);
		name.setLocation(70, 10);
		name.setMargin(new Insets(0, 0, 0, 0));
		name.setOpaque(false);
		name.setBackground(new Color(40, 40, 40));
		name.setBorder(BorderFactory.createEmptyBorder());
		if (guild.getMember(author) != null) name.setForeground(guild.getMember(author).getColor());
		name.setHorizontalTextPosition(0);
		name.setHorizontalAlignment(SwingConstants.LEFT);
		name.setFocusable(false);
		
		FontMetrics textMetrics = text.getFontMetrics(text.getFont());
		
		
		for (Message message: messages) {
			numLines += (int)Math.ceil((double)textMetrics.stringWidth(message.getContent()) / 600.0);
			textString += message.getContent() + "<br>";
		}
		
		
		
		
		
			
		add(text);
			
		text.setForeground(new Color(180, 180, 180));
			
		text.setLocation(70, 30);
		
		text.setBorder(BorderFactory.createEmptyBorder());
			
		text.setVerticalAlignment(JLabel.TOP);
			
		text.setAlignmentX(SwingConstants.TOP);
		
		
		text.setText("<html>" + textString + "</html>");
		if (numLines == 0) numLines = 1;
		text.setSize(600, (textMetrics.getHeight() + 3) * numLines);
		
		if (numLines == 1) {
			this.setSize(600, 70);
		}
		else {
			this.setSize(600, text.getHeight() + 25);
		}
	}
}
