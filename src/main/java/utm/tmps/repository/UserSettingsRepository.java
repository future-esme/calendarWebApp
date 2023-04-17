package utm.tmps.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import utm.tmps.domain.UserSettings;

/**
 * Spring Data JPA repository for the UserSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, UUID> {}
