package ru.joke.am.protocol.client;

import static ru.joke.am.protocol.spi.JsonMessageConverter.CONTENT_TYPE;

class ClientStatusMessageJsonConversionTest extends ClientStatusMessageConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
