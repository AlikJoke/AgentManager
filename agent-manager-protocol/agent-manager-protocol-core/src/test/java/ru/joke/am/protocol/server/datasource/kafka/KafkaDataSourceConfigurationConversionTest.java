package ru.joke.am.protocol.server.datasource.kafka;

import static ru.joke.am.protocol.spi.std_impl.JNSMessageConverter.CONTENT_TYPE;

class KafkaDataSourceConfigurationConversionTest extends KafkaDataSourceConfigurationConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}
