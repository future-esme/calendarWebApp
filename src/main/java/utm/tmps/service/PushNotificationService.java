package utm.tmps.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import utm.tmps.domain.Event;

@Service
public class PushNotificationService implements EventListener {
    @Async
    public void sendEventNotification(Event event) {
    }
}
