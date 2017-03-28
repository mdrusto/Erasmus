package erasmus.commands;

import erasmus.Erasmus;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class Shutdown extends Command {
	
	public Shutdown () {
		super();
		description = "Shut Erasmus down.";
		addAlias("turnoff");
		minArgs = 0;
		maxArgs = 0;
	}
	@Override
	public void called(String[] args, TextChannel textChannel, User author) {
		if (!checkArgs(args, textChannel)) return;
		Erasmus.listener.shutdown();
	}
}
