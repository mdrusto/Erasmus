package erasmus;

import java.lang.reflect.InvocationTargetException;

import javax.security.auth.login.LoginException;
import javax.swing.SwingUtilities;

import erasmus.ui.ErasmusWindow;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class ErasmusMain {
	
	
	public static ErasmusListener listener;
	private static ErasmusWindow gui;
	public static JDA jda;
	
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					gui = new ErasmusWindow();
				}
			});
		}
		catch (InvocationTargetException | InterruptedException e) {
			
		}
		listener = new ErasmusListener(gui);
		
		try {
			jda = new JDABuilder(AccountType.BOT)
					.setToken("MjgxNTQ3Njk3MjcyNTIwNzA0.C4d2mQ.LFQCDLsBGGloN4nWdkLbtc8jDUI")
					.addListener(listener)
					.setEventManager(new ErasmusEventManager())
					.buildBlocking();
		}
		catch (RateLimitedException | LoginException | InterruptedException e) {
			e.printStackTrace();
		}
		
		listener.setJDA(jda);
	}
}
