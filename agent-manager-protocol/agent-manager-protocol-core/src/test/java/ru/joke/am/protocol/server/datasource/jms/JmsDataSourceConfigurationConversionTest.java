package ru.joke.am.protocol.server.datasource.jms;

import static ru.joke.am.protocol.spi.std_impl.JNSMessageConverter.CONTENT_TYPE;

class JmsDataSourceConfigurationConversionTest extends JmsDataSourceConfigurationConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
