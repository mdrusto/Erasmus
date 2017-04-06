package erasmus.bot.commands;

import java.util.ArrayList;
import java.util.List;

import erasmus.bot.MessageOutput;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.managers.RoleManager;

public class CorrectRoles extends Command {

	@Override
	public void called(String[] args, TextChannel textChannel, User author) {
		Guild guild = textChannel.getGuild();
		List<Role> foundRoles = new ArrayList<Role>();
		
		for (Member member: guild.getMembers()) {
			String id = member.getUser().getId();
			List<Role> roles = member.getRoles();
			
			if (roles.size() == 0) {
				GuildController controller = new GuildController(guild);
				Role newRole = controller.createRole()
						.setName(id)
						.complete();
				controller.addRolesToMember(member, newRole).queue();
				MessageOutput.normal("User %s has no roles, creating one", textChannel, member.getUser().getName());
				continue;
			}
			
			for (Role role: roles) {
				String name = role.getName();
				if (	!name.equals("Admin") && 
						!name.equals("Mod") &&
						!name.equals("Nightbot") &&
						!name.equals("MuxyBot") &&
						!name.equals("BOT") &&
						!role.equals(guild.getPublicRole()) &&
						!name.equals(id)) {
					if (foundRoles.contains(role)) {
						GuildController controller = new GuildController(guild);
						Role newRole = controller.createCopyOfRole(role)
						.setName(id)
						.complete();
						controller.removeRolesFromMember(member, role).queue();
						controller.addRolesToMember(member, newRole).queue();
						MessageOutput.normal("Duplicate, creating new role for %s", textChannel, member.getUser().getName());
					}
					else {
						RoleManager manager = new RoleManager(role);
						String oldName = role.getName();
						String newName = id;
						manager.setName(newName).queue();
						foundRoles.add(role);
						MessageOutput.normal("Changing %s's role name from %s to %s", textChannel, member.getUser().getName(), oldName, newName);
					}
				}
			}
		}
	}

}
