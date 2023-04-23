package utm.tmps.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.tmps.domain.Tag;
import utm.tmps.repository.TagRepository;
import utm.tmps.service.dto.TagCreateDTO;
import utm.tmps.service.dto.TagDTO;
import utm.tmps.service.mapper.TagMapper;

/**
 * Service Implementation for managing {@link Tag}.
 */
@Service
@Transactional
public class TagService implements UserTagManagement {

    private final Logger log = LoggerFactory.getLogger(TagService.class);

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    private final UserService userService;

    public TagService(TagRepository tagRepository,
                      TagMapper tagMapper,
                      UserService userService) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.userService = userService;
    }

    /**
     * Save a tag.
     *
     * @param tagDTO the entity to save.
     * @return the persisted entity.
     */
    public TagDTO save(TagDTO tagDTO) {
        log.debug("Request to save Tag : {}", tagDTO);
        Tag tag = tagMapper.toEntity(tagDTO);
        tag = tagRepository.save(tag);
        return tagMapper.toDto(tag);
    }

    public TagDTO createTagForCurrentUser(TagCreateDTO tagDTO) {
        log.debug("Request to save Tag : {}", tagDTO);
        Tag tag = new Tag();
        tag.setColor(tagDTO.getColor());
        tag.setName(tagDTO.getName());
        tag.setIconName(tagDTO.getIconName());
        tag.setUserId(userService.getCurrentAuthenticatedUser());
        tag = tagRepository.save(tag);
        return tagMapper.toDto(tag);
    }

    /**
     * Get all the tags.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TagDTO> findAllCurrentUser(Pageable pageable) {
        log.debug("Request to get all Tags");
        return tagRepository.findAllByUserId(userService.getCurrentAuthenticatedUser(), pageable)
            .map(tagMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<TagDTO> findAllCurrentUser() {
        log.debug("Request to get all Tags");
        return tagRepository.findAllByUserId(userService.getCurrentAuthenticatedUser())
            .stream()
            .map(tagMapper::toDto)
            .toList();
    }

    /**
     * Update a tag.
     *
     * @param tagDTO the entity to save.
     * @return the persisted entity.
     */
    public TagDTO update(TagDTO tagDTO) {
        log.debug("Request to update Tag : {}", tagDTO);
        Tag tag = tagMapper.toEntity(tagDTO);
        tag = tagRepository.save(tag);
        return tagMapper.toDto(tag);
    }

    /**
     * Partially update a tag.
     *
     * @param tagDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TagDTO> partialUpdate(TagDTO tagDTO) {
        log.debug("Request to partially update Tag : {}", tagDTO);

        return tagRepository
            .findById(tagDTO.getId())
            .map(existingTag -> {
                tagMapper.partialUpdate(existingTag, tagDTO);

                return existingTag;
            })
            .map(tagRepository::save)
            .map(tagMapper::toDto);
    }

    /**
     * Get all the tags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tags");
        return tagRepository.findAll(pageable).map(tagMapper::toDto);
    }

    /**
     * Get one tag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TagDTO> findOne(UUID id) {
        log.debug("Request to get Tag : {}", id);
        return tagRepository.findById(id).map(tagMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<Tag> findTag(UUID id) {
        log.debug("Request to get Tag : {}", id);
        return tagRepository.findById(id);
    }

    /**
     * Delete the tag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Tag : {}", id);
        tagRepository.deleteById(id);
    }
}
