package ru.joke.am.protocol.client;

import org.junit.jupiter.api.Test;
import ru.joke.am.protocol.BaseProtocolObjectConversionTest;

import java.time.ZonedDateTime;
import java.util.UUID;

abstract class AgentExecutionStatusMessageConversionBaseTest extends BaseProtocolObjectConversionTest<AgentExecutionStatusMessage> {

    @Test
    void testMessageConversion() {
        final AgentExecutionStatusMessage data =
                AgentExecutionStatusMessage.builder()
                            .status(AgentExecutionStatus.ERROR)
                            .executionId(UUID.randomUUID().toString())
                            .triggerId(UUID.randomUUID().toString())
                            .agentConfigId(UUID.randomUUID().toString())
                            .info(UUID.randomUUID().toString())
                            .application("app1")
                            .host("test")
                            .port(8081)
                            .messageDateTime(ZonedDateTime.now())
                        .build();
        makeObjectConversionCheck(data);
    }
}
