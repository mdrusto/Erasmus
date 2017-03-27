package erasmus;

import javax.security.auth.login.LoginException;

import erasmus.properties.ConfigLoader;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class ErasmusBot {
	
	private JDA jda;
	private ErasmusListener listener;
	
	private Status status = Status.OFFLINE;
	
	public void start() throws RateLimitedException, InterruptedException, LoginException {
		jda = new JDABuilder(AccountType.BOT)
				.setToken("MjgxNTQ3Njk3MjcyNTIwNzA0.C4d2mQ.LFQCDLsBGGloN4nWdkLbtc8jDUI")
				.addListener(listener)
				.setEventManager(new ErasmusEventManager())
				.buildBlocking();
		status = Status.ONLINE;
	}
	
	public void stop() {
		ConfigLoader.saveProperties();
		jda.getPresence().setStatus(OnlineStatus.OFFLINE);
		jda.shutdown(false);
		jda = null;
		status = Status.OFFLINE;
	}
	
	public static enum Status {
		OFFLINE,
		ERROR,
		ONLINE;
	}
	
	public void error() {
		status = Status.ERROR;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public JDA getJDA() {
		return jda;
	}
	
}
