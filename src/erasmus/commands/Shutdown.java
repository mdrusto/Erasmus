package erasmus.commands;

import erasmus.ErasmusMain;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.managers.RoleManagerUpdatable;

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
		if (!checkArgs(args, textChannel, author)) return;
		ErasmusMain.listener.shutdown();
	}
}
