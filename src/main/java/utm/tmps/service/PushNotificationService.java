package utm.tmps.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import utm.tmps.domain.Event;
import utm.tmps.domain.PushNotification;
import utm.tmps.repository.PushNotificationRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class PushNotificationService implements EventListener {

    private final PushNotificationRepository pushNotificationRepository;

    private final UserService userService;

    public PushNotificationService(PushNotificationRepository pushNotificationRepository, UserService userService) {
        this.pushNotificationRepository = pushNotificationRepository;
        this.userService = userService;
    }

    @Async
    public void sendEventNotification(Event event) {
        var newPush = new PushNotification();
        newPush.setTitle(event.getTitle());
        newPush.setUserId(event.getUserId());
        newPush.setContent(event.getTitle());
        pushNotificationRepository.save(newPush);
    }

    public Page<PushNotification> findAll(Pageable pageable) {
        var currentUser = userService.getCurrentAuthenticatedUser();
        return pushNotificationRepository.findAllByUserId(currentUser, pageable);
    }

    public Optional<PushNotification> findOne(UUID id) {
        return pushNotificationRepository.findById(id);
    }
}
