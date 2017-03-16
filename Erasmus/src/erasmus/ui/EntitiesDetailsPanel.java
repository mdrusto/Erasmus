package erasmus.ui;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JPanel;
import net.dv8tion.jda.core.entities.*;

public class EntitiesDetailsPanel extends JPanel {
		
	private boolean inServer = false;

	private static final long serialVersionUID = 8881086496966596033L;
	
	public void setVisible(ISnowflake entity) {
		ArrayList<String> details = new ArrayList<String>();
		
		if (entity instanceof Message) {
			Message message = (Message) entity;
			details.add("ID: " + message.getId());
			details.add("Content" + message.getContent());
			details.add("Author name: " + message.getAuthor().getName());
			details.add("Raw content: " + message.getRawContent());
			details.add("Stripped content: " + message.getStrippedContent());
			details.add("Channel name" + message.getChannel().getName());
			details.add("Is TTS: " + message.isTTS());
			details.add("Mentions everyone: " + message.mentionsEveryone());
			details.add(message.getCreationTime().format(DateTimeFormatter.BASIC_ISO_DATE));
		}
		else if (entity instanceof User) {
			User user = (User) entity;
			details.add("Name: " + user.getName());
			details.add(user.getId());
			details.add("Avatar ID: " + user.getAvatarId());
			details.add("Avatar URL: " + user.getAvatarUrl());
			details.add("Discriminator: " + user.getDiscriminator());
			details.add(user.getCreationTime().format(DateTimeFormatter.BASIC_ISO_DATE));
			details.add("Has private channel: " + user.hasPrivateChannel());
		}
		else if (entity instanceof MessageChannel) {
			
		}
		
		super.setVisible(true);
	}
	
}
