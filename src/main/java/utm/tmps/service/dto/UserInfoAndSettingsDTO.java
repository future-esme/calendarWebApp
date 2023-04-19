package utm.tmps.service.dto;

import utm.tmps.domain.User;
import utm.tmps.domain.enumeration.Languages;
import utm.tmps.domain.enumeration.StartWeekDay;

public class UserInfoAndSettingsDTO {
    private StartWeekDay weekFirstDay;

    private Boolean weekNumber;

    private Boolean keepTrash;

    private Languages emailLanguage;

    private String firstName;
    private String lastName;
    private String email;

    private String langKey;

    public UserInfoAndSettingsDTO() {
    }

    public UserInfoAndSettingsDTO(User user) {
        var userSettings = user.getUserSettings();
        this.weekFirstDay = userSettings.getWeekFirstDay();
        this.weekNumber = userSettings.getWeekNumber();
        this.keepTrash = userSettings.getKeepTrash();
        this.emailLanguage = userSettings.getEmailLanguage();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.langKey = user.getLangKey();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    @Override
    public String toString() {
        return "UserInfoAndSettingsDTO{" +
            "weekFirstDay=" + weekFirstDay +
            ", weekNumber=" + weekNumber +
            ", keepTrash=" + keepTrash +
            ", emailLanguage=" + emailLanguage +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
