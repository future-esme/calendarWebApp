package utm.tmps.service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.tmps.domain.Event;
import utm.tmps.domain.enumeration.Month;
import utm.tmps.repository.EventRepository;
import utm.tmps.repository.PushNotificationRepository;
import utm.tmps.repository.TagRepository;
import utm.tmps.service.dto.EventDTO;

/**
 * Service Implementation for managing {@link Event}.
 */
@Service
public class EventService {

    private final LoggingClient log = new LoggingClient();

    private final EventRepository eventRepository;

    private final TagRepository tagRepository;

    private final UserService userService;

    private final MailService mailService;

    private final PushNotificationRepository pushNotificationRepository;

    public EventService(
        EventRepository eventRepository,
        TagRepository tagRepository,
        UserService userService,
        MailService mailService,
        PushNotificationRepository pushNotificationRepository
    ) {
        this.eventRepository = eventRepository;
        this.tagRepository = tagRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.pushNotificationRepository = pushNotificationRepository;
        this.log.setMyLogger(new FileLogger());
    }

    /**
     * Save a event.
     *
     * @param eventDTO the entity to save.
     * @return the persisted entity.
     */
    public Event save(EventDTO eventDTO) {
        //log.debug("Request to save Event : {}", eventDTO);
        var currentUser = userService.getCurrentAuthenticatedUser();
        var tag = tagRepository.findById(eventDTO.getTagId());
        Event.EventBuilder newEvent;
        if (Boolean.TRUE.equals(eventDTO.getIsAllDay())) {
            newEvent = new Event.EventBuilder(eventDTO.getTitle(), eventDTO.getLocation(), eventDTO.getEventDate(), currentUser, tag.get());
        } else {
            newEvent =
                new Event.EventBuilder(
                    eventDTO.getTitle(),
                    eventDTO.getLocation(),
                    eventDTO.getStartTime(),
                    eventDTO.getEndTime(),
                    currentUser,
                    tag.get()
                );
        }
        if (eventDTO.getLocation() != null) {
            newEvent.location(eventDTO.getLocation());
        }
        if (eventDTO.getNotes() != null) {
            newEvent.notes(eventDTO.getNotes());
        }
        if (eventDTO.getSendPushNotification() != null) {
            newEvent.sendPushNotification(eventDTO.getSendPushNotification()).notificationTime(eventDTO.getNotificationTime());
        } else {
            newEvent.sendPushNotification(false);
        }
        if (eventDTO.getSendEmailNotification() != null) {
            newEvent.sendEmailNotification(eventDTO.getSendEmailNotification()).notificationTime(eventDTO.getNotificationTime());
        } else {
            newEvent.sendEmailNotification(false);
        }
        return eventRepository.save(newEvent.build());
    }

    /**
     * Update a event.
     *
     * @param eventDTO the entity to save.
     * @return the persisted entity.
     */
    public Event update(EventDTO eventDTO, UUID eventId) {
        //log.debug("Request to update Event : {}", eventDTO);
        var tag = tagRepository.findById(eventDTO.getTagId());
        var event = eventRepository.findById(eventId).get();
        event.setTitle(eventDTO.getTitle());
        event.setLocation(event.getLocation());
        if (Boolean.TRUE.equals(eventDTO.getIsAllDay())) {
            event.setStartTime(eventDTO.getEventDate().atStartOfDay().toInstant(ZoneOffset.UTC));
            event.setEndTime(eventDTO.getEventDate().atTime(23, 59).toInstant(ZoneOffset.UTC));
        } else {
            event.setStartTime(eventDTO.getStartTime());
            event.setEndTime(eventDTO.getEndTime());
        }
        event.setNotes(eventDTO.getNotes());
        event.setSendPushNotification(eventDTO.getSendPushNotification());
        event.setSendEmailNotification(eventDTO.getSendEmailNotification());
        if (eventDTO.getSendPushNotification() || eventDTO.getSendEmailNotification()) {
            event.setNotificationTime(eventDTO.getNotificationTime());
        }
        event.setTagId(tag.get());
        return eventRepository.save(event);
    }

    /**
     * Get all the events.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Event> findAll(Pageable pageable) {
        log.debug("Request to get all Events");
        return eventRepository.findAll(pageable);
    }

    /**
     * Get one event by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Event> findOne(UUID id) {
        //log.debug("Request to get Event : {}", id);
        return eventRepository.findById(id);
    }

    public List<Event> findEventsByDay(Integer day, Month month, Integer year) {
        log.debug("Find events for day " + day + " month " + month + " year " + year);
        var currentUser = userService.getCurrentAuthenticatedUser();
        var targetDayStart = LocalDateTime.of(year, month.ordinal() + 1, day, 0, 0).toInstant(ZoneOffset.UTC);
        var targetDayEnd = LocalDateTime.of(year, month.ordinal() + 1, day, 23, 59).toInstant(ZoneOffset.UTC);
        return eventRepository.findUserEventByDay(currentUser.getId(), targetDayStart, targetDayEnd);
    }

    /**
     * Delete the event by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        //log.debug("Request to delete Event : {}", id);
        eventRepository.deleteById(id);
    }

    @Scheduled(cron = "0 */1 * ? * *")
    public void findEventsAndNotifyUsers() {
        var now = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).minus(3, ChronoUnit.HOURS);
        var nowBeginningOfMinute = now.withSecond(0).withNano(0).toInstant(ZoneOffset.UTC);
        var nowEndingOfMinute = now.withSecond(59).toInstant(ZoneOffset.UTC);
        var eventsToBeNotified = eventRepository.findEventsNeedToBeNotified(nowBeginningOfMinute, nowEndingOfMinute);
        var eventManager = EventManager.getInstance();
        for (var event : eventsToBeNotified) {
            if (Boolean.TRUE.equals(event.getSendEmailNotification())) {
                eventManager.subscribe(event, new EmailNotificationService(mailService));
            }
            if (Boolean.TRUE.equals(event.getSendPushNotification())) {
                eventManager.subscribe(event, new PushNotificationService(pushNotificationRepository, userService));
            }
        }
        eventManager.notifyEvent();
    }
}
