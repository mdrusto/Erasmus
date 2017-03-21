package erasmus.commands;

import net.dv8tion.jda.core.entities.TextChannel;

public class Type extends Command {
	public Type () {
		super();
		description = "Make Erasmus look like he's typing.";
		minArgs = 0;
		maxArgs = 0;
	}
	
	@Override
	public void called (String[] args, TextChannel textChannel) {
		if (!checkArgs(args, textChannel)) return;
		textChannel.sendTyping().queue();
	}
}
