package erasmus.commands;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import erasmus.ErasmusMain;
import erasmus.MessageOutput;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;

public class GetFromID extends Command {
	
	public GetFromID() {
		description = "Get info about an entity from an id";
		minArgs = 2;
		maxArgs = 2;
	}

	@Override
	public void called(String[] args, TextChannel textChannel) {
		if (!checkArgs(args, textChannel)) return;
		
		JDA jda = textChannel.getJDA();
		
		Guild guild = textChannel.getGuild();
		
		ArrayList<String> details = new ArrayList<String>();
		
		DateTimeFormatter format = DateTimeFormatter.BASIC_ISO_DATE;

		if (args[0].equalsIgnoreCase("message")) {
			Message message = null;
			for (TextChannel channel: guild.getTextChannels()) {
				try {
					message = channel.getMessageById(args[1]).block();
				}
				catch (Throwable e) {
					e.printStackTrace();
				}
			}
			if (message == null) MessageOutput.normal("No message was found with ID **%s** in this guild.", textChannel, args[1]);
			else {
				details.add("**Creation time**: `" + message.getCreationTime().format(format) + "`");
				details.add("**Content*: `" + message.getContent() + "`");
				details.add("**Author**: `" + message.getAuthor().getName() + "`");
				if (!message.getContent().equals(message.getRawContent())) details.add("**Raw content**: " + message.getRawContent());
				if (!message.getContent().equals(message.getStrippedContent())) details.add("**Stripped content: **" + message.getStrippedContent());
				details.add("**Channel name**: `" + message.getChannel().getName() + "`");
				details.add("**Is TTS**: `" + message.isTTS() + "`");
				details.add("Mentions everyone: " + message.mentionsEveryone());
				details.add(message.getCreationTime().format(format));
			}
		}
		else if (args[0].equalsIgnoreCase("user")) {
			User user = ErasmusMain.jda.getUserById(args[1]);
			if (user == null) MessageOutput.normal("No user found with id **%s**", textChannel, args[1]);
			else {
			
				details.add("User details");
				
				details.add("Name: " + user.getName());
				if (true) {
					Member member = guild.getMember(user);
					details.add("Nickname: " + member.getNickname());
					details.add("Is playing: " + member.getGame().getName());
					details.add("Join date: " + member.getJoinDate());
					details.add("Online status: " + member.getOnlineStatus().toString());
					details.add(("In voice channel: " + member.getVoiceState().getChannel().getName()).replace("null", "false"));
					details.add("Is deafened: " + member.getVoiceState().isDeafened());
					details.add("Is guild-deafened: " + member.getVoiceState().isGuildDeafened());
					details.add("Is muted: " + member.getVoiceState().isMuted());
					details.add("Is guild-muted: " + member.getVoiceState().isGuildMuted());
					details.add("Is suppressed: " + member.getVoiceState().isSuppressed());
				}
				details.add("Creation time: " + user.getCreationTime().format(format));
				details.add("Is bot: " + user.isBot());
				details.add("Discriminator: " + user.getDiscriminator());
				details.add("Avatar URL: " + user.getAvatarUrl());
				details.add("Has private channel: " + user.hasPrivateChannel());
			}
		}
		
		else if (args[0].equalsIgnoreCase("textchannel")) {
			TextChannel channel = guild.getTextChannelById(args[1]);
			if (channel == null) MessageOutput.normal("No text channel was found with ID **%s** in this guild.", textChannel, args[1]);
			else {
				details.add("Name: " + channel.getName());
				details.add("Creation time: " + channel.getCreationTime());
				details.add("Position: " + channel.getPosition());
				details.add("Raw position: " + channel.getPositionRaw());
				details.add("Topic: " + channel.getTopic());
				details.add("Can talk: " + channel.canTalk());
			}
		}
		else if (args[0].equalsIgnoreCase("privatechannel")) {
			PrivateChannel channel = jda.getPrivateChannelById(args[1]);
			if (channel == null) MessageOutput.normal("No private channel was found with ID **%s**.", textChannel, args[1]);
			else {
				details.add("Name: " + channel.getName());
				details.add("Creation time: " + channel.getCreationTime().format(format));
				details.add("User: " + channel.getUser().getAsMention());
			}
		}
		else if (args[0].equalsIgnoreCase("guild")){
			Guild newGuild = jda.getGuildById(args[0]);
			if (newGuild == null) MessageOutput.normal("No guild was found with ID **%s**.", textChannel, args[1]);
			else {
				details.add("Name: " + newGuild.getName());
				details.add("Creation time: " + newGuild.getCreationTime().format(format));
				details.add("Owner: " + newGuild.getOwner().getAsMention());
				details.add("Icon URL: " + newGuild.getIconUrl());
				details.add("AFK Channel: " + newGuild.getAfkChannel().getName());
				details.add("AFK Timeout: " + newGuild.getAfkTimeout().getSeconds() + " seconds");
				details.add("Default channel: " + newGuild.getPublicChannel().getAsMention());
				details.add("Default role: " + newGuild.getPublicRole().getName());
			}
		}
		else if (args[0].equalsIgnoreCase("role")) {
			Role role = guild.getRoleById(args[1]);
			if (role == null) MessageOutput.normal("No role was found with ID **%s** in this guild.", textChannel, args[1]);
			else {
				details.add("Name: " + role.getName());
				details.add("ID: " + role.getId());
				details.add("Position: " + role.getPosition());
				details.add("Raw position: " + role.getPositionRaw());
				details.add("Color: " + "R: " + role.getColor().getRed() + " G: " + role.getColor().getGreen() + " B: " + role.getColor().getBlue());
				details.add("Is hoisted: " + role.isHoisted());
				details.add("Is mentionable: " + role.isMentionable());
				details.add("Is managed: " + role.isManaged());
			}
		}
		
		String content = "";
		
		for (String detail: details) {
			content += "\n" + detail;
		}
		
		
		MessageOutput.normal(content, textChannel);
	}

}
