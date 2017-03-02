package erasmus.commands;

import erasmus.Main;
import net.dv8tion.jda.core.entities.TextChannel;

public class Shutdown extends Command {
	
	public Shutdown () {
		super();
		description = "Shut Erasmus down.";
		addAlias("turnoff");
		minArgs = 0;
		maxArgs = 0;
	}
	@Override
	public void called(String[] args, TextChannel textChannel) {
		if (!checkArgs(args, textChannel)) return;
		Main.shutdown();
	}
}
