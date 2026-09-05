package ru.joke.am.protocol.server;

import static ru.joke.am.protocol.spi.ProtobufMessageConverter.CONTENT_TYPE;

class StopTriggerMessageProtoTest extends StopTriggerMessageConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}