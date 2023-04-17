package utm.tmps.service;

import org.springframework.stereotype.Service;
import utm.tmps.domain.Authority;
import utm.tmps.web.rest.errors.NotFoundException;

import java.util.UUID;

import static utm.tmps.security.AuthoritiesConstants.ADMIN;

@Service
public class ProxyUserTagManagement implements UserTagManagement {
    private final TagService tagService;
    private final UserService userService;

    public ProxyUserTagManagement(TagService tagService, UserService userService) {
        this.tagService = tagService;
        this.userService = userService;
    }

    @Override
    public void delete(UUID id) {
        var currentUser = userService.getCurrentAuthenticatedUser();
        var tag = tagService.findTag(id);
        if (tag.isEmpty()) {
            throw new NotFoundException("tag");
        }
        if (tag.get().getUserId().getLogin().equals(currentUser.getLogin()) ||
            currentUser.getAuthorities().stream().map(Authority::getName).toList().contains(ADMIN)) {
            tagService.delete(id);
        }
    }

}
