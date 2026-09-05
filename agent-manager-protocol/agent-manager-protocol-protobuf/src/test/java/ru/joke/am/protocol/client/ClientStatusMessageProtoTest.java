package ru.joke.am.protocol.client;

import static ru.joke.am.protocol.spi.ProtobufMessageConverter.CONTENT_TYPE;

class ClientStatusMessageProtoTest extends ClientStatusMessageConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
