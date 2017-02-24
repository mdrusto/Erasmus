package erasmus.commands;

import erasmus.Main;
import net.dv8tion.jda.core.entities.Message;

public class Ping extends Command {
	
	public Ping () {
		description = "Ping!";
		usage = Main.PREFIX + name;
		addAlias("pong");
		addAlias("peng");
		minArgs = 0;
		maxArgs = 0;
	}

	@Override
	public void called(String[] args, Message message) {
		if (!checkArgs(args, message)) return;

		message.getTextChannel().sendMessage("Piiiiiiiiiiiiiiiiing").queue();
	}
	
}
