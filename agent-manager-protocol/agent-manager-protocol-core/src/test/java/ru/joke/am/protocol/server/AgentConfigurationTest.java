package ru.joke.am.protocol.server;

import org.junit.jupiter.api.Test;
import ru.joke.am.protocol.server.datasource.jms.DestinationType;
import ru.joke.am.protocol.server.datasource.jms.JmsDataSourceConfiguration;
import ru.joke.am.protocol.server.datasource.kafka.KafkaDataSourceConfiguration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AgentConfigurationTest {

    private static final String EXPECTED_AGENT_ID = "a1";
    private static final String EXPECTED_AGENT_CONFIG_ID = "ac1";
    private static final String EXPECTED_TITLE = "test";
    private static final int EXPECTED_RETRY_COUNT = 2;
    private static final int EXPECTED_THREAD_COUNT = 3;
    private static final DuplicationStartupPolicy EXPECTED_DUPLICATION_STARTUP_POLICY = DuplicationStartupPolicy.INTERRUPT_EXISTING;
    private static final Map<String, Object> expectedParams = Map.of("p1", "1", "p2", 2);

    @Test
    void testCreationWithInvalidState() {
        final AgentConfiguration config = buildConfig(null);
        assertThrows(IllegalArgumentException.class, () -> config.toBuilder().agentId("").build());
        assertThrows(IllegalArgumentException.class, () -> config.toBuilder().name("").build());
        assertThrows(IllegalArgumentException.class, () -> config.toBuilder().agentConfigId("").build());
        assertThrows(IllegalArgumentException.class, () -> config.toBuilder().threads(0).build());
        assertThrows(IllegalArgumentException.class, () -> config.toBuilder().threads(-1).build());
        assertThrows(IllegalArgumentException.class, () -> config.toBuilder().retries(-1).build());
    }

    @Test
    void testCreationWithValidStateAndJmsDataSource() {
        makeCreationWithValidStateChecks(buildJmsDataSource());
    }

    @Test
    void testCreationWithValidStateAndKafkaDataSource() {
        makeCreationWithValidStateChecks(buildKafkaDataSource());
    }

    @Test
    void testCreationWithValidStateWithoutDataSource() {
        makeCreationWithValidStateChecks(null);
    }

    static KafkaDataSourceConfiguration buildKafkaDataSource() {
        return KafkaDataSourceConfiguration.builder()
                    .subscriptionId("s1")
                    .consumerProperties(Map.of("bootstrapServers", "test:8081,test2:8082"))
                    .destination("d2")
                    .messageSelector("p1=1")
                .build();
    }

    static DataSourceConfiguration buildJmsDataSource() {
        return JmsDataSourceConfiguration.builder()
                    .connectionFactory("cf")
                    .destination("d1")
                    .isDurable(true)
                    .messageSelector("p1=1")
                    .destinationType(DestinationType.TOPIC)
                .build();
    }

    static AgentConfiguration buildConfig(final DataSourceConfiguration expectedDataSourceConfig) {
        return AgentConfiguration.builder()
                    .agentId(EXPECTED_AGENT_ID)
                    .agentConfigId(EXPECTED_AGENT_CONFIG_ID)
                    .name(EXPECTED_TITLE)
                    .retries(EXPECTED_RETRY_COUNT)
                    .threads(EXPECTED_THREAD_COUNT)
                    .duplicationStartupPolicy(EXPECTED_DUPLICATION_STARTUP_POLICY)
                    .parameters(expectedParams)
                    .dataSource(expectedDataSourceConfig)
                .build();
    }

    private void makeCreationWithValidStateChecks(final DataSourceConfiguration expectedDataSourceConfig) {
        final AgentConfiguration config = buildConfig(expectedDataSourceConfig);

        assertNotNull(config, "Object to check must be not null");
        assertEquals(EXPECTED_AGENT_CONFIG_ID, config.agentConfigId(), "Agent config id must be equal");
        assertEquals(EXPECTED_AGENT_ID, config.agentId(), "Agent id must be equal");
        assertEquals(EXPECTED_TITLE, config.name(), "Name must be equal");
        assertEquals(EXPECTED_RETRY_COUNT, config.retries(), "Retry count must be equal");
        assertEquals(EXPECTED_THREAD_COUNT, config.threads(), "Thread count must be equal");
        assertEquals(EXPECTED_DUPLICATION_STARTUP_POLICY, config.duplicationStartupPolicy(), "Duplication startup policy must be equal");
        assertEquals(expectedParams, config.parameters(), "Config parameters must be equal");
        assertEquals(expectedDataSourceConfig, config.dataSource(), "Datasource config must be equal");
    }
}
