package erasmus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import erasmus.commands.*;
import erasmus.properties.ParseException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
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
	
	private static Properties properties = new Properties();
	
	private static boolean readEdits;
	private static int updateSettingsInterval;
	private static int updateStatsInterval;
	private static String guildId;
	private static TextChannel infoTextChannel;
	private static TextChannel announcementsTextChannel;
	private static User user;
	private static String prefix;
	
	private static JDA jda;
	private static Guild guild;
	private static MessageChannel outputChannel;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			JDA jda = new JDABuilder(AccountType.BOT)
					.setToken("MjgxNTQ3Njk3MjcyNTIwNzA0.C4d2mQ.LFQCDLsBGGloN4nWdkLbtc8jDUI")
					.addListener(new Main())
					.buildBlocking();
		}
		catch (RateLimitedException | LoginException | InterruptedException e) {e.printStackTrace();}
	}
	
	public static TextChannel getInfoTextChannel() {
		return infoTextChannel;
	}
	
	public static TextChannel getAnnouncementsTextChannel() {
		return announcementsTextChannel;
	}
	
	public static String getPrefix() {
		return prefix;
	}
	
	public static Properties getProperties() {
		return properties;
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		jda = event.getJDA();

		try {
			FileInputStream in = new FileInputStream("config.properties");
			properties.load(in);
			loadProperties();
		}
		catch (FileNotFoundException e) {
			String userId = "142046468151312384";
			user = jda.getUserById(userId);
			properties.setProperty("userId", userId);
			if (!user.hasPrivateChannel()) user.openPrivateChannel().queue();
			outputChannel = user.getPrivateChannel();
			
			updateSettingsInterval = 10;
			properties.setProperty("updateSettingsInterval", String.valueOf(updateSettingsInterval));
			
			updateStatsInterval = 10;
			properties.setProperty("updateStatsInterval", String.valueOf(updateStatsInterval));
			
			guildId = "225743704533630986";
			properties.setProperty("guildId", guildId);
			guild = jda.getGuildById(guildId);
			
			String infoTextChannelId = "281484833765588992";
			infoTextChannel = guild.getTextChannelById(infoTextChannelId);
			properties.setProperty("infoTextChannelId", infoTextChannelId);
			
			String announcementsTextChannelId = "281492686844854272";
			announcementsTextChannel = guild.getTextChannelById(announcementsTextChannelId);
			properties.setProperty("announcementsTextChannelId", announcementsTextChannelId);
			
			readEdits = false;
			properties.setProperty("readEdits", String.valueOf(readEdits));
			
			prefix = "$";
			properties.setProperty("prefix", prefix);
			
			
			
			File file = new File("config.properties");
			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(file);
			}
			catch(FileNotFoundException error) {
				error(e);
			}
			try {
				properties.store(fileOut, "Properties");
			}
			catch (IOException error) {
				error(e);
			}
		}
		catch (IOException e) {
			error(e);
		}
		catch (ParseException e) {
			error(ErrorType.COULDNOTPARSE);
		}
		
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
		
		infoTextChannel.sendMessage("```Started up successfully```").queue();
		event.getJDA().getPresence().setGame(Game.of("'" + prefix + "help' for help"));
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (!event.getMessage().getContent().startsWith(prefix)) return;
		commandCalled(event.getMessage());
	}
	
	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
	
	@SuppressWarnings("unused")
	public void commandCalled(Message message) {
		Guild guild = message.getGuild();
		TextChannel textChannel = message.getTextChannel();
		User author = message.getAuthor();
		String name = author.getName();
		String content = message.getContent().substring(prefix.length());
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
			try {
				finalCommand.called(newArgs, textChannel);
			}
			catch (Exception e) {
				error(e);
			}
			if (isResponse && isStillResponse) removeResponse();
		}
	}
	
	private static void error(ErrorType type, String... args) {
		outputChannel.sendMessage("```Error: " + String.format(type.getMessage(), (Object[])args) + "```").queue();
		jda.shutdown();
	}
	
	private static void error(Exception e) {
		StringBuilder error = new StringBuilder("");
		for (StackTraceElement s: e.getStackTrace()) {
			error.append(s.toString() + "\n");
		}
		error(ErrorType.OTHER, error.toString());
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
		if (readEdits) commandCalled(event.getMessage());
	}
	
	public static void loadProperties() throws FileNotFoundException, IOException, NumberFormatException, ParseException {
		String userId = properties.getProperty("userId");
		user = jda.getUserById(userId);
		PrivateChannel test_privateChannel = user.getPrivateChannel();
		if (test_privateChannel == null) {
			System.out.println("Error: No user found with id " + userId);
			jda.shutdown();
		}
		outputChannel = test_privateChannel;
		
		int test_updateSettingsInterval = 0;
		try {
			test_updateSettingsInterval = Integer.parseInt(properties.getProperty("updateSettingsInterval"));
		}
		catch (NumberFormatException e) {
			throw new ParseException("int", "updateSettingsInterval", properties.getProperty("updateSettingsInterval"));
		}
		updateSettingsInterval = test_updateSettingsInterval;
		
		int test_updateStatsInterval = 0;
		try {
			test_updateStatsInterval = Integer.parseInt(properties.getProperty("updateStatsInterval"));
		}
		catch (NumberFormatException e) {
			throw new ParseException("int", "updateStatsInterval", properties.getProperty("updateStatsInterval"));
		}
		updateStatsInterval = test_updateStatsInterval;
		
		guildId = properties.getProperty("guildId");
		Guild test_guild = jda.getGuildById(guildId);
		if (test_guild == null) error(ErrorType.GUILDNOTFOUND);
		guild = test_guild;
		
		TextChannel test_infoTextChannel = guild.getTextChannelById(properties.getProperty("infoTextChannelId"));
		if (test_infoTextChannel == null) error(ErrorType.TEXTCHANNELNOTFOUND);
		infoTextChannel = test_infoTextChannel;
		outputChannel = test_infoTextChannel;
		
		TextChannel test_announcementsTextChannel = guild.getTextChannelById(properties.getProperty("announcementsTextChannelId"));
		if (test_announcementsTextChannel == null) error(ErrorType.TEXTCHANNELNOTFOUND);
		announcementsTextChannel = test_announcementsTextChannel;
		
		String test_readEdits = properties.getProperty("readEdits");
		if (!test_readEdits.equalsIgnoreCase("true") && !test_readEdits.equalsIgnoreCase("false"))
			throw new ParseException("boolean", "readEdits", test_readEdits);
		readEdits = Boolean.parseBoolean(test_readEdits);
		
		prefix = properties.getProperty("prefix");
	}
	
	public static void shutdown() {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("config.properties");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			properties.store(out, "---No Comment---");
			out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		infoTextChannel.sendMessage("```Shutting down```").queue();
		
		jda.shutdownNow(false);
	}
}