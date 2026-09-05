package ru.joke.am.protocol.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientStatusMessageTest extends BaseClientMessageTest<ClientStatusMessage> {

    private static final ClientStatus EXPECTED_STATUS = ClientStatus.STARTED;

    @Test
    void testCreationWithInvalidState() {
        final ClientStatusMessage message = createTestObject(0);
        assertThrows(IllegalArgumentException.class, () -> message.toBuilder().port(-2).build());
        assertThrows(IllegalArgumentException.class, () -> message.toBuilder().host("").build());
        assertThrows(IllegalArgumentException.class, () -> message.toBuilder().application("").build());
    }

    @Override
    void makeStateChecks(ClientStatusMessage message) {
        assertEquals(EXPECTED_STATUS, message.status(), "Client status must be equal");
    }

    @Override
    ClientStatusMessage createTestObject(final int port) {
        return ClientStatusMessage.builder()
                    .status(EXPECTED_STATUS)
                    .application(EXPECTED_APPLICATION)
                    .host(EXPECTED_HOST)
                    .port(port)
                    .messageDateTime(EXPECTED_MESSAGE_DT)
                .build();
    }
}
