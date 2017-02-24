package erasmus;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import erasmus.commands.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
	
	public static final String PREFIX = "$";
	public static final ArrayList<Command> commands = new ArrayList<Command>();
	public static boolean isResponse = false;
	String[] args = new String[1];
	String content;
	public static String choice;
	
	public static Command currentCommand;
	public static String[] currentArgs;
	public static Message currentMessage;
	
	private static Help helpCommand = new Help();
	private static Ping pingCommand = new Ping();
	private static Shutdown shutdownCommand = new Shutdown();
	private static Type typeCommand = new Type();
	private static Settings settingsCommand = new Settings();
	private static Yes yesCommand = new Yes();
	private static No noCommand = new No();
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			JDA jda = new JDABuilder(AccountType.BOT)
					.setToken("MjgxNTQ3Njk3MjcyNTIwNzA0.C4d2mQ.LFQCDLsBGGloN4nWdkLbtc8jDUI")
					.addListener(new Main())
					.buildBlocking();
		}
		catch (RateLimitedException e) {e.printStackTrace();}
		catch (LoginException e) {e.printStackTrace();}
		catch (InterruptedException e) {e.printStackTrace();}
		
		commands.add(helpCommand);
		commands.add(pingCommand);
		commands.add(shutdownCommand);
		commands.add(typeCommand);
		commands.add(settingsCommand);
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		event.getJDA().getTextChannelById("281484833765588992").sendMessage("```Starting up```").queue();
		event.getJDA().getPresence().setGame(Game.of("'" + PREFIX + "help' for help"));
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		called(event.getMessage());
	}
	
	@SuppressWarnings("unused")
	public void called(Message message) {
		if (!message.getContent().startsWith("$")) return;
		
		Guild guild = message.getGuild();
		TextChannel textChannel = message.getTextChannel();
		User author = message.getAuthor();
		String name = author.getName();
		content = message.getContent().toLowerCase().substring(1);
				
		if (content.contains(" ")) {
			int length = 1;
			for (int x = 0; x < content.length() - 1; x++) {
				if (content.charAt(x) == ' ' && content.charAt(x + 1) != ' ') length++;
			}
			args = new String[length];
			args[0] = content.substring(0, content.indexOf(" "));
			String arguments = content.substring(content.indexOf(" ") + 1);
			for (int x = 1; !arguments.equals("") && !arguments.equals(" "); x++) {
				while (arguments.startsWith(" ")) arguments = arguments.substring(1);
				if (arguments.contains(" ")) args[x] = arguments.substring(0,  arguments.indexOf(' '));
				else {
					args[x] = arguments;
					break;
				}
				arguments = arguments.substring(arguments.indexOf(' ') + 1);
			}
		}
		else {
			args = new String[1];
			args[0] = content;
			
		}
		
		Command command = null;
		ArrayList<Command> currentList = commands;
		String[] newArgs = null;
		layersLoop: for (int d = 0; true; d++) {
			for (int x = 0; x < currentList.size(); x++) {
				try {
					if (args[d].equals(currentList.get(x).getName())) {
						command = currentList.get(x);
						currentList = command.getSubCommands();
						if (d > 0) {
							int arrayLength = args.length - d - 1;
							newArgs = new String[arrayLength];
							for (int g = 0; g < arrayLength; g++) {
								newArgs[g] = args[g + d + 1];
							}
						}
						else newArgs = new String[0];
						continue layersLoop;
					}
					for (int c = 0; c < currentList.get(x).getAliases().size(); c++) {
						if (args[d].equals(currentList.get(x).getAliases().get(c))) {
							command = currentList.get(x);
							currentList = command.getSubCommands();
							int arrayLength = args.length - d - 1;
							newArgs = new String[arrayLength];
							for (int g = 0; g < arrayLength; g++) {
								newArgs[g] = args[g + d + 1];
							}
							continue layersLoop;
						}
					}
				}
				catch (ArrayIndexOutOfBoundsException e) {
					break layersLoop;
				}
			}
			if (d > 0) {
				int arrayLength = args.length - d;
				newArgs = new String[arrayLength];
				for (int g = 0; g < arrayLength; g++) {
					newArgs[g] = args[g + d];
				}
			}
			break layersLoop;
		}
		if (command == null) {
			StringBuilder builder = new StringBuilder();
			for (int x = 0; x < args.length; x++) {
				builder.append(args[x]);
				if (x != args.length - 1) builder.append(" ");
			}
			textChannel.sendMessage("```The command '" + builder.toString() + "' was not recognized.```").queue();
		}
		else {
			boolean isStillResponse = isResponse;
			if (!command.equals(yesCommand) && !command.equals(noCommand)) {
				currentCommand = command;
				currentArgs = newArgs;
				currentMessage = message;
				choice = "";
			}
			try {
				command.called(newArgs, message);
			}
			catch (Exception e) {
				StringBuilder error = new StringBuilder("");
				for (StackTraceElement s: e.getStackTrace()) {
					error.append(s.toString() + "\n");
				}
				textChannel.sendMessage("```css\n" + error + "```").queue();
			}
			if (isResponse && isStillResponse) removeResponse();
		}
	}
	
	public static void addResponse() {
		commands.add(yesCommand);
		commands.add(noCommand);
		isResponse = true;
	}
	
	public static void removeResponse() {
		commands.remove(yesCommand);
		commands.remove(noCommand);
		isResponse = false;
	}
	
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		event.getGuild().getTextChannelById("281492686844854272").sendMessage("```Welcome " + event.getMember().getAsMention() + " to the server!```").queue();
	}
	
	public void onGuildMessageUpdate(GuildMessageUpdateEvent event) {
		called(event.getMessage());
	}
}