package utm.tmps.service;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.tmps.domain.UserSettings;
import utm.tmps.repository.UserSettingsRepository;
import utm.tmps.service.dto.UserSettingsDTO;
import utm.tmps.service.mapper.UserSettingsMapper;

/**
 * Service Implementation for managing {@link UserSettings}.
 */
@Service
@Transactional
public class UserSettingsService {

    private final Logger log = LoggerFactory.getLogger(UserSettingsService.class);

    private final UserSettingsRepository userSettingsRepository;

    private final UserSettingsMapper userSettingsMapper;

    public UserSettingsService(UserSettingsRepository userSettingsRepository, UserSettingsMapper userSettingsMapper) {
        this.userSettingsRepository = userSettingsRepository;
        this.userSettingsMapper = userSettingsMapper;
    }

    /**
     * Save a userSettings.
     *
     * @param userSettingsDTO the entity to save.
     * @return the persisted entity.
     */
    public UserSettingsDTO save(UserSettingsDTO userSettingsDTO) {
        log.debug("Request to save UserSettings : {}", userSettingsDTO);
        UserSettings userSettings = userSettingsMapper.toEntity(userSettingsDTO);
        userSettings = userSettingsRepository.save(userSettings);
        return userSettingsMapper.toDto(userSettings);
    }

    /**
     * Update a userSettings.
     *
     * @param userSettingsDTO the entity to save.
     * @return the persisted entity.
     */
    public UserSettingsDTO update(UserSettingsDTO userSettingsDTO) {
        log.debug("Request to update UserSettings : {}", userSettingsDTO);
        UserSettings userSettings = userSettingsMapper.toEntity(userSettingsDTO);
        userSettings = userSettingsRepository.save(userSettings);
        return userSettingsMapper.toDto(userSettings);
    }

    /**
     * Partially update a userSettings.
     *
     * @param userSettingsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserSettingsDTO> partialUpdate(UserSettingsDTO userSettingsDTO) {
        log.debug("Request to partially update UserSettings : {}", userSettingsDTO);

        return userSettingsRepository
            .findById(userSettingsDTO.getId())
            .map(existingUserSettings -> {
                userSettingsMapper.partialUpdate(existingUserSettings, userSettingsDTO);

                return existingUserSettings;
            })
            .map(userSettingsRepository::save)
            .map(userSettingsMapper::toDto);
    }

    /**
     * Get all the userSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserSettingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserSettings");
        return userSettingsRepository.findAll(pageable).map(userSettingsMapper::toDto);
    }

    /**
     * Get one userSettings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserSettingsDTO> findOne(UUID id) {
        log.debug("Request to get UserSettings : {}", id);
        return userSettingsRepository.findById(id).map(userSettingsMapper::toDto);
    }

    /**
     * Delete the userSettings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete UserSettings : {}", id);
        userSettingsRepository.deleteById(id);
    }
}
