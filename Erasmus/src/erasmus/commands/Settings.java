package erasmus.commands;

import erasmus.Main;
import net.dv8tion.jda.core.entities.Message;

public class Settings extends Command {
	
	public Settings() {
		description = "Add, remove or change Erasmus' settings";
		usage = Main.PREFIX + name;
		minArgs = 0;
		maxArgs = 0;
		
		subCommands.add(new Reset());
		subCommands.add(new Set());
		subCommands.add(new Remove());
	}
	
	@Override
	public void called(String[] args, Message message) {
		if (!checkArgs(args, message)) return;
		
	}
	
	class Reset extends Command {
		
		Reset() {
			description = "Reset all Erasmus' settings";
			minArgs = 0;
			maxArgs = 0;
		}

		@Override
		public void called(String[] args, Message message) {
			if (!checkArgs(args, message)) return;
			if (!Main.isResponse) {
				Main.addResponse();
				message.getTextChannel().sendMessage("```Are you sure you want to reset the settings?\nType &yes or $no```").queue();
			}
			else {
				if (Main.choice.equals("yes")) message.getTextChannel().sendMessage("```Erasmus' settings have been reset.```").queue();
				else message.getTextChannel().sendMessage("```Erasmus' settings will not be reset.```").queue();
			}
		}
		
	}
	
	class Set extends Command {
		
		Set() {
			description = "Set one of Erasmus' settings";
			usage += " [setting] [value]";
			minArgs = 2;
			maxArgs = 2;
		}

		@Override
		public void called(String[] args, Message message) {
			if (!checkArgs(args, message)) return;
			message.getTextChannel().sendMessage(String.format("```'%s' has been set to '%s'.```", args[0], args[1])).queue();
		}
	}
	
	class Remove extends Command {
		
		Remove() {
			description = "Remove one of Erasmus' settings";
			usage += " [setting]";
			minArgs = 1;
			maxArgs = 1;
		}

		@Override
		public void called(String[] args, Message message) {
			if (!checkArgs(args, message)) return;
			if (!Main.isResponse) {
				Main.addResponse();
				message.getTextChannel().sendMessage("```Are you sure you want to remove this setting?\nType $yes or $no```").queue();
			}
			else {
				if (Main.choice.equals("yes")) message.getTextChannel().sendMessage("```'" + args[0] + "' has been removed.```").queue();
				else message.getTextChannel().sendMessage("```'" + args[0] + "' will not be removed.```").queue();
			}
		}
	}
}
