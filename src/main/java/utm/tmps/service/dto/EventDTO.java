package utm.tmps.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link utm.tmps.domain.Event} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventDTO implements Serializable {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String location;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    @Size(max = 1024)
    private String notes;

    private Boolean sendPushNotification;

    private Boolean sendEmailNotification;

    private Instant notificationTime;

    @Size(max = 50)
    private String status;

    private UserDTO userId;

    private TagDTO tagId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDTO getUserId() {
        return userId;
    }

    public void setUserId(UserDTO userId) {
        this.userId = userId;
    }

    public TagDTO getTagId() {
        return tagId;
    }

    public void setTagId(TagDTO tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDTO)) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventDTO{" +
            "id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", location='" + getLocation() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", notes='" + getNotes() + "'" +
            ", sendPushNotification='" + getSendPushNotification() + "'" +
            ", sendEmailNotification='" + getSendEmailNotification() + "'" +
            ", notificationTime='" + getNotificationTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", userId=" + getUserId() +
            ", tagId=" + getTagId() +
            "}";
    }
}
