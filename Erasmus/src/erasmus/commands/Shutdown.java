package erasmus.commands;

import net.dv8tion.jda.core.entities.Message;

public class Shutdown extends Command {
	
	public Shutdown () {
		description = "Shut Erasmus down.";
		addAlias("turnoff");
		minArgs = 0;
		maxArgs = 0;
	}
	@Override
	public void called(String[] args, Message message) {
		if (!checkArgs(args, message)) return;
		message.getJDA().getTextChannelById("281484833765588992").sendMessage("```Shutting down```").queue();
		message.getJDA().shutdown(true);
	}
}
