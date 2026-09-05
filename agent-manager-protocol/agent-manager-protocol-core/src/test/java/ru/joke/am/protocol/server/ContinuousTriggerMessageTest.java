package ru.joke.am.protocol.server;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.joke.am.protocol.server.AgentConfigurationTest.buildJmsDataSource;

class ContinuousTriggerMessageTest extends BaseTriggerMessageTest {

    private static final AgentConfiguration expectedAgentConfig = buildAgentConfig();

    @Test
    void testCreationWithValidState() {
        final ContinuousTriggerMessage trigger = buildTrigger();

        makeTriggerStateChecks(trigger);
        assertEquals(expectedAgentConfig, trigger.agent(), "Agent config must be equal");
        assertEquals(List.of(expectedAgentConfig), trigger.agents(), "Agent config must be equal");
    }

    @Test
    void testCreationWithInvalidState() {
        final ContinuousTriggerMessage validTrigger = buildTrigger();

        assertThrows(IllegalArgumentException.class, () -> validTrigger.toBuilder().triggerId("").build());
        assertThrows(IllegalArgumentException.class, () -> validTrigger.toBuilder().executionId("").build());
        assertThrows(IllegalArgumentException.class, () -> validTrigger.toBuilder().partitions(Collections.emptyList()).build());
    }

    static ContinuousTriggerMessage buildTrigger() {
        return ContinuousTriggerMessage.builder()
                    .triggerId(EXPECTED_TRIGGER_ID)
                    .executionId(EXPECTED_EXECUTION_ID)
                    .partitions(expectedPartitions)
                    .maxConsecutiveExceptions(EXPECTED_MAX_CONSECUTIVE_EXCEPTIONS_COUNT)
                    .agent(expectedAgentConfig)
                .build();
    }

    private static AgentConfiguration buildAgentConfig() {
        final String uuid = UUID.randomUUID().toString();
        return AgentConfiguration.builder()
                    .agentId(uuid)
                    .agentConfigId(uuid)
                    .name(uuid)
                    .retries(1)
                    .threads(2)
                    .duplicationStartupPolicy(DuplicationStartupPolicy.ALLOW)
                    .dataSource(buildJmsDataSource())
                    .parameters(Map.of(
                            "p1", UUID.randomUUID().toString(),
                            "p2", true,
                            "p3", Map.of("pn1", 2.0, "pn2", "s2", "pn3", List.of("s1", true))))
                .build();
    }
}
