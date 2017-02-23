package erasmus.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Type extends Command {
	public Type () {
		description = "Make Erasmus look like he's typing.";
		usage = "$" + name;
		minArgs = 0;
		maxArgs = 0;
	}
	
	@Override
	public void called (String[] args, MessageReceivedEvent event) {
		if (!checkArgs(args,  event)) return;

		event.getTextChannel().sendTyping().queue();
	}
}
