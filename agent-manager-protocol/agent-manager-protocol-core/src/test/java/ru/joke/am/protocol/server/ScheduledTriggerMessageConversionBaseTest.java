package ru.joke.am.protocol.server;

import org.junit.jupiter.api.Test;
import ru.joke.am.protocol.BaseProtocolObjectConversionTest;

import static ru.joke.am.protocol.server.ScheduledTriggerMessageTest.buildTrigger;

abstract class ScheduledTriggerMessageConversionBaseTest extends BaseProtocolObjectConversionTest<ScheduledTriggerMessage> {

    @Test
    void testMessageConversion() {
        final ScheduledTriggerMessage data = buildTrigger();
        makeObjectConversionCheck(data);
    }
}
