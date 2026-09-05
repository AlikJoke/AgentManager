package ru.joke.am.protocol.client;

import static ru.joke.am.protocol.spi.OneNioMessageConverter.CONTENT_TYPE;

class ClientStatusMessageOneNioConversionTest extends ClientStatusMessageConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
