package erasmus.commands;

import net.dv8tion.jda.core.entities.TextChannel;

public class GetFromID extends Command {
	
	public GetFromID() {
		minArgs = 2;
		maxArgs = 2;
	}

	@Override
	public void called(String[] args, TextChannel textChannel) {
		if (!checkArgs(args, textChannel)) return;
		
	}

}
