package erasmus.commands;

import net.dv8tion.jda.core.entities.TextChannel;

public class Ping extends Command {
	
	public Ping () {
		super();
		description = "Ping!";
		addAlias("pong");
		addAlias("peng");
		minArgs = 0;
		maxArgs = 0;
	}

	@Override
	public void called(String[] args, TextChannel textChannel) {
		if (!checkArgs(args, textChannel)) return;

		textChannel.sendMessage("Piiiiiiiiiiiiiiiiing").queue();
	}
	
}
