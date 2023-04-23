package utm.tmps.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import utm.tmps.web.rest.TestUtil;

class UserSettingsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSettingsDTO.class);
        UserSettingsDTO userSettingsDTO1 = new UserSettingsDTO();
        userSettingsDTO1.setId(UUID.randomUUID());
        UserSettingsDTO userSettingsDTO2 = new UserSettingsDTO();
        assertThat(userSettingsDTO1).isNotEqualTo(userSettingsDTO2);
        userSettingsDTO2.setId(userSettingsDTO1.getId());
        assertThat(userSettingsDTO1).isEqualTo(userSettingsDTO2);
        userSettingsDTO2.setId(UUID.randomUUID());
        assertThat(userSettingsDTO1).isNotEqualTo(userSettingsDTO2);
        userSettingsDTO1.setId(null);
        assertThat(userSettingsDTO1).isNotEqualTo(userSettingsDTO2);
    }
}
