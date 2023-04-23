package utm.tmps.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import utm.tmps.domain.Tag;
import utm.tmps.service.ProxyUserTagManagement;
import utm.tmps.service.TagService;
import utm.tmps.service.dto.TagCreateDTO;
import utm.tmps.service.dto.TagDTO;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class TagUserResource {
    private final Logger log = LoggerFactory.getLogger(TagUserResource.class);
    private static final String ENTITY_NAME = "tag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagService tagService;

    private final ProxyUserTagManagement proxyUserTagManagement;

    public TagUserResource(TagService tagService, ProxyUserTagManagement proxyUserTagManagement) {
        this.tagService = tagService;
        this.proxyUserTagManagement = proxyUserTagManagement;
    }

    @PostMapping("/tags")
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagCreateDTO tagDTO) {
        log.debug("REST request to save Tag : {}", tagDTO);
        TagDTO result = tagService.createTagForCurrentUser(tagDTO);
        return ResponseEntity
            .status(201)
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/tags/all")
    public ResponseEntity<List<TagDTO>> getAllTags() {
        log.debug("REST request to get a page of Tags");
        var page = tagService.findAllCurrentUser();
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<TagDTO>> getTags(Pageable pageable) {
        log.debug("REST request to get a page of Tags");
        var page = tagService.findAllCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable UUID id) {
        log.debug("REST request to get Tag : {}", id);
        var tag = proxyUserTagManagement.findTag(id);
        return ResponseUtil.wrapOrNotFound(tag);
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        log.debug("REST request to delete Tag : {}", id);
        proxyUserTagManagement.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
