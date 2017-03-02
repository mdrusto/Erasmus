package erasmus.commands;

import erasmus.Main;
import net.dv8tion.jda.core.entities.TextChannel;

public class No extends Command {

	public No() {
		super();
		description = "Just answer the question!";
		minArgs = 0;
		maxArgs = 0;
	}
	
	@Override
	public void called(String[] args, TextChannel textChannel) {
		if (!checkArgs(args, textChannel)) return;
		Main.choice = "no";
		Main.currentCommand.called(Main.currentArgs, Main.currentTextChannel);
		Main.removeResponse();
	}

}
