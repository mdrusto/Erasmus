package erasmus.bot.commands;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.ImageInfo;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.MessageEmbedImpl;

public class Embed extends Command {
	
	public Embed() {
		minArgs = 1;
		maxArgs = 1;
	}
	
	@Override
	public void called(String[] args, TextChannel textChannel, User author) {
		if (!checkArgs(args, textChannel)) return;
		MessageEmbedImpl embed = new MessageEmbedImpl();
		if (args[0].equals("red")) embed.setColor(new Color(255, 0, 0));
		else if (args[0].equals("green")) embed.setColor(new Color(0, 255, 0));
		else if (args[0].equals("blue")) embed.setColor(new Color(0, 0, 255));
		embed.setFields(new ArrayList<MessageEmbed.Field>());
		embed.setDescription("Hi");
		textChannel.sendMessage(embed).queue();
	}

}
