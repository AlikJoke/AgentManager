package ru.joke.am.protocol.server;

import static ru.joke.am.protocol.spi.ProtobufMessageConverter.CONTENT_TYPE;

class ContinuousTriggerMessageProtoTest extends ContinuousTriggerMessageConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
