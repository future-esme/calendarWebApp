package utm.tmps.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
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

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @OneToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tagId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Event id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Event title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return this.location;
    }

    public Event location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public Event startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public Event endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getNotes() {
        return this.notes;
    }

    public Event notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getSendPushNotification() {
        return this.sendPushNotification;
    }

    public Event sendPushNotification(Boolean sendPushNotification) {
        this.setSendPushNotification(sendPushNotification);
        return this;
    }

    public void setSendPushNotification(Boolean sendPushNotification) {
        this.sendPushNotification = sendPushNotification;
    }

    public Boolean getSendEmailNotification() {
        return this.sendEmailNotification;
    }

    public Event sendEmailNotification(Boolean sendEmailNotification) {
        this.setSendEmailNotification(sendEmailNotification);
        return this;
    }

    public void setSendEmailNotification(Boolean sendEmailNotification) {
        this.sendEmailNotification = sendEmailNotification;
    }

    public Instant getNotificationTime() {
        return this.notificationTime;
    }

    public Event notificationTime(Instant notificationTime) {
        this.setNotificationTime(notificationTime);
        return this;
    }

    public void setNotificationTime(Instant notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getStatus() {
        return this.status;
    }

    public Event status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUserId() {
        return this.userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public Event userId(User user) {
        this.setUserId(user);
        return this;
    }

    public Tag getTagId() {
        return this.tagId;
    }

    public void setTagId(Tag tag) {
        this.tagId = tag;
    }

    public Event tagId(Tag tag) {
        this.setTagId(tag);
        return this;
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
}
