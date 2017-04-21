package erasmus.bot;

import java.lang.reflect.InvocationTargetException;

import javax.security.auth.login.LoginException;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import erasmus.bot.ErasmusBot.Status;
import erasmus.ui.ErasmusWindow;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class Erasmus {
		
	public static ErasmusWindow gui;
	public static ErasmusBot bot = new ErasmusBot();
	private static ErasmusWindow.UIEventListener uiEventListener;
	
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (bot.getStatus() == Status.ONLINE) {
					bot.getJDA().removeEventListener(uiEventListener);
					
				}
			}
		});
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
		Thread.setDefaultUncaughtExceptionHandler(gui.new UIUncaughtExceptionHandler());

		uiEventListener = gui.new UIEventListener();
		
		start();
	}
	
	public static void start() {
		gui.rightSidePanel.statusPanel.setStatus(Status.LOADING);
		new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
				try {
					bot.start(gui);
				}
				catch (RateLimitedException | LoginException | InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			public void done() {
				bot.getJDA().addEventListener(uiEventListener);
				gui.init(bot.getJDA());
				
				gui.rightSidePanel.statusPanel.setStatus(Status.ONLINE);
			}
		}.execute();
		
	}
	
	public static void stop() {
		new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
				bot.stop();
				return null;
			}
			@Override
			public void done() {
				gui.rightSidePanel.statusPanel.setStatus(Status.OFFLINE);
			}
		}.execute();
	}
	
	public static ErasmusBot getBot() {
		return bot;
	}
}
