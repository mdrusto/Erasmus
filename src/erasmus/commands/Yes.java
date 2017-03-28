package erasmus.commands;

import erasmus.ErasmusListener;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class Yes extends Command {
	
	public Yes() {
		super();
		description = "Just answer the question!";
		minArgs = 0;
		maxArgs = 0;
	}

	@Override
	public void called(String[] args, TextChannel textChannel, User author) {
		if (!checkArgs(args, textChannel)) return;
		ErasmusListener.choice = "yes";
		ErasmusListener.currentCommand.called(ErasmusListener.currentArgs, ErasmusListener.currentTextChannel, ErasmusListener.currentAuthor);
		ErasmusListener.removeResponse();
	}

}
