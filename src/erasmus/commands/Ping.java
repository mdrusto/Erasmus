package erasmus.commands;

import erasmus.MessageOutput;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.managers.RoleManager;
import net.dv8tion.jda.core.managers.RoleManagerUpdatable;

public class Ping extends Command {
	
	public Ping () {
		super();
		description = "Ping!";
		addAlias("pong");
		addAlias("peng");
		minArgs = 0;
		maxArgs = 0;
	}

	@Override
	public void called(String[] args, TextChannel textChannel, User author) {
		if (!checkArgs(args, textChannel, author)) return;

		MessageOutput.normal("Ping", textChannel);
	}


		
	}
	
