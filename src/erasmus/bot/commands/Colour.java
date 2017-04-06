package erasmus.bot.commands;

import java.awt.Color;

import erasmus.bot.MessageOutput;
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
		maxArgs = 3;
		addAlias("color");
	}

	@Override
	public void called(String[] args, TextChannel textChannel, User author) {
		if (!checkArgs(args, textChannel)) return;
 		Guild guild = textChannel.getGuild();
		Member member = guild.getMember(author);
		
		Role foundRole = null;
		
		Color newColor = null;
		String attemptedString = null;
		
		if (args.length == 1) {
			//Try from colour names
			switch(args[0].toLowerCase()) {
			case "white": newColor = Color.WHITE;
			break;
			case "light_gray":
			case "light_grey": newColor = Color.LIGHT_GRAY;
			break;
			case "gray":
			case "grey": newColor = Color.GRAY;
			break;
			case "dark_gray":
			case "dark_grey": newColor = Color.DARK_GRAY;
			break;
			case "black": newColor = new Color(1, 1, 1);
			break;
			case "red": newColor = Color.RED;
			break;
			case "pink": newColor = Color.PINK;
			break;
			case "orange": newColor = Color.ORANGE;
			break;
			case "yellow": newColor = Color.YELLOW;
			break;
			case "green": newColor = Color.GREEN;
			break;
			case "magenta": newColor = Color.MAGENTA;
			break;
			case "cyan": newColor = Color.CYAN;
			break;
			case "blue": newColor = Color.BLUE;
			}
		
		
			//try from hexadecimal
		
			try {
				newColor = new Color(
						Integer.valueOf(args[0].substring(0, 2), 16),
						Integer.valueOf(args[0].substring(2, 4), 16),
						Integer.valueOf(args[0].substring(4, 6), 16));;
			}
			catch(NumberFormatException | StringIndexOutOfBoundsException e) {
				attemptedString = "`" + args[0] + "` is not a decimal value or name of a color";
			}
		}
		
		//Try from RGB
		else if (args.length == 3) {
			int[] intargs = new int[3];
			for (int x = 0; x < 3; x++) {
				try {
					intargs[x] = Integer.parseInt(args[x]);
				}
				catch(NumberFormatException e) {
					attemptedString = "`" + args[x] + "` could not be converted to an integer";
					break;
				}
			}
			newColor = new Color(intargs[0], intargs[1], intargs[2]);
		}
		else {
			MessageOutput.normal("When choosing a color, either\n**-** enter a color name (e.g. \"red\")\n**-** enter a hexadecimal code (without the # sign)\n**-** enter RGB values (e.g. \"255 255 0\")", textChannel);
			return;
		}
		
		if (newColor == null) {
			MessageOutput.normal(attemptedString, textChannel);
			return;
		}
		
		for (Role role: member.getRoles()) {
			if (role.getName().equals(author.getId())) foundRole = role;
		}
		
		
		
		if (foundRole == null) {
			GuildController controller = new GuildController(guild);
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
