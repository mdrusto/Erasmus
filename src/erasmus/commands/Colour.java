package erasmus.commands;



import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.managers.RoleManager;
import net.dv8tion.jda.core.managers.RoleManagerUpdatable;
import net.dv8tion.jda.core.requests.Route;
import net.dv8tion.jda.core.requests.Route.Roles;


public class Colour extends Command {
	
	public String RGB;
	public Colour(){
		super();
		description="Change the colour of a users name";
		
		minArgs=0;
		maxArgs=0;
		
		
	}

	@Override
	public void called(String[] args, TextChannel textChannel, User author) {
		if (!checkArgs(args, textChannel, author )) return;		

		RoleManager roleManager=null;
		
		GuildController guildController = new GuildController(textChannel.getGuild());
		
		for(Role role: textChannel.getGuild().getMember(author).getRoles()) if(role.getName().equals(author.getId()))roleManager= new RoleManager(role);
		
		if(roleManager!=null){
			
			
			try {
				guildController.createRole().block();
			} catch (RateLimitedException e) {
				
				e.printStackTrace();
			}
		
			
			
			
			
			
		}
		
		
		
		
		
		
		
	
		
	}


}

