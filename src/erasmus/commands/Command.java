package erasmus.commands;

import java.util.ArrayList;
import java.util.List;

import erasmus.MessageOutput;
import erasmus.properties.Values;
import net.dv8tion.jda.core.entities.TextChannel;

public abstract class Command {
	public boolean ignoreCase = true;
	public int minArgs;
	public int maxArgs;
	public String name = getClass().getSimpleName().toLowerCase();
	public String fullName = getClass().getName().toLowerCase().replace('$', ' ').substring(17);
	public String description;
	public List<String> aliases = new ArrayList<String>();
	public List<Command> subCommands = new ArrayList<Command>();
	public String usage = Values.prefix + fullName;
	
	public Command() {
		Class<?>[] classes = this.getClass().getClasses();
		try {
			for (Class<?> clazz: classes) {
				if (clazz.getSuperclass().equals(Command.class))
					subCommands.add((Command)clazz.newInstance());
			}
		}
		catch (IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	public String getUsage() {
		return usage;
		}
	public String getName() {
		return name;
		}
	public String getFullName() {
		return fullName;
		}
	public String getDescription() {
		return description;
		}
	public List<String> getAliases() {
		return aliases;
		}
	public List<Command> getSubCommands() {
		return subCommands;
		}
	public boolean checkArgs (String[] args, TextChannel textChannel) {
		if (maxArgs != -1 && args.length > maxArgs) {
			MessageOutput.normal("A maximum of **%s** arguments are permitted for the **$%s** command.\nUsage: **%s**", textChannel, String.valueOf(maxArgs), getFullName(), getUsage());
			return false;
		}
		if (args.length < minArgs) {
			MessageOutput.normal("A minimum of **%s** arguments are permitted for the **$%s** command.\nUsage: **%s**", textChannel, String.valueOf(minArgs), getFullName(), getUsage());
			return false;
		}
		return true;
	}
	public abstract void called(String[] args, TextChannel textChannel);
	public void addAlias(String alias) {
		aliases.add(alias);
		}
}
