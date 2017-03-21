package erasmus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import erasmus.commands.*;
import erasmus.properties.ConfigLoader;
import erasmus.properties.Values;
import erasmus.ui.ErasmusWindow;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;
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
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ErasmusListener extends ListenerAdapter {
	
	public static final ArrayList<Command> commands = new ArrayList<Command>();
	
	public static boolean isResponse = false;
	
	public static String choice;
	
	private ErasmusWindow gui;
	
	public static Command currentCommand;
	public static String[] currentArgs;
	public static TextChannel currentTextChannel;
		
	public JDA jda;
	public User user;
	private Guild guild;
	private MessageChannel infoChannel;
	
	public ErasmusListener(ErasmusWindow gui) {
		this.gui = gui;
	}
	
	public void setJDA(JDA jda) {
		this.jda = jda;
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		jda = event.getJDA();
		ConfigLoader.loadProperties(Values.class);
		


		guild = jda.getGuildById(Values.guildID);
		
		user = jda.getUserById(Values.userID);

		if (!user.hasPrivateChannel()) user.openPrivateChannel();
		infoChannel = user.getPrivateChannel();
		

		
		MessageOutput.setInfoChannel(infoChannel);
		
		infoChannel = guild.getTextChannelById(Values.infoTextChannelID);
		if (infoChannel == null) MessageOutput.error("Could not find info channel with id **%s**", Values.infoTextChannelID);
		MessageOutput.setInfoChannel(infoChannel);
		
		jda.getPresence().setGame(Game.of("'" + Values.prefix + "help' for help"));
		jda.getPresence().setStatus(OnlineStatus.ONLINE);

		
		Reflections r = new Reflections("erasmus.commands");
		
		Set<Class<? extends Command>> classes = r.getSubTypesOf(Command.class);
				
		try {
			for (Class<? extends Command> clazz: classes) {
				if (!clazz.getSimpleName().equals("Yes") && !clazz.getSimpleName().equals("No") && !clazz.isMemberClass()) commands.add(clazz.newInstance());
			}
		}
		catch (IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
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
		List<Command> currentList = commands;
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
				
		if (finalCommand == null) MessageOutput.normal("The command **%s** was not recognized.", textChannel, content);
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
			
			if (!finalCommand.getName().equals("yes") && !finalCommand.getName().equals("no")) {
				currentCommand = finalCommand;
				currentArgs = newArgs;
				currentTextChannel = textChannel;
				choice = "";
			}
			finalCommand.called(newArgs, textChannel);
		}
	}

	public static void addResponse() {
		Reflections reflections = new Reflections("erasmus");
		Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);

		try {
			for (Class<? extends Command> clazz: classes) {
				if (clazz.getSimpleName().equals("Yes") || clazz.getSimpleName().equals("No")) commands.add(clazz.newInstance());
			}
		}
		catch (IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
		isResponse = true;
	}
	
	public static void removeResponse() {
		for (Command command: commands) {
			if (command.getName().equals("yes") || command.getName().equals("no")) commands.remove(command);
		}
		isResponse = false;
	}
	
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		event.getGuild().getTextChannelById("281492686844854272").sendMessage("```Welcome " + event.getMember().getAsMention() + " to the server!```").queue();
	}
	
	public void onGuildMessageUpdate(GuildMessageUpdateEvent event) {
		if (Values.readEdits) commandCalled(event.getMessage());
	}

	public void shutdown() {
		//MessageOutput.info("Shutting down");
		ConfigLoader.saveProperties();
		jda.getPresence().setStatus(OnlineStatus.OFFLINE);
		jda.shutdownNow(false);
	}
}