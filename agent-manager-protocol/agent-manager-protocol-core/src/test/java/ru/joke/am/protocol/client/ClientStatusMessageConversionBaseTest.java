package ru.joke.am.protocol.client;

import org.junit.jupiter.api.Test;
import ru.joke.am.protocol.BaseProtocolObjectConversionTest;

import java.time.ZonedDateTime;

abstract class ClientStatusMessageConversionBaseTest extends BaseProtocolObjectConversionTest<ClientStatusMessage> {

    @Test
    void testMessageConversion() {
        final ClientStatusMessage data = ClientStatusMessage.builder()
                    .status(ClientStatus.ALIVE)
                    .application("app1")
                    .host("test")
                    .port(8082)
                    .messageDateTime(ZonedDateTime.now())
                .build();
        makeObjectConversionCheck(data);
    }
}
