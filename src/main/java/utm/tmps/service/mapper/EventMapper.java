package utm.tmps.service.mapper;

import org.mapstruct.*;
import utm.tmps.domain.Event;
import utm.tmps.domain.Tag;
import utm.tmps.domain.User;
import utm.tmps.service.dto.EventDTO;
import utm.tmps.service.dto.TagDTO;
import utm.tmps.service.dto.UserDTO;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventMapper extends EntityMapper<EventDTO, Event> {
    @Mapping(target = "userId", source = "userId", qualifiedByName = "userId")
    @Mapping(target = "tagId", source = "tagId", qualifiedByName = "tagId")
    EventDTO toDto(Event s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("tagId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TagDTO toDtoTagId(Tag tag);
}
