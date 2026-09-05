package ru.joke.am.protocol.server.datasource.kafka;

import org.junit.jupiter.api.Test;
import ru.joke.am.protocol.BaseProtocolObjectConversionTest;

import java.util.UUID;

import static ru.joke.am.protocol.server.datasource.kafka.KafkaDataSourceConfigurationTest.buildKafkaDataSource;

abstract class KafkaDataSourceConfigurationConversionBaseTest extends BaseProtocolObjectConversionTest<KafkaDataSourceConfiguration> {

    @Test
    void testMessageConversion() {
        final KafkaDataSourceConfiguration data = buildKafkaDataSource(UUID.randomUUID().toString());
        makeObjectConversionCheck(data);
    }
}
