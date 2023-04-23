package utm.tmps.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import utm.tmps.domain.Event;

@Service
public class EmailNotificationService implements EventListener {

    private final MailService mailService;

    public EmailNotificationService(MailService mailService) {
        this.mailService = mailService;
    }

    @Async
    public void sendEventNotification(Event event) {
        //log.debug("Sending password reset email to '{}'", event.getUserId());
        mailService.sendEmailFromTemplate(event, "mail/eventNotificationEmail", "email.event.title");
    }
}
