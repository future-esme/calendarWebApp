package utm.tmps.domain;

import utm.tmps.domain.enumeration.EventStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Size(max = 255)
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 255)
    @Column(name = "location")
    private String location;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Size(max = 1024)
    @Column(name = "notes", length = 1024)
    private String notes;

    @Column(name = "send_push_notification")
    private Boolean sendPushNotification;

    @Column(name = "send_email_notification")
    private Boolean sendEmailNotification;

    @Column(name = "notification_time")
    private Instant notificationTime;

    @Column(name = "status")
    private EventStatus status;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @OneToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tagId;

    public Event() {
    }

    public Event(EventBuilder builder) {
        this.title = builder.title;
        this.location = builder.location;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.notes = builder.notes;
        this.sendPushNotification = builder.sendPushNotification;
        this.sendEmailNotification = builder.sendEmailNotification;
        this.notificationTime = builder.notificationTime;
        this.status = builder.status;
        this.userId = builder.userId;
        this.tagId = builder.tagId;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public String getNotes() {
        return notes;
    }

    public Boolean getSendPushNotification() {
        return sendPushNotification;
    }

    public Boolean getSendEmailNotification() {
        return sendEmailNotification;
    }

    public Instant getNotificationTime() {
        return notificationTime;
    }

    public EventStatus getStatus() {
        return status;
    }

    public User getUserId() {
        return userId;
    }

    public Tag getTagId() {
        return tagId;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setSendPushNotification(Boolean sendPushNotification) {
        this.sendPushNotification = sendPushNotification;
    }

    public void setSendEmailNotification(Boolean sendEmailNotification) {
        this.sendEmailNotification = sendEmailNotification;
    }

    public void setNotificationTime(Instant notificationTime) {
        this.notificationTime = notificationTime;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public void setTagId(Tag tagId) {
        this.tagId = tagId;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", location='" + getLocation() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", notes='" + getNotes() + "'" +
            ", sendPushNotification='" + getSendPushNotification() + "'" +
            ", sendEmailNotification='" + getSendEmailNotification() + "'" +
            ", notificationTime='" + getNotificationTime() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }

    public static class EventBuilder {
        private String title;
        private String location;
        private Instant startTime;
        private Instant endTime;
        private String notes;
        private Boolean sendPushNotification;
        private Boolean sendEmailNotification;
        private Instant notificationTime;
        private EventStatus status;
        private User userId;
        private Tag tagId;

        public EventBuilder(String title, String location, LocalDate eventDay, User userId, Tag tagId) {
            this.title = title;
            this.location = location;
            this.startTime = eventDay.atStartOfDay().toInstant(ZoneOffset.UTC);
            this.endTime = eventDay.atTime(23,59).toInstant(ZoneOffset.UTC);
            this.userId = userId;
            this.tagId = tagId;
            this.status = EventStatus.NO_STATUS;
        }

        public EventBuilder(String title, String location, Instant startTime, Instant endTime, User userId, Tag tagId) {
            this.title = title;
            this.location = location;
            this.startTime = startTime;
            this.endTime = endTime;
            this.userId = userId;
            this.tagId = tagId;
            this.status = EventStatus.NO_STATUS;
        }

        public EventBuilder location(String location) {
            this.location = location;
            return this;
        }

        public EventBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public EventBuilder sendPushNotification(Boolean sendPushNotification) {
            this.sendPushNotification = sendPushNotification;
            return this;
        }

        public EventBuilder sendEmailNotification(Boolean sendEmailNotification) {
            this.sendEmailNotification = sendEmailNotification;
            return this;
        }

        public EventBuilder notificationTime(Instant notificationTime) {
            this.notificationTime = notificationTime;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }
}
