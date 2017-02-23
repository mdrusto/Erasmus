package erasmus.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Shutdown extends Command {
	
	public Shutdown () {
		description = "Shut Erasmus down.";
		addAlias("turnoff");
		minArgs = 0;
		maxArgs = 0;
	}
	@Override
	public void called(String[] args, MessageReceivedEvent event) {
		if (!checkArgs(args,  event)) return;
		event.getJDA().getTextChannelById("281484833765588992").sendMessage("```Shutting down```").queue();
		event.getJDA().shutdown(true);
	}
}
