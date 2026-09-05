package ru.joke.am.protocol.spi;

import lombok.NonNull;
import lombok.SneakyThrows;
import one.nio.serial.Serializer;
import ru.joke.am.protocol.Message;

public final class OneNioMessageConverter implements MessageConverter {

    public static final String FORMAT = "one-nio";
    public static final String CONTENT_TYPE = "application/octet-stream;type=" + FORMAT;

    @Override
    @SneakyThrows
    @NonNull
    public <T> Message toMessage(@NonNull T obj) {
        final byte[] serializedData = Serializer.serialize(obj);
        return new Message() {
            @Override
            @NonNull
            public byte[] data() {
                return serializedData;
            }

            @Override
            @NonNull
            public String dataType() {
                return CONTENT_TYPE;
            }
        };
    }

    @Override
    @SneakyThrows
    @NonNull
    public <T> T fromMessage(@NonNull Message data, @NonNull Class<T> tokenType) {
        @SuppressWarnings("unchecked")
        final T result = (T) Serializer.deserialize(data.data());
        return result;
    }

    @Override
    public boolean applied(String type) {
        return FORMAT.equals(type) || CONTENT_TYPE.equals(type);
    }
}
