package ru.joke.am.protocol.server.datasource.kafka;

import static ru.joke.am.protocol.spi.JsonMessageConverter.CONTENT_TYPE;

class KafkaDataSourceConfigurationJsonTest extends KafkaDataSourceConfigurationConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}