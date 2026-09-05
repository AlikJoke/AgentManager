package ru.joke.am.protocol.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StopTriggerMessageTest {

    @Test
    void testCreationWithValidState() {
        final StopTriggerMessage message = new StopTriggerMessage("t1");

        assertNotNull(message, "Object to check must be not null");
        assertEquals("t1", message.triggerId(), "Trigger id must be equal");
    }

    @Test
    void testCreationWithInvalidState() {
        assertThrows(IllegalArgumentException.class, () -> new StopTriggerMessage(""));
    }
}
