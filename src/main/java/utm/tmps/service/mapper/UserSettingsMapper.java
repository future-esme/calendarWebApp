package utm.tmps.service.mapper;

import org.mapstruct.*;
import utm.tmps.domain.User;
import utm.tmps.domain.UserSettings;
import utm.tmps.service.dto.UserDTO;
import utm.tmps.service.dto.UserSettingsDTO;

/**
 * Mapper for the entity {@link UserSettings} and its DTO {@link UserSettingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserSettingsMapper extends EntityMapper<UserSettingsDTO, UserSettings> {
    @Mapping(target = "userId", source = "userId", qualifiedByName = "userId")
    UserSettingsDTO toDto(UserSettings s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
