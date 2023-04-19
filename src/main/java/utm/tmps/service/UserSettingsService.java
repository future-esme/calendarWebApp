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
import utm.tmps.service.dto.UserInfoAndSettingsDTO;
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

    private final UserService userService;

    public UserSettingsService(UserSettingsRepository userSettingsRepository, UserSettingsMapper userSettingsMapper, UserService userService) {
        this.userSettingsRepository = userSettingsRepository;
        this.userSettingsMapper = userSettingsMapper;
        this.userService = userService;
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
    public UserInfoAndSettingsDTO update(UserSettingsDTO userSettingsDTO) {
        log.debug("Request to update UserSettings : {}", userSettingsDTO);
        var currentUser = userService.getCurrentAuthenticatedUser();
        UserSettings userSettings = userSettingsRepository.findUserSettingsByUserId(currentUser).get();
        userSettings.setWeekNumber(userSettingsDTO.getWeekNumber());
        userSettings.setKeepTrash(userSettingsDTO.getKeepTrash());
        userSettings.setWeekFirstDay(userSettingsDTO.getWeekFirstDay());
        userSettings.setEmailLanguage(userSettingsDTO.getEmailLanguage());
        userSettingsRepository.save(userSettings);
        return new UserInfoAndSettingsDTO(currentUser);
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

    public UserInfoAndSettingsDTO findMySettings() {
        var currentUser = userService.getCurrentAuthenticatedUser();
        return new UserInfoAndSettingsDTO(currentUser);
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
