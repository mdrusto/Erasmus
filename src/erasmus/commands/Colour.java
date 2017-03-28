package erasmus.commands;

import java.awt.Color;

import erasmus.MessageOutput;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.managers.RoleManager;

public class Colour extends Command {
	
	public Colour() {
		minArgs = 1;
		maxArgs = 1;
		addAlias("color");
	}

	@Override
	public void called(String[] args, TextChannel textChannel, User author) {
		if (!checkArgs(args, textChannel)) return;
 		Guild guild = textChannel.getGuild();
		Member member = guild.getMember(author);
		
		Role foundRole = null;
		
		Color newColor;

		try {
			newColor = new Color(
		            Integer.valueOf(args[0].substring( 0, 2 ), 16 ),
		            Integer.valueOf(args[0].substring( 2, 4 ), 16 ),
		            Integer.valueOf(args[0].substring( 4, 6 ), 16 ) );;
		}
		catch(NumberFormatException | StringIndexOutOfBoundsException e) {
			MessageOutput.normal("`%s` could not be converted to an integer", textChannel, args[0]);
			return;
		}
		
		for (Role role: member.getRoles()) {
			if (role.getName().equals(author.getId())) foundRole = role;
		}
		
		
		if (foundRole == null) {
			GuildController controller = new GuildController(textChannel.getGuild());
			Role newRole = controller.createRole()
			.setColor(newColor)
			.setName(author.getId())
			.complete();
			controller.addRolesToMember(member, newRole).queue();
		}
		else {
			RoleManager roleManager = new RoleManager(foundRole);
			roleManager.setColor(newColor).queue();
		}
		
		MessageOutput.normal("%s, your color has been changed.", textChannel, author.getAsMention());
	}

}
