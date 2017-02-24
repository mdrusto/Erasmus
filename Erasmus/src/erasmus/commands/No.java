package erasmus.commands;

import erasmus.Main;
import net.dv8tion.jda.core.entities.Message;

public class No extends Command {

	public No() {
		description = "Just answer the question!";
		usage = Main.PREFIX + name;
		minArgs = 0;
		maxArgs = 0;
	}
	
	@Override
	public void called(String[] args, Message message) {
		if (!checkArgs(args, message)) return;
		Main.choice = "no";
		Main.currentCommand.called(Main.currentArgs, Main.currentMessage);
		Main.removeResponse();
	}

}
