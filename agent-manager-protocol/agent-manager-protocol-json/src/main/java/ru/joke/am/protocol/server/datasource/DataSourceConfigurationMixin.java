package ru.joke.am.protocol.server.datasource;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.joke.am.protocol.server.datasource.jms.JmsDataSourceConfiguration;
import ru.joke.am.protocol.server.datasource.kafka.KafkaDataSourceConfiguration;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = JmsDataSourceConfiguration.class,
                name = "jms"
        ),
        @JsonSubTypes.Type(
                value = KafkaDataSourceConfiguration.class,
                name = "kafka"
        )
})
public abstract class DataSourceConfigurationMixin {
}