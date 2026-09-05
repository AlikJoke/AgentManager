package ru.joke.am.protocol.spi;

import lombok.NonNull;
import ru.joke.am.protocol.Message;
import ru.joke.am.protocol.spi.std_impl.DefaultJsonMapperFactory;

import java.util.ServiceLoader;

public final class JsonMessageConverter implements MessageConverter {

    public static final String FORMAT = "json";
    public static final String CONTENT_TYPE = "application/" + FORMAT;

    private final JsonMapperFactory jsonMapperFactory;

    public JsonMessageConverter() {
        this.jsonMapperFactory =
                ServiceLoader.load(JsonMapperFactory.class, getClass().getClassLoader())
                        .findFirst()
                        .orElseGet(DefaultJsonMapperFactory::new);
    }

    @Override
    @NonNull
    public <T> Message toMessage(@NonNull T obj) {
        final var writer = this.jsonMapperFactory.createWriterFor(obj.getClass());
        final byte[] data = writer.writeValueAsBytes(obj);
        return new Message() {
            @Override
            @NonNull
            public byte[] data() {
                return data;
            }

            @Override
            @NonNull
            public String dataType() {
                return CONTENT_TYPE;
            }
        };
    }

    @Override
    @NonNull
    public <T> T fromMessage(@NonNull Message message, @NonNull Class<T> tokenType) {
        final var reader = this.jsonMapperFactory.createReaderFor(tokenType);
        return reader.readValue(message.data());
    }

    @Override
    public boolean applied(String type) {
        return FORMAT.equals(type) || CONTENT_TYPE.equals(type);
    }
}
