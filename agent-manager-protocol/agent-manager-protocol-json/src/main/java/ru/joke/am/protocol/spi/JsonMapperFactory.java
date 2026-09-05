package ru.joke.am.protocol.spi;

import lombok.NonNull;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectReader;
import tools.jackson.databind.ObjectWriter;

public interface JsonMapperFactory {

    @NonNull
    ObjectMapper createMapper();

    @NonNull
    ObjectWriter createWriterFor(@NonNull Class<?> clazz);

    @NonNull
    ObjectReader createReaderFor(@NonNull Class<?> clazz);
}
