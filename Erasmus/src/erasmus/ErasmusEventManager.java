package erasmus;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.IEventManager;

public class ErasmusEventManager implements IEventManager {
	
	private final CopyOnWriteArrayList<EventListener> listeners = new CopyOnWriteArrayList<>();

    public ErasmusEventManager() {

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
            catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
