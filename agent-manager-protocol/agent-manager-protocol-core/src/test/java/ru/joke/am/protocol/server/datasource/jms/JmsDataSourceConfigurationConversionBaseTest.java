package ru.joke.am.protocol.server.datasource.jms;

import org.junit.jupiter.api.Test;
import ru.joke.am.protocol.BaseProtocolObjectConversionTest;

import static ru.joke.am.protocol.server.datasource.jms.JmsDataSourceConfigurationTest.*;

abstract class JmsDataSourceConfigurationConversionBaseTest extends BaseProtocolObjectConversionTest<JmsDataSourceConfiguration> {

    @Test
    void testMessageConversion() {
        final JmsDataSourceConfiguration data = buildJmsDataSource(EXPECTED_CONNECTION_FACTORY, EXPECTED_DESTINATION);
        makeObjectConversionCheck(data);
    }
}
