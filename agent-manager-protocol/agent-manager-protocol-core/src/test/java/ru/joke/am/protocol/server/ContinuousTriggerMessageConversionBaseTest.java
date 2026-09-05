package ru.joke.am.protocol.server;

import org.junit.jupiter.api.Test;
import ru.joke.am.protocol.BaseProtocolObjectConversionTest;

import static ru.joke.am.protocol.server.ContinuousTriggerMessageTest.buildTrigger;

abstract class ContinuousTriggerMessageConversionBaseTest extends BaseProtocolObjectConversionTest<ContinuousTriggerMessage> {

    @Test
    void testMessageConversion() {
        final ContinuousTriggerMessage data = buildTrigger();
        makeObjectConversionCheck(data);
    }
}
