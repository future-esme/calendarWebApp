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
import utm.tmps.domain.PushNotification;
import utm.tmps.repository.PushNotificationRepository;
import utm.tmps.service.dto.PushNotificationDTO;
import utm.tmps.service.mapper.PushNotificationMapper;

/**
 * Integration tests for the {@link PushNotificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PushNotificationResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/push-notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PushNotificationRepository pushNotificationRepository;

    @Autowired
    private PushNotificationMapper pushNotificationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPushNotificationMockMvc;

    private PushNotification pushNotification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PushNotification createEntity(EntityManager em) {
        PushNotification pushNotification = new PushNotification().title(DEFAULT_TITLE).content(DEFAULT_CONTENT);
        return pushNotification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PushNotification createUpdatedEntity(EntityManager em) {
        PushNotification pushNotification = new PushNotification().title(UPDATED_TITLE).content(UPDATED_CONTENT);
        return pushNotification;
    }

    @BeforeEach
    public void initTest() {
        pushNotification = createEntity(em);
    }

    @Test
    @Transactional
    void createPushNotification() throws Exception {
        int databaseSizeBeforeCreate = pushNotificationRepository.findAll().size();
        // Create the PushNotification
        PushNotificationDTO pushNotificationDTO = pushNotificationMapper.toDto(pushNotification);
        restPushNotificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pushNotificationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PushNotification in the database
        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        PushNotification testPushNotification = pushNotificationList.get(pushNotificationList.size() - 1);
        assertThat(testPushNotification.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPushNotification.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    void createPushNotificationWithExistingId() throws Exception {
        // Create the PushNotification with an existing ID
        pushNotificationRepository.saveAndFlush(pushNotification);
        PushNotificationDTO pushNotificationDTO = pushNotificationMapper.toDto(pushNotification);

        int databaseSizeBeforeCreate = pushNotificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPushNotificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pushNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PushNotification in the database
        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = pushNotificationRepository.findAll().size();
        // set the field null
        pushNotification.setTitle(null);

        // Create the PushNotification, which fails.
        PushNotificationDTO pushNotificationDTO = pushNotificationMapper.toDto(pushNotification);

        restPushNotificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pushNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPushNotifications() throws Exception {
        // Initialize the database
        pushNotificationRepository.saveAndFlush(pushNotification);

        // Get all the pushNotificationList
        restPushNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pushNotification.getId().toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    void getPushNotification() throws Exception {
        // Initialize the database
        pushNotificationRepository.saveAndFlush(pushNotification);

        // Get the pushNotification
        restPushNotificationMockMvc
            .perform(get(ENTITY_API_URL_ID, pushNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pushNotification.getId().toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    void getNonExistingPushNotification() throws Exception {
        // Get the pushNotification
        restPushNotificationMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPushNotification() throws Exception {
        // Initialize the database
        pushNotificationRepository.saveAndFlush(pushNotification);

        int databaseSizeBeforeUpdate = pushNotificationRepository.findAll().size();

        // Update the pushNotification
        PushNotification updatedPushNotification = pushNotificationRepository.findById(pushNotification.getId()).get();
        // Disconnect from session so that the updates on updatedPushNotification are not directly saved in db
        em.detach(updatedPushNotification);
        updatedPushNotification.title(UPDATED_TITLE).content(UPDATED_CONTENT);
        PushNotificationDTO pushNotificationDTO = pushNotificationMapper.toDto(updatedPushNotification);

        restPushNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pushNotificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pushNotificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the PushNotification in the database
        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeUpdate);
        PushNotification testPushNotification = pushNotificationList.get(pushNotificationList.size() - 1);
        assertThat(testPushNotification.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPushNotification.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void putNonExistingPushNotification() throws Exception {
        int databaseSizeBeforeUpdate = pushNotificationRepository.findAll().size();
        pushNotification.setId(UUID.randomUUID());

        // Create the PushNotification
        PushNotificationDTO pushNotificationDTO = pushNotificationMapper.toDto(pushNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPushNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pushNotificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pushNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PushNotification in the database
        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPushNotification() throws Exception {
        int databaseSizeBeforeUpdate = pushNotificationRepository.findAll().size();
        pushNotification.setId(UUID.randomUUID());

        // Create the PushNotification
        PushNotificationDTO pushNotificationDTO = pushNotificationMapper.toDto(pushNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPushNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pushNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PushNotification in the database
        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPushNotification() throws Exception {
        int databaseSizeBeforeUpdate = pushNotificationRepository.findAll().size();
        pushNotification.setId(UUID.randomUUID());

        // Create the PushNotification
        PushNotificationDTO pushNotificationDTO = pushNotificationMapper.toDto(pushNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPushNotificationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pushNotificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PushNotification in the database
        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePushNotificationWithPatch() throws Exception {
        // Initialize the database
        pushNotificationRepository.saveAndFlush(pushNotification);

        int databaseSizeBeforeUpdate = pushNotificationRepository.findAll().size();

        // Update the pushNotification using partial update
        PushNotification partialUpdatedPushNotification = new PushNotification();
        partialUpdatedPushNotification.setId(pushNotification.getId());

        partialUpdatedPushNotification.title(UPDATED_TITLE);

        restPushNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPushNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPushNotification))
            )
            .andExpect(status().isOk());

        // Validate the PushNotification in the database
        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeUpdate);
        PushNotification testPushNotification = pushNotificationList.get(pushNotificationList.size() - 1);
        assertThat(testPushNotification.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPushNotification.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    void fullUpdatePushNotificationWithPatch() throws Exception {
        // Initialize the database
        pushNotificationRepository.saveAndFlush(pushNotification);

        int databaseSizeBeforeUpdate = pushNotificationRepository.findAll().size();

        // Update the pushNotification using partial update
        PushNotification partialUpdatedPushNotification = new PushNotification();
        partialUpdatedPushNotification.setId(pushNotification.getId());

        partialUpdatedPushNotification.title(UPDATED_TITLE).content(UPDATED_CONTENT);

        restPushNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPushNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPushNotification))
            )
            .andExpect(status().isOk());

        // Validate the PushNotification in the database
        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeUpdate);
        PushNotification testPushNotification = pushNotificationList.get(pushNotificationList.size() - 1);
        assertThat(testPushNotification.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPushNotification.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void patchNonExistingPushNotification() throws Exception {
        int databaseSizeBeforeUpdate = pushNotificationRepository.findAll().size();
        pushNotification.setId(UUID.randomUUID());

        // Create the PushNotification
        PushNotificationDTO pushNotificationDTO = pushNotificationMapper.toDto(pushNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPushNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pushNotificationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pushNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PushNotification in the database
        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPushNotification() throws Exception {
        int databaseSizeBeforeUpdate = pushNotificationRepository.findAll().size();
        pushNotification.setId(UUID.randomUUID());

        // Create the PushNotification
        PushNotificationDTO pushNotificationDTO = pushNotificationMapper.toDto(pushNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPushNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pushNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PushNotification in the database
        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPushNotification() throws Exception {
        int databaseSizeBeforeUpdate = pushNotificationRepository.findAll().size();
        pushNotification.setId(UUID.randomUUID());

        // Create the PushNotification
        PushNotificationDTO pushNotificationDTO = pushNotificationMapper.toDto(pushNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPushNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pushNotificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PushNotification in the database
        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePushNotification() throws Exception {
        // Initialize the database
        pushNotificationRepository.saveAndFlush(pushNotification);

        int databaseSizeBeforeDelete = pushNotificationRepository.findAll().size();

        // Delete the pushNotification
        restPushNotificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, pushNotification.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PushNotification> pushNotificationList = pushNotificationRepository.findAll();
        assertThat(pushNotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
