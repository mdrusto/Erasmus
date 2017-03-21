package erasmus;

import erasmus.properties.Values;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;

public class MessageOutput {
	
	private static MessageChannel infoChannel;
	private static TextChannel announcementsChannel;
	
	public static void setInfoChannel (MessageChannel infoChannel) {
		MessageOutput.infoChannel = infoChannel;
	}
	
	public static void setAnnouncementsChannel (TextChannel announcementsChannel) {
		MessageOutput.announcementsChannel = announcementsChannel;
	}
	
	public static void normal (String message, TextChannel channel, String... args) {
		channel.sendMessage(String.format(Values.messageFormat, String.format(message, (Object[])args))).queue();
	}
	
	public static void info (String message, String... args) {
		
		if (infoChannel != null) infoChannel.sendMessage(String.format(Values.messageFormat, String.format(message, (Object[])args))).queue();
		else System.out.println(message);
	}
	
	public static void error (String message, String... args) {
		try {
			infoChannel.sendMessage(String.format(Values.messageFormat, "Error: " + message)).queue();
		}
		catch (NullPointerException e) {
			System.out.println(message);
		}
		//ErasmusListener.shutdown();
	}
	
	public static void announce (String message) {
		announcementsChannel.sendMessage(String.format(Values.messageFormat, message));
	}
}
