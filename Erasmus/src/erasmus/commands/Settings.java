package erasmus.commands;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import erasmus.Main;
import erasmus.properties.ParseException;
import net.dv8tion.jda.core.entities.TextChannel;

public class Settings extends Command {
	
	public Settings() {
		description = "Add, remove or change Erasmus' settings";
		usage = Main.getPrefix() + name;
		minArgs = 1;
		maxArgs = 3;
		
		subCommands.add(new Reset());
		subCommands.add(new Set());
		subCommands.add(new Remove());
		subCommands.add(new Get());
		subCommands.add(new Save());
		
		usage += " [";
		for (int x = 0; x < subCommands.size(); x++) {
			if (x > 0) usage += "|";
			usage += subCommands.get(x).getName();
		}
		usage += "]";
	}
	
	@Override
	public void called(String[] args, TextChannel textChannel) {
		if (!checkArgs(args, textChannel)) return;
		
	}
	
	class Reset extends Command {
		
		Reset() {
			super();
			description = "Reset all Erasmus' settings";
			minArgs = 0;
			maxArgs = 0;
		}

		@Override
		public void called(String[] args, TextChannel textChannel) {
			if (!checkArgs(args, textChannel)) return;
			if (!Main.isResponse) {
				Main.addResponse();
				textChannel.sendMessage("```Are you sure you want to reset the settings?\nType &yes or $no```").queue();
			}
			else {
				if (Main.choice.equals("yes")) textChannel.sendMessage("```Erasmus' settings have been reset.```").queue();
				else textChannel.sendMessage("```Erasmus' settings will not be reset.```").queue();
			}
		}
		
	}
	
	class Set extends Command {
		
		Set() {
			super();
			ignoreCase = true;
			description = "Set one of Erasmus' settings. If the key doesn't exist it will be created";
			usage += " [key] [value]";
			minArgs = 2;
			maxArgs = 2;
		}

		@Override
		public void called(String[] args, TextChannel textChannel) {
			if (!checkArgs(args, textChannel)) return;
			if (!Main.getProperties().containsKey(args[0]))
				textChannel.sendMessage("```Key " + args[0] + " was not found```").queue();
			else {
				try {
					Main.setProperty(args[0], args[1]);
					Main.loadProperties();
					textChannel.sendMessage(String.format("```'%s' has been set to '%s'.```", args[0], args[1])).queue();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				catch (ParseException e) {
					textChannel.sendMessage("```The value '" + args[1] + "' could not be parsed```").queue();
				}
			}
		}
	}
	
	class Remove extends Command {
		
		Remove() {
			super();
			description = "Remove one of Erasmus' settings";
			usage += " [key]";
			minArgs = 1;
			maxArgs = 1;
		}

		@Override
		public void called(String[] args, TextChannel textChannel) {
			if (!checkArgs(args, textChannel)) return;
			if (!Main.isResponse) {
				Main.addResponse();
				textChannel.sendMessage("```Are you sure you want to remove this setting?\nType $yes or $no```").queue();
			}
			else {
				if (Main.choice.equals("yes")) {
					if (Main.getProperties().remove(args[0]) != null)
						textChannel.sendMessage("```'" + args[0] + "' has been removed.```").queue();
					else textChannel.sendMessage("```Key " + args[0] + " was not found```").queue();
				}
				else textChannel.sendMessage("```'" + args[0] + "' will not be removed.```").queue();
			}
		}
	}
	
	class Get extends Command {
		Get() {
			super();
			description = "List one or all of Erasmus' settings";
			usage += "<setting>";
			minArgs = 0;
			maxArgs = 1;
		}
		
		@Override
		@SuppressWarnings("rawtypes")
		public void called(String[] args, TextChannel textChannel) {
			if (!checkArgs(args, textChannel)) return;
			if (args.length == 0) {
				String output = "=============== List of all my settings ===============\n\n";
				Enumeration e = Main.getProperties().propertyNames();
				while (e.hasMoreElements()) {
					String key = (String)e.nextElement();
					output += key + ": " + Main.getProperties().getProperty(key) + "\n";
				}
				output += "\n=======================================================";
				textChannel.sendMessage("```" + output + "```").queue();
			}
			else {
				if (Main.getProperties().containsKey(args[0])) textChannel.sendMessage("```The value of '" + args[0] + "' is '" + Main.getProperties().getProperty(args[0]) + "'.```").queue();
				else textChannel.sendMessage("```The key '" + args[0] + "' was not found.").queue();
			}
		}
	}
	
	class Save extends Command {
		Save() {
			super();
			description = "Save Erasmus' settings from this session to the settings file";
			minArgs = 0;
			maxArgs = 0;
		}
		
		@Override
		public void called(String[] args, TextChannel textChannel) {
			if (!checkArgs(args, textChannel)) return;
			try {
				FileOutputStream out = new FileOutputStream("config.properties");
				Main.getProperties().store(out,  "---No Comment---");
				textChannel.sendMessage("```Erasmus' settings have been saved.```").queue();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
