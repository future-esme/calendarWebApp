package utm.tmps.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import utm.tmps.domain.Tag;
import utm.tmps.domain.User;

/**
 * Spring Data JPA repository for the Tag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    List<Tag> findAllByUserId(User user);

    Page<Tag> findAllByUserId(User user, Pageable pageable);
}
