package erasmus.commands;

import java.util.ArrayList;

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
	public ArrayList<String> aliases = new ArrayList<String>();
	public ArrayList<Command> subCommands = new ArrayList<Command>();
	public String usage = Values.prefix + fullName;
	public String getUsage() { return usage;}
	public String getName() { return name;}
	public String getFullName() { return fullName;}
	public String getDescription() { return description;}
	public ArrayList<String> getAliases() { return aliases;}
	public ArrayList<Command> getSubCommands() { return subCommands;}
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
	public void addAlias(String alias) { aliases.add(alias);}
}
