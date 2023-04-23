package utm.tmps.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import utm.tmps.IntegrationTest;
import utm.tmps.domain.UserSettings;
import utm.tmps.repository.UserSettingsRepository;
import utm.tmps.service.dto.UserSettingsDTO;
import utm.tmps.service.mapper.UserSettingsMapper;

/**
 * Integration tests for the {@link UserSettingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserSettingsResourceIT {

    private static final String DEFAULT_WEEK_FIRST_DAY = "AAAAAAAAAA";
    private static final String UPDATED_WEEK_FIRST_DAY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WEEK_NUMBER = false;
    private static final Boolean UPDATED_WEEK_NUMBER = true;

    private static final Boolean DEFAULT_KEEP_TRASH = false;
    private static final Boolean UPDATED_KEEP_TRASH = true;

    private static final String DEFAULT_EMAIL_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_LANGUAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private UserSettingsMapper userSettingsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserSettingsMockMvc;

    private UserSettings userSettings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSettings createEntity(EntityManager em) {
        UserSettings userSettings = new UserSettings()
            .weekFirstDay(DEFAULT_WEEK_FIRST_DAY)
            .weekNumber(DEFAULT_WEEK_NUMBER)
            .keepTrash(DEFAULT_KEEP_TRASH)
            .emailLanguage(DEFAULT_EMAIL_LANGUAGE);
        return userSettings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSettings createUpdatedEntity(EntityManager em) {
        UserSettings userSettings = new UserSettings()
            .weekFirstDay(UPDATED_WEEK_FIRST_DAY)
            .weekNumber(UPDATED_WEEK_NUMBER)
            .keepTrash(UPDATED_KEEP_TRASH)
            .emailLanguage(UPDATED_EMAIL_LANGUAGE);
        return userSettings;
    }

    @BeforeEach
    public void initTest() {
        userSettings = createEntity(em);
    }

    @Test
    @Transactional
    void createUserSettings() throws Exception {
        int databaseSizeBeforeCreate = userSettingsRepository.findAll().size();
        // Create the UserSettings
        UserSettingsDTO userSettingsDTO = userSettingsMapper.toDto(userSettings);
        restUserSettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSettingsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        UserSettings testUserSettings = userSettingsList.get(userSettingsList.size() - 1);
        assertThat(testUserSettings.getWeekFirstDay()).isEqualTo(DEFAULT_WEEK_FIRST_DAY);
        assertThat(testUserSettings.getWeekNumber()).isEqualTo(DEFAULT_WEEK_NUMBER);
        assertThat(testUserSettings.getKeepTrash()).isEqualTo(DEFAULT_KEEP_TRASH);
        assertThat(testUserSettings.getEmailLanguage()).isEqualTo(DEFAULT_EMAIL_LANGUAGE);
    }

    @Test
    @Transactional
    void createUserSettingsWithExistingId() throws Exception {
        // Create the UserSettings with an existing ID
        userSettingsRepository.saveAndFlush(userSettings);
        UserSettingsDTO userSettingsDTO = userSettingsMapper.toDto(userSettings);

        int databaseSizeBeforeCreate = userSettingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkWeekFirstDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSettingsRepository.findAll().size();
        // set the field null
        userSettings.setWeekFirstDay(null);

        // Create the UserSettings, which fails.
        UserSettingsDTO userSettingsDTO = userSettingsMapper.toDto(userSettings);

        restUserSettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserSettings() throws Exception {
        // Initialize the database
        userSettingsRepository.saveAndFlush(userSettings);

        // Get all the userSettingsList
        restUserSettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSettings.getId().toString())))
            .andExpect(jsonPath("$.[*].weekFirstDay").value(hasItem(DEFAULT_WEEK_FIRST_DAY)))
            .andExpect(jsonPath("$.[*].weekNumber").value(hasItem(DEFAULT_WEEK_NUMBER.booleanValue())))
            .andExpect(jsonPath("$.[*].keepTrash").value(hasItem(DEFAULT_KEEP_TRASH.booleanValue())))
            .andExpect(jsonPath("$.[*].emailLanguage").value(hasItem(DEFAULT_EMAIL_LANGUAGE)));
    }

    @Test
    @Transactional
    void getUserSettings() throws Exception {
        // Initialize the database
        userSettingsRepository.saveAndFlush(userSettings);

        // Get the userSettings
        restUserSettingsMockMvc
            .perform(get(ENTITY_API_URL_ID, userSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userSettings.getId().toString()))
            .andExpect(jsonPath("$.weekFirstDay").value(DEFAULT_WEEK_FIRST_DAY))
            .andExpect(jsonPath("$.weekNumber").value(DEFAULT_WEEK_NUMBER.booleanValue()))
            .andExpect(jsonPath("$.keepTrash").value(DEFAULT_KEEP_TRASH.booleanValue()))
            .andExpect(jsonPath("$.emailLanguage").value(DEFAULT_EMAIL_LANGUAGE));
    }

    @Test
    @Transactional
    void getNonExistingUserSettings() throws Exception {
        // Get the userSettings
        restUserSettingsMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserSettings() throws Exception {
        // Initialize the database
        userSettingsRepository.saveAndFlush(userSettings);

        int databaseSizeBeforeUpdate = userSettingsRepository.findAll().size();

        // Update the userSettings
        UserSettings updatedUserSettings = userSettingsRepository.findById(userSettings.getId()).get();
        // Disconnect from session so that the updates on updatedUserSettings are not directly saved in db
        em.detach(updatedUserSettings);
        updatedUserSettings
            .weekFirstDay(UPDATED_WEEK_FIRST_DAY)
            .weekNumber(UPDATED_WEEK_NUMBER)
            .keepTrash(UPDATED_KEEP_TRASH)
            .emailLanguage(UPDATED_EMAIL_LANGUAGE);
        UserSettingsDTO userSettingsDTO = userSettingsMapper.toDto(updatedUserSettings);

        restUserSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userSettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSettingsDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeUpdate);
        UserSettings testUserSettings = userSettingsList.get(userSettingsList.size() - 1);
        assertThat(testUserSettings.getWeekFirstDay()).isEqualTo(UPDATED_WEEK_FIRST_DAY);
        assertThat(testUserSettings.getWeekNumber()).isEqualTo(UPDATED_WEEK_NUMBER);
        assertThat(testUserSettings.getKeepTrash()).isEqualTo(UPDATED_KEEP_TRASH);
        assertThat(testUserSettings.getEmailLanguage()).isEqualTo(UPDATED_EMAIL_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingUserSettings() throws Exception {
        int databaseSizeBeforeUpdate = userSettingsRepository.findAll().size();
        userSettings.setId(UUID.randomUUID());

        // Create the UserSettings
        UserSettingsDTO userSettingsDTO = userSettingsMapper.toDto(userSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userSettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserSettings() throws Exception {
        int databaseSizeBeforeUpdate = userSettingsRepository.findAll().size();
        userSettings.setId(UUID.randomUUID());

        // Create the UserSettings
        UserSettingsDTO userSettingsDTO = userSettingsMapper.toDto(userSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserSettings() throws Exception {
        int databaseSizeBeforeUpdate = userSettingsRepository.findAll().size();
        userSettings.setId(UUID.randomUUID());

        // Create the UserSettings
        UserSettingsDTO userSettingsDTO = userSettingsMapper.toDto(userSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSettingsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserSettingsWithPatch() throws Exception {
        // Initialize the database
        userSettingsRepository.saveAndFlush(userSettings);

        int databaseSizeBeforeUpdate = userSettingsRepository.findAll().size();

        // Update the userSettings using partial update
        UserSettings partialUpdatedUserSettings = new UserSettings();
        partialUpdatedUserSettings.setId(userSettings.getId());

        partialUpdatedUserSettings.keepTrash(UPDATED_KEEP_TRASH).emailLanguage(UPDATED_EMAIL_LANGUAGE);

        restUserSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserSettings))
            )
            .andExpect(status().isOk());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeUpdate);
        UserSettings testUserSettings = userSettingsList.get(userSettingsList.size() - 1);
        assertThat(testUserSettings.getWeekFirstDay()).isEqualTo(DEFAULT_WEEK_FIRST_DAY);
        assertThat(testUserSettings.getWeekNumber()).isEqualTo(DEFAULT_WEEK_NUMBER);
        assertThat(testUserSettings.getKeepTrash()).isEqualTo(UPDATED_KEEP_TRASH);
        assertThat(testUserSettings.getEmailLanguage()).isEqualTo(UPDATED_EMAIL_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateUserSettingsWithPatch() throws Exception {
        // Initialize the database
        userSettingsRepository.saveAndFlush(userSettings);

        int databaseSizeBeforeUpdate = userSettingsRepository.findAll().size();

        // Update the userSettings using partial update
        UserSettings partialUpdatedUserSettings = new UserSettings();
        partialUpdatedUserSettings.setId(userSettings.getId());

        partialUpdatedUserSettings
            .weekFirstDay(UPDATED_WEEK_FIRST_DAY)
            .weekNumber(UPDATED_WEEK_NUMBER)
            .keepTrash(UPDATED_KEEP_TRASH)
            .emailLanguage(UPDATED_EMAIL_LANGUAGE);

        restUserSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserSettings))
            )
            .andExpect(status().isOk());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeUpdate);
        UserSettings testUserSettings = userSettingsList.get(userSettingsList.size() - 1);
        assertThat(testUserSettings.getWeekFirstDay()).isEqualTo(UPDATED_WEEK_FIRST_DAY);
        assertThat(testUserSettings.getWeekNumber()).isEqualTo(UPDATED_WEEK_NUMBER);
        assertThat(testUserSettings.getKeepTrash()).isEqualTo(UPDATED_KEEP_TRASH);
        assertThat(testUserSettings.getEmailLanguage()).isEqualTo(UPDATED_EMAIL_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingUserSettings() throws Exception {
        int databaseSizeBeforeUpdate = userSettingsRepository.findAll().size();
        userSettings.setId(UUID.randomUUID());

        // Create the UserSettings
        UserSettingsDTO userSettingsDTO = userSettingsMapper.toDto(userSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userSettingsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserSettings() throws Exception {
        int databaseSizeBeforeUpdate = userSettingsRepository.findAll().size();
        userSettings.setId(UUID.randomUUID());

        // Create the UserSettings
        UserSettingsDTO userSettingsDTO = userSettingsMapper.toDto(userSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserSettings() throws Exception {
        int databaseSizeBeforeUpdate = userSettingsRepository.findAll().size();
        userSettings.setId(UUID.randomUUID());

        // Create the UserSettings
        UserSettingsDTO userSettingsDTO = userSettingsMapper.toDto(userSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserSettings() throws Exception {
        // Initialize the database
        userSettingsRepository.saveAndFlush(userSettings);

        int databaseSizeBeforeDelete = userSettingsRepository.findAll().size();

        // Delete the userSettings
        restUserSettingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, userSettings.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
