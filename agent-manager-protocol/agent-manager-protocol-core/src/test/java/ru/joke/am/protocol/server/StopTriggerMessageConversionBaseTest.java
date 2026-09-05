package ru.joke.am.protocol.server;

import org.junit.jupiter.api.Test;
import ru.joke.am.protocol.BaseProtocolObjectConversionTest;

import java.util.UUID;

abstract class StopTriggerMessageConversionBaseTest extends BaseProtocolObjectConversionTest<StopTriggerMessage> {

    @Test
    void testMessageConversion() {
        final StopTriggerMessage data = new StopTriggerMessage(UUID.randomUUID().toString());
        makeObjectConversionCheck(data);
    }
}
