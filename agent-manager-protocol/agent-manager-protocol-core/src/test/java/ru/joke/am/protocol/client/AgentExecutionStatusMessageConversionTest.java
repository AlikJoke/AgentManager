package ru.joke.am.protocol.client;

import static ru.joke.am.protocol.spi.std_impl.JNSMessageConverter.CONTENT_TYPE;

class AgentExecutionStatusMessageConversionTest extends AgentExecutionStatusMessageConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
