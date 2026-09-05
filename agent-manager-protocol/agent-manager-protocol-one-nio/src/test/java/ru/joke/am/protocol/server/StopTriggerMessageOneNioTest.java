package ru.joke.am.protocol.server;

import static ru.joke.am.protocol.spi.OneNioMessageConverter.CONTENT_TYPE;

class StopTriggerMessageOneNioTest extends StopTriggerMessageConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}