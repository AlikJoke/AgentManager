package ru.joke.am.protocol.client;

import static ru.joke.am.protocol.spi.OneNioMessageConverter.CONTENT_TYPE;

class AgentExecutionStatusMessageOneNioConversionTest extends AgentExecutionStatusMessageConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
