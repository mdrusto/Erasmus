package erasmus.commands;

import erasmus.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

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
	public void called(String[] args, MessageReceivedEvent event) {
		if (!checkArgs(args,  event)) return;

		event.getTextChannel().sendMessage("Piiiiiiiiiiiiiiiiing").queue();
	}
	
}
