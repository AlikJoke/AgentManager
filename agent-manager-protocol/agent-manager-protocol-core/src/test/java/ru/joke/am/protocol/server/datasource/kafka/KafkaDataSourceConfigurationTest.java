package ru.joke.am.protocol.server.datasource.kafka;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class KafkaDataSourceConfigurationTest {

    static final String EXPECTED_DESTINATION = "dst";
    static final String EXPECTED_SELECTOR = "p1=1";
    static final String EXPECTED_SUBSCRIPTION_ID = "s1";
    static final Map<String, Object> expectedConsumerProperties = Map.of("p1", "1", "p2", true);

    @Test
    void testCreationWithValidState() {
        final KafkaDataSourceConfiguration config = buildKafkaDataSource(EXPECTED_SUBSCRIPTION_ID);

        assertNotNull(config, "Object to check must be not null");
        assertEquals(expectedConsumerProperties, config.consumerProperties(), "Consumer properties must be equal");
        assertEquals(EXPECTED_DESTINATION, config.destination(), "Destination must be equal");
        assertEquals(EXPECTED_SELECTOR, config.messageSelector(), "Selector must be equal");
        assertEquals(EXPECTED_SUBSCRIPTION_ID, config.subscriptionId(), "Subscription id must be equal");
    }

    @Test
    void testCreationWithInvalidState() {
        assertThrows(IllegalArgumentException.class, () -> buildKafkaDataSource(""));
    }

    static KafkaDataSourceConfiguration buildKafkaDataSource(final String expectedSubscriptionId) {
        return KafkaDataSourceConfiguration.builder()
                    .consumerProperties(expectedConsumerProperties)
                    .destination(EXPECTED_DESTINATION)
                    .messageSelector(EXPECTED_SELECTOR)
                    .subscriptionId(expectedSubscriptionId)
                .build();
    }
}
