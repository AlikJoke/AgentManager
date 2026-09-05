package ru.joke.am.protocol.server.datasource.jms;

import static ru.joke.am.protocol.spi.JsonMessageConverter.CONTENT_TYPE;

class JmsDataSourceConfigurationJsonTest extends JmsDataSourceConfigurationConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
