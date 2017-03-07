package erasmus.commands;

import java.util.ArrayList;

import erasmus.Main;
import erasmus.MessageOutput;
import erasmus.properties.Values;
import net.dv8tion.jda.core.entities.TextChannel;

public class Help extends Command {
	
	public Help() {
		super();
		description = "Help with a specific command, or get a list of all commands.";
		usage += " [command]";
		minArgs = 0;
		maxArgs = -1;
	}
	
	@Override
	public void called(String[] args, TextChannel textChannel) {
		if (!checkArgs(args, textChannel)) return;

		String output = "";
		if (args.length == 0) {
			output += "=============== List of all my commands ===============\n\n";
			for (int x = 0; x < Main.commands.size(); x++) {
				Command command = Main.commands.get(x);
				output += "**" + Values.prefix + command.getName() + "**" + " - " + command.getDescription() + "\n";
			}
			output += "\n=======================================================";
		}
		else {
			ArrayList<Command> currentList = Main.commands;
			Command command = null;
			layersLoop: for (int d = 0; true; d++) {
				if (d >= args.length) break layersLoop;
				for (int x = 0; x < currentList.size(); x++) {
					if (args[d].equals(currentList.get(x).getName())) {
						command = currentList.get(x);
						currentList = command.getSubCommands();
						continue layersLoop;
					}
					for (int c = 0; c < currentList.get(x).getAliases().size(); c++) {
						if (args[d].equals(currentList.get(x).getAliases().get(c))) {
							command = currentList.get(x);
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
				output += "The command '" + builder.toString() + "' was not recognized.";
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
