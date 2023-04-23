package utm.tmps.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import utm.tmps.domain.PushNotification;
import utm.tmps.service.PushNotificationService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing {@link utm.tmps.domain.PushNotification}.
 */
@RestController
@RequestMapping("/api")
public class PushNotificationResource {

    private final Logger log = LoggerFactory.getLogger(PushNotificationResource.class);

    private static final String ENTITY_NAME = "pushNotification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PushNotificationService pushNotificationService;

    public PushNotificationResource(
        PushNotificationService pushNotificationService
    ) {
        this.pushNotificationService = pushNotificationService;
    }


    /**
     * {@code GET  /push-notifications} : get all the pushNotifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pushNotifications in body.
     */
    @GetMapping("/push-notifications")
    public ResponseEntity<List<PushNotification>> getAllPushNotifications(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PushNotifications");
        Page<PushNotification> page = pushNotificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /push-notifications/:id} : get the "id" pushNotification.
     *
     * @param id the id of the pushNotificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pushNotificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/push-notifications/{id}")
    public ResponseEntity<PushNotification> getPushNotification(@PathVariable UUID id) {
        log.debug("REST request to get PushNotification : {}", id);
        Optional<PushNotification> pushNotificationDTO = pushNotificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pushNotificationDTO);
    }
}
