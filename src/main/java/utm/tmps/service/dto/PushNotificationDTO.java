package utm.tmps.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link utm.tmps.domain.PushNotification} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PushNotificationDTO implements Serializable {

    private UUID id;

    @NotNull
    @Size(min = 3, max = 255)
    private String title;

    @Size(max = 1024)
    private String content;

    private UserDTO userId;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        if (!(o instanceof PushNotificationDTO)) {
            return false;
        }

        PushNotificationDTO pushNotificationDTO = (PushNotificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pushNotificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PushNotificationDTO{" +
            "id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
