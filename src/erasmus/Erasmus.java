package erasmus;

import java.lang.reflect.InvocationTargetException;

import javax.security.auth.login.LoginException;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import erasmus.ui.ErasmusUI;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import erasmus.ErasmusBot.Status;

public class Erasmus {
		
	public static ErasmusListener listener;
	private static ErasmusUI gui;
	private static JDA jda;
	private static ErasmusBot bot = new ErasmusBot();
	
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					gui = new ErasmusUI();
				}
			});
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch(InvocationTargetException e) {
			e.getTargetException().printStackTrace();
		}
		listener = new ErasmusListener(gui);
		
		start();
	}
	
	public static void start() {
		gui.statusPanel.setStatus(Status.ONLINE, false);
		new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
				try {
					bot.start();
				}
				catch (RateLimitedException | LoginException | InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			public void done() {
				gui.statusPanel.setStatus(Status.ONLINE, true);
				gui.loadGuilds();
			}
		}.execute();
		
	}
	
	public static void stop() {
		gui.statusPanel.setStatus(Status.OFFLINE, false);
		new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
				bot.stop();
				return null;
			}
			@Override
			public void done() {
				gui.statusPanel.setStatus(Status.OFFLINE, true);
			}
		}.execute();
	}
	
	public static JDA getJDA() {
		return jda;
	}


}
