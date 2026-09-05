package ru.joke.am.protocol.server;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.joke.am.protocol.server.AgentConfigurationTest.buildKafkaDataSource;

class ScheduledTriggerMessageTest extends BaseTriggerMessageTest {

    private static final int EXPECTED_MAX_EXECUTION_TIME = 150;
    private static final List<AgentConfiguration> expectedAgentConfigs = List.of(
            buildAgentConfig("1"),
            buildAgentConfig("2")
    );

    @Test
    void testCreationWithValidState() {
        final ScheduledTriggerMessage trigger = buildTrigger();

        makeTriggerStateChecks(trigger);
        assertEquals(EXPECTED_MAX_EXECUTION_TIME, trigger.maxExecutionTime(), "Max execution time must be equal");
        assertEquals(expectedAgentConfigs, trigger.agents(), "Agent configs must be equal");
    }

    @Test
    void testCreationWithInvalidState() {
        final ScheduledTriggerMessage validTrigger = buildTrigger();

        assertThrows(IllegalArgumentException.class, () -> validTrigger.toBuilder().triggerId("").build());
        assertThrows(IllegalArgumentException.class, () -> validTrigger.toBuilder().executionId("").build());
        assertThrows(IllegalArgumentException.class, () -> validTrigger.toBuilder().maxExecutionTime(-1).build());
        assertThrows(IllegalArgumentException.class, () -> validTrigger.toBuilder().partitions(Collections.emptyList()).build());
        assertThrows(IllegalArgumentException.class, () -> validTrigger.toBuilder().agents(Collections.emptyList()).build());
    }

    static ScheduledTriggerMessage buildTrigger() {
        return ScheduledTriggerMessage.builder()
                    .triggerId(EXPECTED_TRIGGER_ID)
                    .executionId(EXPECTED_EXECUTION_ID)
                    .maxExecutionTime(EXPECTED_MAX_EXECUTION_TIME)
                    .partitions(expectedPartitions)
                    .maxConsecutiveExceptions(EXPECTED_MAX_CONSECUTIVE_EXCEPTIONS_COUNT)
                    .agents(expectedAgentConfigs)
                .build();
    }

    private static AgentConfiguration buildAgentConfig(final String id) {
        return AgentConfiguration.builder()
                    .agentId("agent_" + id)
                    .agentConfigId("ac_" + id)
                    .name("title_" + id)
                    .retries(1)
                    .threads(2)
                    .dataSource(buildKafkaDataSource())
                    .duplicationStartupPolicy(DuplicationStartupPolicy.ALLOW)
                    .parameters(Collections.emptyMap())
                .build();
    }
}
