package ru.joke.am.protocol.server.datasource.jms;

import static ru.joke.am.protocol.spi.OneNioMessageConverter.CONTENT_TYPE;

class JmsDataSourceConfigurationOneNioTest extends JmsDataSourceConfigurationConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
