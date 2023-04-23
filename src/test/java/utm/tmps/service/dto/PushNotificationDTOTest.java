package utm.tmps.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import utm.tmps.web.rest.TestUtil;

class PushNotificationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PushNotificationDTO.class);
        PushNotificationDTO pushNotificationDTO1 = new PushNotificationDTO();
        pushNotificationDTO1.setId(UUID.randomUUID());
        PushNotificationDTO pushNotificationDTO2 = new PushNotificationDTO();
        assertThat(pushNotificationDTO1).isNotEqualTo(pushNotificationDTO2);
        pushNotificationDTO2.setId(pushNotificationDTO1.getId());
        assertThat(pushNotificationDTO1).isEqualTo(pushNotificationDTO2);
        pushNotificationDTO2.setId(UUID.randomUUID());
        assertThat(pushNotificationDTO1).isNotEqualTo(pushNotificationDTO2);
        pushNotificationDTO1.setId(null);
        assertThat(pushNotificationDTO1).isNotEqualTo(pushNotificationDTO2);
    }
}
