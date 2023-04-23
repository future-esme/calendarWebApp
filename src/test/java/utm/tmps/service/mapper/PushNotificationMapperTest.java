package utm.tmps.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PushNotificationMapperTest {

    private PushNotificationMapper pushNotificationMapper;

    @BeforeEach
    public void setUp() {
        pushNotificationMapper = new PushNotificationMapperImpl();
    }
}
