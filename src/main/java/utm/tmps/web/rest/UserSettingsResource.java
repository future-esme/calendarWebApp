package utm.tmps.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import utm.tmps.repository.UserSettingsRepository;
import utm.tmps.service.UserSettingsService;
import utm.tmps.service.dto.UserSettingsDTO;
import utm.tmps.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link utm.tmps.domain.UserSettings}.
 */
@RestController
@RequestMapping("/api")
public class UserSettingsResource {

    private final Logger log = LoggerFactory.getLogger(UserSettingsResource.class);

    private static final String ENTITY_NAME = "userSettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserSettingsService userSettingsService;

    private final UserSettingsRepository userSettingsRepository;

    public UserSettingsResource(UserSettingsService userSettingsService, UserSettingsRepository userSettingsRepository) {
        this.userSettingsService = userSettingsService;
        this.userSettingsRepository = userSettingsRepository;
    }

    /**
     * {@code POST  /user-settings} : Create a new userSettings.
     *
     * @param userSettingsDTO the userSettingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userSettingsDTO, or with status {@code 400 (Bad Request)} if the userSettings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-settings")
    public ResponseEntity<UserSettingsDTO> createUserSettings(@Valid @RequestBody UserSettingsDTO userSettingsDTO)
        throws URISyntaxException {
        log.debug("REST request to save UserSettings : {}", userSettingsDTO);
        if (userSettingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserSettingsDTO result = userSettingsService.save(userSettingsDTO);
        return ResponseEntity
            .created(new URI("/api/user-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-settings/:id} : Updates an existing userSettings.
     *
     * @param id the id of the userSettingsDTO to save.
     * @param userSettingsDTO the userSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the userSettingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-settings/{id}")
    public ResponseEntity<UserSettingsDTO> updateUserSettings(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody UserSettingsDTO userSettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserSettings : {}, {}", id, userSettingsDTO);
        if (userSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userSettingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserSettingsDTO result = userSettingsService.update(userSettingsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userSettingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-settings/:id} : Partial updates given fields of an existing userSettings, field will ignore if it is null
     *
     * @param id the id of the userSettingsDTO to save.
     * @param userSettingsDTO the userSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the userSettingsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userSettingsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserSettingsDTO> partialUpdateUserSettings(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody UserSettingsDTO userSettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserSettings partially : {}, {}", id, userSettingsDTO);
        if (userSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userSettingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserSettingsDTO> result = userSettingsService.partialUpdate(userSettingsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userSettingsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-settings} : get all the userSettings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userSettings in body.
     */
    @GetMapping("/user-settings")
    public ResponseEntity<List<UserSettingsDTO>> getAllUserSettings(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UserSettings");
        Page<UserSettingsDTO> page = userSettingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-settings/:id} : get the "id" userSettings.
     *
     * @param id the id of the userSettingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userSettingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-settings/{id}")
    public ResponseEntity<UserSettingsDTO> getUserSettings(@PathVariable UUID id) {
        log.debug("REST request to get UserSettings : {}", id);
        Optional<UserSettingsDTO> userSettingsDTO = userSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userSettingsDTO);
    }

    /**
     * {@code DELETE  /user-settings/:id} : delete the "id" userSettings.
     *
     * @param id the id of the userSettingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-settings/{id}")
    public ResponseEntity<Void> deleteUserSettings(@PathVariable UUID id) {
        log.debug("REST request to delete UserSettings : {}", id);
        userSettingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
