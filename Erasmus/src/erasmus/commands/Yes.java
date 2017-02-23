package erasmus.commands;

import erasmus.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Yes extends Command {
	
	public Yes() {
		description = "Just answer the question!";
		usage = Main.PREFIX + name;
		minArgs = 0;
		maxArgs = 0;
	}

	@Override
	public void called(String[] args, MessageReceivedEvent event) {
		if (!checkArgs(args,  event)) return;
		Main.choice = "yes";
		Main.currentCommand.called(Main.currentArgs, Main.currentEvent);
		Main.removeResponse();
	}

}
