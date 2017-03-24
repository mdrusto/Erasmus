package erasmus;

import java.lang.reflect.InvocationTargetException;

import javax.security.auth.login.LoginException;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import erasmus.ui.ErasmusWindow;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class Erasmus {
	
	private static Status status;
	
	public static ErasmusListener listener;
	private static ErasmusWindow gui;
	private static JDA jda;
	
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					gui = new ErasmusWindow();
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
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
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
				return null;
			}
			
			@Override
			public void done() {
				status = Status.ONLINE;
				gui.statusPanel.setStatus(Status.ONLINE, true);
				gui.loadGuilds();
			}
		};
		worker.execute();
		
	}
	
	public static void stop() {
		gui.statusPanel.setStatus(Status.OFFLINE, false);
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
				jda.shutdown(false);
				return null;
			}
			@Override
			public void done() {
				status = Status.OFFLINE;
				gui.statusPanel.setStatus(Status.OFFLINE, true);
			}
		};
		worker.execute();
	}
	
	public static JDA getJDA() {
		return jda;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public static enum Status {
		
		ERROR(-1),
		
		OFFLINE(0),
		
		ONLINE(1);
		
		int id;

		Status(int n) {
			this.id = n;
		}
		
		public int getID() {
			return id;
		}
	}
}
