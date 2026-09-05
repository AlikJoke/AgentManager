package ru.joke.am.protocol.spi.std_impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.NonNull;
import ru.joke.am.protocol.server.DataSourceConfiguration;
import ru.joke.am.protocol.server.datasource.DataSourceConfigurationMixin;
import ru.joke.am.protocol.spi.JsonMapperFactory;
import tools.jackson.core.Version;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectReader;
import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DefaultJsonMapperFactory implements JsonMapperFactory {

    private final Map<Class<?>, ObjectWriter> writers;
    private final Map<Class<?>, ObjectReader> readers;
    private final ObjectMapper mapper;

    public DefaultJsonMapperFactory() {

        final SimpleModule module = new SimpleModule("agent-manager-protocol", Version.unknownVersion());
        module.addDeserializer(ZonedDateTime.class, new JsonZonedDateTimeDeserializer());
        module.addSerializer(ZonedDateTime.class, new JsonZonedDateTimeSerializer());

        this.mapper = JsonMapper.builder()
                                    .addModule(module)
                                    .addMixIn(
                                            DataSourceConfiguration.class,
                                            DataSourceConfigurationMixin.class
                                    )
                                    .changeDefaultVisibility(h -> h.withFieldVisibility(JsonAutoDetect.Visibility.ANY))
                                .build();
        this.writers = new ConcurrentHashMap<>();
        this.readers = new ConcurrentHashMap<>();
    }

    @Override
    @NonNull
    public ObjectMapper createMapper() {
        return this.mapper;
    }

    @Override
    @NonNull
    public ObjectWriter createWriterFor(@NonNull Class<?> clazz) {
        return writers.computeIfAbsent(clazz, this.mapper::writerFor);
    }

    @Override
    @NonNull
    public ObjectReader createReaderFor(@NonNull Class<?> clazz) {
        return readers.computeIfAbsent(clazz, this.mapper::readerFor);
    }
}
