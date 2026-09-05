package ru.joke.am.protocol.server;

import static ru.joke.am.protocol.spi.std_impl.JNSMessageConverter.CONTENT_TYPE;

class StopTriggerMessageConversionTest extends StopTriggerMessageConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
