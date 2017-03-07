package erasmus.commands;

import java.util.Enumeration;
import java.util.Properties;

import erasmus.Main;
import erasmus.MessageOutput;
import erasmus.properties.ConfigLoader;
import erasmus.properties.ParseException;
import erasmus.properties.Values;
import net.dv8tion.jda.core.entities.TextChannel;

public class Settings extends Command {
	
	public Settings() {
		description = "Get, set, save or reset Erasmus' settings";
		minArgs = 1;
		maxArgs = 3;
		
		subCommands.add(new Reset());
		subCommands.add(new Set());
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
				MessageOutput.normal("Are you sure you want to reset the settings?\nType **%syes** or **%sno**", textChannel, Values.prefix, Values.prefix);
			}
			else {
				if (Main.choice.equals("yes")) {
					ConfigLoader.setProperties(ConfigLoader.getDefaults());
					MessageOutput.normal("Erasmus' settings have been reset.", textChannel);
				}
				else MessageOutput.normal("Erasmus' settings will not be reset.", textChannel);
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
			try {
				ConfigLoader.setProperty(args[0], args[1], textChannel);
				MessageOutput.normal("The key **%s** has been set to **%s**", textChannel, args[0], args[1]);
			}
			catch (NoSuchFieldException e) {
				MessageOutput.normal("The key %s was not found.", textChannel, args[0]);
			}
			catch (ParseException e) {
				MessageOutput.normal("The following value could not be parsed:\nKey: %s\nValue: %s\nVariable type: %s", textChannel, args[0], args[1], e.variableType);
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
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
			
			Properties props = ConfigLoader.getProperties();
			if (args.length == 0) {
				String output = "=============== List of all my settings ===============\n\n";
				Enumeration e = props.propertyNames();
				while (e.hasMoreElements()) {
					String key = (String)e.nextElement();
					output += key + ": " + props.getProperty(key) + "\n";
				}
				output += "\n=======================================================";
				MessageOutput.normal(output, textChannel);
			}
			else {
				if (props.containsKey(args[0])) MessageOutput.normal(String.format("The value of **%s** is '**%s**'.", args[0], props.getProperty(args[0])), textChannel);
				else MessageOutput.normal("The key %s was not found.", textChannel, args[0]);
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
			ConfigLoader.saveProperties();
			MessageOutput.normal("Erasmus' settings have been saved.", textChannel);
		}
	}
}
