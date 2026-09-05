package ru.joke.am.protocol.server.datasource.kafka;

import static ru.joke.am.protocol.spi.OneNioMessageConverter.CONTENT_TYPE;

class KafkaDataSourceConfigurationOneNioTest extends KafkaDataSourceConfigurationConversionBaseTest {

    @Override
    protected String targetDataType() {
        return CONTENT_TYPE;
    }
}