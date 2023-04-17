package utm.tmps.service.dto;

import utm.tmps.domain.enumeration.Languages;
import utm.tmps.domain.enumeration.StartWeekDay;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link utm.tmps.domain.UserSettings} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserSettingsDTO implements Serializable {

    private UUID id;

    @NotNull
    private StartWeekDay weekFirstDay;

    private Boolean weekNumber;

    private Boolean keepTrash;

    private Languages emailLanguage;

    private UserDTO userId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StartWeekDay getWeekFirstDay() {
        return weekFirstDay;
    }

    public void setWeekFirstDay(StartWeekDay weekFirstDay) {
        this.weekFirstDay = weekFirstDay;
    }

    public Boolean getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Boolean weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Boolean getKeepTrash() {
        return keepTrash;
    }

    public void setKeepTrash(Boolean keepTrash) {
        this.keepTrash = keepTrash;
    }

    public Languages getEmailLanguage() {
        return emailLanguage;
    }

    public void setEmailLanguage(Languages emailLanguage) {
        this.emailLanguage = emailLanguage;
    }

    public UserDTO getUserId() {
        return userId;
    }

    public void setUserId(UserDTO userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSettingsDTO)) {
            return false;
        }

        UserSettingsDTO userSettingsDTO = (UserSettingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userSettingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSettingsDTO{" +
            "id='" + getId() + "'" +
            ", weekFirstDay='" + getWeekFirstDay() + "'" +
            ", weekNumber='" + getWeekNumber() + "'" +
            ", keepTrash='" + getKeepTrash() + "'" +
            ", emailLanguage='" + getEmailLanguage() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
