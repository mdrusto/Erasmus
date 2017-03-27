package erasmus;



import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.role.RoleCreateEvent;
import net.dv8tion.jda.core.managers.RoleManager;
import net.dv8tion.jda.core.managers.RoleManagerUpdatable;
import net.dv8tion.jda.core.requests.Route;

public class RoleCreate {



	
	public static void createRole(User iD, String string, RoleManagerUpdatable r, RoleCreateEvent RE) {
	
		//RE.getRole().getName();
		
		
		
		RE.getGuild().getPublicChannel().sendMessage(RE.getRole().getName());
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
