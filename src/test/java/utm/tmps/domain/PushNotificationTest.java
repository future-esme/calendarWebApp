package utm.tmps.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import utm.tmps.web.rest.TestUtil;

class PushNotificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PushNotification.class);
        PushNotification pushNotification1 = new PushNotification();
        pushNotification1.setId(UUID.randomUUID());
        PushNotification pushNotification2 = new PushNotification();
        pushNotification2.setId(pushNotification1.getId());
        assertThat(pushNotification1).isEqualTo(pushNotification2);
        pushNotification2.setId(UUID.randomUUID());
        assertThat(pushNotification1).isNotEqualTo(pushNotification2);
        pushNotification1.setId(null);
        assertThat(pushNotification1).isNotEqualTo(pushNotification2);
    }
}
