package ru.joke.am.protocol.server;

import org.junit.jupiter.api.Test;
import ru.joke.am.protocol.BaseProtocolObjectConversionTest;

import static ru.joke.am.protocol.server.AgentConfigurationTest.*;

abstract class AgentConfigurationConversionBaseTest extends BaseProtocolObjectConversionTest<AgentConfiguration> {

    @Test
    void testMessageConversionWithJmsDataSource() {
        final AgentConfiguration data = buildConfig(buildJmsDataSource());
        makeObjectConversionCheck(data);
    }

    @Test
    void testMessageConversionWithKafkaDataSource() {
        final AgentConfiguration data = buildConfig(buildKafkaDataSource());
        makeObjectConversionCheck(data);
    }

    @Test
    void testMessageConversionWithoutDataSource() {
        final AgentConfiguration data = buildConfig(null);
        makeObjectConversionCheck(data);
    }
}
