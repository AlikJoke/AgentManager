package ru.joke.am.protocol.server.datasource.jms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JmsDataSourceConfigurationTest {

    static final String EXPECTED_CONNECTION_FACTORY = "cf";
    static final String EXPECTED_DESTINATION = "dst";
    static final String EXPECTED_SELECTOR = "p1=1";
    static final String EXPECTED_SUBSCRIPTION_ID = "s1";
    static final DestinationType EXPECTED_DESTINATION_TYPE = DestinationType.TOPIC;
    static final boolean EXPECTED_DURABLE = true;

    @Test
    void testCreationWithValidState() {
        final JmsDataSourceConfiguration config = buildJmsDataSource(EXPECTED_CONNECTION_FACTORY, EXPECTED_DESTINATION);

        assertNotNull(config, "Object to check must be not null");
        assertEquals(EXPECTED_CONNECTION_FACTORY, config.connectionFactory(), "Connection factory must be equal");
        assertEquals(EXPECTED_DESTINATION, config.destination(), "Destination must be equal");
        assertEquals(EXPECTED_SELECTOR, config.messageSelector(), "Selector must be equal");
        assertEquals(EXPECTED_SUBSCRIPTION_ID, config.subscriptionId(), "Subscription id must be equal");
        assertEquals(EXPECTED_DESTINATION_TYPE, config.destinationType(), "Destination type must be equal");
        assertTrue(config.isDurable(), "Durable property must be equal");
    }

    @Test
    void testCreationWithInvalidState() {
        assertThrows(IllegalArgumentException.class, () -> buildJmsDataSource("", EXPECTED_DESTINATION));
        assertThrows(IllegalArgumentException.class, () -> buildJmsDataSource(EXPECTED_CONNECTION_FACTORY, ""));
    }

    static JmsDataSourceConfiguration buildJmsDataSource(
            final String expectedConnectionFactory,
            final String expectedDestination
    ) {
        return JmsDataSourceConfiguration.builder()
                    .connectionFactory(expectedConnectionFactory)
                    .destination(expectedDestination)
                    .isDurable(EXPECTED_DURABLE)
                    .messageSelector(EXPECTED_SELECTOR)
                    .subscriptionId(EXPECTED_SUBSCRIPTION_ID)
                    .destinationType(EXPECTED_DESTINATION_TYPE)
                .build();
    }
}
