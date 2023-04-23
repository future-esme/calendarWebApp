package utm.tmps.service;

import utm.tmps.domain.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    Map<Event, List<EventListener>> listeners = new HashMap<>();

    public void subscribe(Event event, EventListener listener) {
        var listenerExisting = listeners.get(event);
        if (listenerExisting == null) {
            listeners.put(event, new ArrayList<>(List.of(listener)));
        } else {
            listenerExisting.add(listener);
        }
    }

    public void notifyEvent() {
        for (var event : listeners.keySet()) {
            var eventListeners = listeners.get(event);
            eventListeners.forEach(item -> item.sendEventNotification(event));
        }
    }
}
