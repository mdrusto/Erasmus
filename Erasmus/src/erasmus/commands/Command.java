package erasmus.commands;

import java.util.ArrayList;

import erasmus.Main;
import net.dv8tion.jda.core.entities.Message;

public abstract class Command {
	public int minArgs;
	public int maxArgs;
	public String name = getClass().getSimpleName().toLowerCase();
	public String fullName = getClass().getName().toLowerCase().replace('$', ' ').substring(17);
	public String description;
	public ArrayList<String> aliases = new ArrayList<String>();
	public ArrayList<Command> subCommands = new ArrayList<Command>();
	public String usage = Main.PREFIX + fullName;
	public String getUsage() { return usage;}
	public String getName() { return name;}
	public String getFullName() { return fullName;}
	public String getDescription() { return description;}
	public ArrayList<String> getAliases() { return aliases;}
	public ArrayList<Command> getSubCommands() { return subCommands;}
	public boolean checkArgs (String[] args, Message message) {
		if (maxArgs != -1 && args.length > maxArgs) {
			message.getTextChannel().sendMessage(String.format("```css\nA maximum of %s arguments are permitted for the '$%s' command.\nUsage: %s```", maxArgs, fullName, getUsage())).queue();
			return false;
		}
		if (args.length < minArgs) {
			message.getTextChannel().sendMessage(String.format("```css\nA minimum of %s arguments are required for the '$%s' command.\nUsage: %s```", minArgs, fullName, getUsage())).queue();
			return false;
		}
		return true;
	}
	public abstract void called(String[] args, Message message);
	public void addAlias(String alias) { aliases.add(alias);}
}
