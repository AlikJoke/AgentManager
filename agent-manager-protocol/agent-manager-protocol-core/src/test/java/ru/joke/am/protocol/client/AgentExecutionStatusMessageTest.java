package ru.joke.am.protocol.client;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AgentExecutionStatusMessageTest extends BaseClientMessageTest<AgentExecutionStatusMessage> {

    private static final AgentExecutionStatus EXPECTED_STATUS = AgentExecutionStatus.ERROR;
    private static final String EXPECTED_EXECUTION_ID = UUID.randomUUID().toString();
    private static final String EXPECTED_TRIGGER_ID = "t1";
    private static final String EXPECTED_AGENT_CONFIG_ID = "ac1";
    private static final String EXPECTED_INFO = "-";

    @Test
    void testCreationWithInvalidState() {
        final AgentExecutionStatusMessage message = createTestObject(0);
        assertThrows(IllegalArgumentException.class, () -> message.toBuilder().port(-2).build());
        assertThrows(IllegalArgumentException.class, () -> message.toBuilder().host("").build());
        assertThrows(IllegalArgumentException.class, () -> message.toBuilder().application("").build());
        assertThrows(IllegalArgumentException.class, () -> message.toBuilder().executionId("").build());
        assertThrows(IllegalArgumentException.class, () -> message.toBuilder().triggerId("").build());
        assertThrows(IllegalArgumentException.class, () -> message.toBuilder().agentConfigId("").build());
    }

    @Override
    void makeStateChecks(AgentExecutionStatusMessage message) {
        assertEquals(EXPECTED_STATUS, message.status(), "Agent status must be equal");
        assertEquals(EXPECTED_AGENT_CONFIG_ID, message.agentConfigId(), "Agent config id must be equal");
        assertEquals(EXPECTED_TRIGGER_ID, message.triggerId(), "Trigger id must be equal");
        assertEquals(EXPECTED_EXECUTION_ID, message.executionId(), "Execution id must be equal");
        assertEquals(EXPECTED_INFO, message.info(), "Execution add info must be equal");
    }

    @Override
    AgentExecutionStatusMessage createTestObject(final int port) {
        return AgentExecutionStatusMessage.builder()
                    .status(EXPECTED_STATUS)
                    .executionId(EXPECTED_EXECUTION_ID)
                    .triggerId(EXPECTED_TRIGGER_ID)
                    .agentConfigId(EXPECTED_AGENT_CONFIG_ID)
                    .info(EXPECTED_INFO)
                    .application(EXPECTED_APPLICATION)
                    .host(EXPECTED_HOST)
                    .port(port)
                    .messageDateTime(EXPECTED_MESSAGE_DT)
                .build();
    }
}
