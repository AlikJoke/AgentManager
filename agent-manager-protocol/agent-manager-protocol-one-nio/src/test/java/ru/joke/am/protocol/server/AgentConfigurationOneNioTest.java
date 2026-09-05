package ru.joke.am.protocol.server;

import static ru.joke.am.protocol.spi.OneNioMessageConverter.CONTENT_TYPE;

class AgentConfigurationOneNioTest extends AgentConfigurationConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
