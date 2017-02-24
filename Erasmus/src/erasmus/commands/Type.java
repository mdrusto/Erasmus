package erasmus.commands;

import net.dv8tion.jda.core.entities.Message;

public class Type extends Command {
	public Type () {
		description = "Make Erasmus look like he's typing.";
		usage = "$" + name;
		minArgs = 0;
		maxArgs = 0;
	}
	
	@Override
	public void called (String[] args, Message message) {
		if (!checkArgs(args, message)) return;

		message.getTextChannel().sendTyping().queue();
	}
}
