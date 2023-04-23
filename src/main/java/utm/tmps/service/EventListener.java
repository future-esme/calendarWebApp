package utm.tmps.service;

import utm.tmps.domain.Event;

public interface EventListener {
    void sendEventNotification(Event event);
}
