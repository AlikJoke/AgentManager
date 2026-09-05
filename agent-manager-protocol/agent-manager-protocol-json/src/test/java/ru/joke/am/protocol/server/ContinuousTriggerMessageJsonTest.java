package ru.joke.am.protocol.server;

import static ru.joke.am.protocol.spi.JsonMessageConverter.CONTENT_TYPE;

class ContinuousTriggerMessageJsonTest extends ContinuousTriggerMessageConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
