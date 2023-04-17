package utm.tmps.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
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

    @GetMapping("/tags")
    public ResponseEntity<List<TagDTO>> getAllTags() {
        log.debug("REST request to get a page of Tags");
        var page = tagService.findAll();
        return ResponseEntity.ok().body(page);
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
