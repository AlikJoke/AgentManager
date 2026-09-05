package ru.joke.am.protocol.server;

import static ru.joke.am.protocol.spi.JsonMessageConverter.CONTENT_TYPE;

class ScheduledTriggerMessageJsonTest extends ScheduledTriggerMessageConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}