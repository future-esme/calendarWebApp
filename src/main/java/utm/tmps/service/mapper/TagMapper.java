package utm.tmps.service.mapper;

import org.mapstruct.*;
import utm.tmps.domain.Tag;
import utm.tmps.domain.User;
import utm.tmps.service.dto.TagDTO;
import utm.tmps.service.dto.UserDTO;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {
    @Mapping(target = "userId", source = "userId", qualifiedByName = "userId")
    TagDTO toDto(Tag s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
