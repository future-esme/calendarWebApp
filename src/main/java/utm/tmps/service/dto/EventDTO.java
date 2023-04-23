package utm.tmps.service.dto;

import utm.tmps.domain.enumeration.EventStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A DTO for the {@link utm.tmps.domain.Event} entity.
 */
public class EventDTO {

    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String location;

    private Instant startTime;

    private Instant endTime;

    @Size(max = 1024)
    private String notes;

    private Boolean sendPushNotification;

    private Boolean sendEmailNotification;

    private Instant notificationTime;

    private LocalDate eventDate;

    private EventStatus status;

    @NotNull
    private Boolean isAllDay;

    @NotNull
    private UUID tagId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getSendPushNotification() {
        return sendPushNotification;
    }

    public void setSendPushNotification(Boolean sendPushNotification) {
        this.sendPushNotification = sendPushNotification;
    }

    public Boolean getSendEmailNotification() {
        return sendEmailNotification;
    }

    public void setSendEmailNotification(Boolean sendEmailNotification) {
        this.sendEmailNotification = sendEmailNotification;
    }

    public Instant getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(Instant notificationTime) {
        this.notificationTime = notificationTime;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public UUID getTagId() {
        return tagId;
    }

    public void setTagId(UUID tagId) {
        this.tagId = tagId;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Boolean getIsAllDay() {
        return isAllDay;
    }

    public void setIsAllDay(Boolean allDay) {
        isAllDay = allDay;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventDTO{" +
            ", title='" + getTitle() + "'" +
            ", location='" + getLocation() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", notes='" + getNotes() + "'" +
            ", sendPushNotification='" + getSendPushNotification() + "'" +
            ", sendEmailNotification='" + getSendEmailNotification() + "'" +
            ", notificationTime='" + getNotificationTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", tagId=" + getTagId() +
            "}";
    }
}
