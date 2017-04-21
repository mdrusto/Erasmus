package erasmus.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

import erasmus.ui.tabs.InfoPanel;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.IEventManager;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ErasmusWindow extends JFrame {

	private static final long serialVersionUID = 2334807590779291894L;
		
	private InfoPanel infoPanel = new InfoPanel();
	public RightSidePanel rightSidePanel = new RightSidePanel();
	
	
	
	public ErasmusWindow() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(infoPanel)
				.addComponent(rightSidePanel));
		
		layout.setVerticalGroup(layout.createParallelGroup()
				.addComponent(infoPanel)
				.addComponent(rightSidePanel));
		pack();
		getContentPane().setBackground(Colours.BACKGROUND);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		setVisible(true);
		setIconImage(new ImageIcon("resources/img/erasmus_icon.png").getImage());
		setTitle("Erasmus v0.0.1");
	}
	
	public void init(JDA jda) {
		List<Loadable> loadables = new ArrayList<Loadable>();
		
		
		
		for (Loadable loadable: loadables) {
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				public Void doInBackground() {
					loadable.startLoading();
					return null;
				}
				@Override
				public void done() {
					loadable.finishLoading();
				}
			};
			
			worker.execute();
			try {
				worker.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		infoPanel.init(jda);
	}
	
	public void onError(Throwable error) {
		error.printStackTrace();
	}
	
	public class UIEventListener extends ListenerAdapter {
		@Override
		public void onMessageReceived(MessageReceivedEvent event) {
			infoPanel.discordViewPanel.getGuildView(event.getGuild()).getChannelView(event.getTextChannel()).messagesPanel.addNewMessage(event.getMessage());
		}
		@Override
		public void onGuildJoin(GuildJoinEvent event) {
			infoPanel.discordViewPanel.addGuild(event.getGuild());
		}
		@Override
		public void onGuildLeave(GuildLeaveEvent event) {
			infoPanel.discordViewPanel.removeGuild(event.getGuild());
		}
		@Override
		public void onTextChannelCreate(TextChannelCreateEvent event) {
			infoPanel.discordViewPanel.getGuildView(event.getGuild()).textChannelCreated(event.getChannel());
		}
	}
	
	public class UIUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
		
		@Override
		public void uncaughtException(Thread t, Throwable e) {
			onError(e);
		}
		
	}
	
	public class UIEventManager implements IEventManager {

		private final CopyOnWriteArrayList<EventListener> listeners = new CopyOnWriteArrayList<>();

	    public UIEventManager() {

	    }

	    @Override
	    public void register(Object listener) {
	        if (!(listener instanceof EventListener)) {
	            throw new IllegalArgumentException("Listener must implement EventListener");
	        }
	        listeners.add(((EventListener) listener));
	    }

	    @Override
	    public void unregister(Object listener) {
	        listeners.remove(listener);
	    }

	    @Override
	    public List<Object> getRegisteredListeners() {
	        return Collections.unmodifiableList(new LinkedList<>(listeners));
	    }

	    @Override
	    public void handle(Event event) {
	        for (EventListener listener : listeners) {
	        	try {
	        		listener.onEvent(event);
	        	}
	        	catch(Throwable thr) {
	        		onError(thr);
	        	}
	        }
	    }
		
	}
}
