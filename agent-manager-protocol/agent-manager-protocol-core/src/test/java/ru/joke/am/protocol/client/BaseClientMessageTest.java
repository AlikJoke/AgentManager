package ru.joke.am.protocol.client;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class BaseClientMessageTest<T extends ClientMessage> {

    static final String EXPECTED_HOST = "h1";
    static final String EXPECTED_APPLICATION = "app1";
    static final int EXPECTED_PORT = 8443;
    static final ZonedDateTime EXPECTED_MESSAGE_DT = ZonedDateTime.now(ZoneId.systemDefault()).minusDays(1);

    @Test
    void testCreationWithValidMessageState() {
        final T message = createTestObject(EXPECTED_PORT);

        assertNotNull(message, "Object to check must be not null");
        assertEquals(EXPECTED_APPLICATION, message.application(), "Client application name must be equal");
        assertEquals(EXPECTED_HOST, message.host(), "Client host must be equal");
        assertEquals(EXPECTED_PORT, message.port(), "Client port must be equal");
        assertEquals(EXPECTED_MESSAGE_DT, message.messageDateTime(), "Message date-time must be equal");

        makeStateChecks(message);
    }

    abstract T createTestObject(final int port);

    abstract void makeStateChecks(T obj);
}
