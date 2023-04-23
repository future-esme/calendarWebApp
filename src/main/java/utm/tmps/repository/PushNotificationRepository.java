package utm.tmps.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import utm.tmps.domain.PushNotification;
import utm.tmps.domain.User;

/**
 * Spring Data JPA repository for the PushNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PushNotificationRepository extends JpaRepository<PushNotification, UUID> {

    Page<PushNotification> findAllByUserId(User user, Pageable pageable);
}
