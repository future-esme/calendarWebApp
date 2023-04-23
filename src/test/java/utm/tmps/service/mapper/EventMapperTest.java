package utm.tmps.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventMapperTest {

    private EventMapper eventMapper;

    @BeforeEach
    public void setUp() {
        eventMapper = new EventMapperImpl();
    }
}
