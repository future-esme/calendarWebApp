package utm.tmps.service;

import utm.tmps.domain.Tag;

import java.util.Optional;
import java.util.UUID;

public interface UserTagManagement {
    void delete(UUID id);

    Optional<Tag> findTag(UUID id);
}
