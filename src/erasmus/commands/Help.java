package erasmus.commands;

import java.util.List;

import erasmus.ErasmusListener;
import erasmus.MessageOutput;
import erasmus.properties.Values;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.managers.RoleManagerUpdatable;

public class Help extends Command {
	
	public Help() {
		super();
		description = "Help with a specific command, or get a list of all commands.";
		usage += " [command]";
		minArgs = 0;
		maxArgs = -1;
	}
	
	@Override
	public void called(String[] args, TextChannel textChannel, User author) {
		if (!checkArgs(args, textChannel, author)) return;

		String output = "";
		if (args.length == 0) {
			output += "**=============== List of all my commands ===============**\n\n";
			for (int x = 0; x < ErasmusListener.commands.size(); x++) {
				Command command = ErasmusListener.commands.get(x);
				output += "**" + Values.prefix + command.getName() + "**" + " - " + command.getDescription() + "\n";
			}
			output += "\n**================================================**";
		}
		else {
			List<Command> currentList = ErasmusListener.commands;
			Command command = null;
			layersLoop: for (int d = 0; true; d++) {
				if (d >= args.length) break layersLoop;
				for (Command cmd: currentList) {
					if (args[d].equals(cmd.getName())) {
						command = cmd;
						currentList = command.getSubCommands();
						continue layersLoop;
					}
					for (String alias: cmd.getAliases()) {
						if (args[d].equals(alias)) {
							command = cmd;
							currentList = command.getSubCommands();
							continue layersLoop;
						}
					}
				}
				command = null;
				break layersLoop;
			}
			if (command == null) {
				StringBuilder builder = new StringBuilder();
				for (int x = 0; x < args.length; x++) {
					builder.append(args[x]);
					if (x != args.length - 1) builder.append(" ");
				}
				output += "The command **" + builder.toString() + "** was not recognized.";
			}
			else {
				output += "**Command**: " + Values.prefix + command.getFullName() + "  |  **Aliases**: ";
				for (int x = 0; x < command.getAliases().size(); x++) {
					output += "'" + command.getAliases().get(x) + "'";
					if (x != command.getAliases().size() - 1) output += ", ";
				}
				if (command.getAliases().isEmpty()) output += "None";
				output += "\n------------------------------------------------\n";
				output += "**Description**: " + command.getDescription() + "\n"; 
				output += "**Usage**: " + command.getUsage() + "\n";
				output += "**Minimum arguments**: " + command.minArgs + "\n";
				if (command.maxArgs != -1)
					output += "**Maximum arguments**: " + command.maxArgs;
				else output += "**Maximum arguments**: no limit";
			}
		}
		MessageOutput.normal(output, textChannel);
	}

}
