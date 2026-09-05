package ru.joke.am.protocol.server;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class BaseTriggerMessageTest {

    protected static final String EXPECTED_TRIGGER_ID = "t1";
    protected static final String EXPECTED_EXECUTION_ID = UUID.randomUUID().toString();
    protected static final int EXPECTED_MAX_CONSECUTIVE_EXCEPTIONS_COUNT = 5;
    protected static final List<Partition> expectedPartitions = List.of(
            new Partition("h1", 1),
            new Partition("h2", 2)
    );

    protected void makeTriggerStateChecks(final TriggerMessage triggerMessage) {
        assertNotNull(triggerMessage, "Object to check must be not null");
        assertEquals(EXPECTED_TRIGGER_ID, triggerMessage.triggerId(), "Trigger id must be equal");
        assertEquals(EXPECTED_EXECUTION_ID, triggerMessage.executionId(), "Execution id must be equal");
        assertEquals(EXPECTED_MAX_CONSECUTIVE_EXCEPTIONS_COUNT, triggerMessage.maxConsecutiveExceptions(), "Max consecutive exceptions count must be equal");
        assertEquals(expectedPartitions, triggerMessage.partitions(), "Partitions must be equal");
    }
}
