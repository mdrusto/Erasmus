package erasmus;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import erasmus.commands.*;
import erasmus.properties.ConfigLoader;
import erasmus.properties.Values;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
	
	public static final ArrayList<Command> commands = new ArrayList<Command>();
	public static boolean isResponse = false;
	
	public static String choice;
	
	public static Command currentCommand;
	public static String[] currentArgs;
	public static TextChannel currentTextChannel;
	
	private static Help helpCommand;
	private static Ping pingCommand;
	private static Shutdown shutdownCommand;
	private static Type typeCommand;
	private static Settings settingsCommand;
	private static Yes yesCommand;
	private static No noCommand;
		
	public static JDA jda;
	public static User user;
	private static Guild guild;
	private static MessageChannel infoChannel;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			JDA jda = new JDABuilder(AccountType.BOT)
					.setToken("MjgxNTQ3Njk3MjcyNTIwNzA0.C4d2mQ.LFQCDLsBGGloN4nWdkLbtc8jDUI")
					.addListener(new Main())
					.setGame(Game.of("'" + Values.prefix + "help' for help"))
					.buildBlocking();
		}
		catch (RateLimitedException | LoginException | InterruptedException e) {e.printStackTrace();}
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		jda = event.getJDA();
		
		ConfigLoader.loadProperties(Values.class);
		
		user = jda.getUserById(Values.userID);
		if (!user.hasPrivateChannel()) user.openPrivateChannel();
		infoChannel = user.getPrivateChannel();
		
		MessageOutput.setInfoChannel(infoChannel);
		
		infoChannel = guild.getTextChannelById(Values.infoTextChannelID);
		if (infoChannel == null) MessageOutput.error("");
		
		helpCommand = new Help();
		pingCommand = new Ping();
		shutdownCommand = new Shutdown();
		typeCommand = new Type();
		settingsCommand = new Settings();
		
		commands.add(helpCommand);
		commands.add(pingCommand);
		commands.add(shutdownCommand);
		commands.add(typeCommand);
		commands.add(settingsCommand);
		
		MessageOutput.info("Started up successfully");
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (!event.getMessage().getContent().startsWith(Values.prefix)) return;
		commandCalled(event.getMessage());
	}

	@SuppressWarnings("unused")
	public void commandCalled(Message message) {
		Guild guild = message.getGuild();
		TextChannel textChannel = message.getTextChannel();
		User author = message.getAuthor();
		String name = author.getName();
		String content = message.getContent().substring(Values.prefix.length());
		String[] args;
		
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
		
		Command finalCommand = null;
		ArrayList<Command> currentList = commands;
		String[] newArgs = null;
		int index = 0;
		layersLoop: for (int d = 0; true; d++) {
			for (Command command: currentList) {
				try {
					if (args[d].equalsIgnoreCase(command.getName())) {
						finalCommand = command;
						currentList = command.getSubCommands();
						index = d;
						continue layersLoop;
					}
					for (int c = 0; c < command.getAliases().size(); c++) {
						if (args[d].equalsIgnoreCase(command.getAliases().get(c))) {
							finalCommand = command;
							currentList = command.getSubCommands();
							index = d;
							continue layersLoop;
						}
					}
				}
				catch (ArrayIndexOutOfBoundsException e) {
					break layersLoop;
				}
			}
			break layersLoop;
		}
				
		if (finalCommand == null) textChannel.sendMessage("```The command '" + content + "' was not recognized.```").queue();
		else {
			int arrayLength = args.length - index - 1;
			newArgs = new String[arrayLength];
			if (finalCommand.ignoreCase) {
				for (int g = 0; g < arrayLength; g++) {
					newArgs[g] = args[g + index + 1];
				}
			}
			else {
				for (int g = 0; g < arrayLength; g++) {
					newArgs[g] = args[g + index + 1].toLowerCase();
				}
			}
			
			boolean isStillResponse = isResponse;
			
			if (!finalCommand.equals(yesCommand) && !finalCommand.equals(noCommand)) {
				currentCommand = finalCommand;
				currentArgs = newArgs;
				currentTextChannel = textChannel;
				choice = "";
			}
			finalCommand.called(newArgs, textChannel);
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
		if (Values.readEdits) commandCalled(event.getMessage());
	}

	public static void shutdown() {
		ConfigLoader.saveProperties();
		
		MessageOutput.info("Shutting down");
		
		jda.shutdownNow(false);
	}
}