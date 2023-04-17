package utm.tmps.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import utm.tmps.domain.enumeration.Languages;
import utm.tmps.domain.enumeration.StartWeekDay;

/**
 * A UserSettings.
 */
@Entity
@Table(name = "user_settings")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserSettings implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "week_first_day", nullable = false)
    private StartWeekDay weekFirstDay;

    @Column(name = "week_number")
    private Boolean weekNumber;

    @Column(name = "keep_trash")
    private Boolean keepTrash;

    @Column(name = "email_language")
    @Enumerated(EnumType.STRING)
    private Languages emailLanguage;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    public UserSettings() {
    }

    public UserSettings(StartWeekDay weekFirstDay,
                        Boolean weekNumber,
                        Boolean keepTrash,
                        Languages emailLanguage) {
        this.weekFirstDay = weekFirstDay;
        this.weekNumber = weekNumber;
        this.keepTrash = keepTrash;
        this.emailLanguage = emailLanguage;
    }

    public UUID getId() {
        return this.id;
    }

    public UserSettings id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StartWeekDay getWeekFirstDay() {
        return this.weekFirstDay;
    }

    public UserSettings weekFirstDay(StartWeekDay weekFirstDay) {
        this.setWeekFirstDay(weekFirstDay);
        return this;
    }

    public void setWeekFirstDay(StartWeekDay weekFirstDay) {
        this.weekFirstDay = weekFirstDay;
    }

    public Boolean getWeekNumber() {
        return this.weekNumber;
    }

    public UserSettings weekNumber(Boolean weekNumber) {
        this.setWeekNumber(weekNumber);
        return this;
    }

    public void setWeekNumber(Boolean weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Boolean getKeepTrash() {
        return this.keepTrash;
    }

    public UserSettings keepTrash(Boolean keepTrash) {
        this.setKeepTrash(keepTrash);
        return this;
    }

    public void setKeepTrash(Boolean keepTrash) {
        this.keepTrash = keepTrash;
    }

    public Languages getEmailLanguage() {
        return this.emailLanguage;
    }

    public UserSettings emailLanguage(Languages emailLanguage) {
        this.setEmailLanguage(emailLanguage);
        return this;
    }

    public void setEmailLanguage(Languages emailLanguage) {
        this.emailLanguage = emailLanguage;
    }

    public User getUserId() {
        return this.userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public UserSettings userId(User user) {
        this.setUserId(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSettings)) {
            return false;
        }
        return id != null && id.equals(((UserSettings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public UserSettings clone() {
        return new UserSettings(this.weekFirstDay, this.weekNumber, this.keepTrash, this.emailLanguage);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSettings{" +
            "id=" + getId() +
            ", weekFirstDay='" + getWeekFirstDay() + "'" +
            ", weekNumber='" + getWeekNumber() + "'" +
            ", keepTrash='" + getKeepTrash() + "'" +
            ", emailLanguage='" + getEmailLanguage() + "'" +
            "}";
    }
}
