package erasmus.commands;

import erasmus.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class No extends Command {

	public No() {
		description = "Just answer the question!";
		usage = Main.PREFIX + name;
		minArgs = 0;
		maxArgs = 0;
	}
	
	@Override
	public void called(String[] args, MessageReceivedEvent event) {
		if (!checkArgs(args,  event)) return;
		Main.choice = "no";
		Main.currentCommand.called(Main.currentArgs, Main.currentEvent);
		Main.removeResponse();
	}

}
