package erasmus.bot;

import javax.security.auth.login.LoginException;

import erasmus.bot.properties.ConfigLoader;
import erasmus.ui.ErasmusWindow;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class ErasmusBot {
	
	private JDA jda;
	private ErasmusListener listener = new ErasmusListener();
	
	private Status status = Status.OFFLINE;
	
	public synchronized void start(ErasmusWindow gui) throws RateLimitedException, InterruptedException, LoginException {
		jda = new JDABuilder(AccountType.BOT)
				.setToken("MjgxNTQ3Njk3MjcyNTIwNzA0.C4d2mQ.LFQCDLsBGGloN4nWdkLbtc8jDUI")
				.addListener(listener)
				.setEventManager(gui.new UIEventManager())
				.buildBlocking();
		
		status = Status.ONLINE;
	}
	
	public synchronized void stop() {
		if (jda == null) return;
		ConfigLoader.saveProperties();
		jda.getPresence().setStatus(OnlineStatus.OFFLINE);
		jda.shutdown(false);
		jda = null;
		status = Status.OFFLINE;
	}
	
	public static enum Status {
		OFFLINE,
		LOADING,
		ONLINE;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public JDA getJDA() {
		return jda;
	}
	
	public ErasmusListener getListener() {
		return listener;
	}
}
