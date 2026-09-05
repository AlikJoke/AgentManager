package ru.joke.am.protocol.spi.std_impl;

import lombok.NonNull;
import lombok.SneakyThrows;
import ru.joke.am.protocol.Message;
import ru.joke.am.protocol.spi.MessageConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class JNSMessageConverter implements MessageConverter {

    public static final String FORMAT = "native";
    public static final String CONTENT_TYPE = "application/octet-stream;type=" + FORMAT;

    @Override
    @SneakyThrows
    @NonNull
    public <T> Message toMessage(@NonNull T obj) {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream();
             final ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(obj);
            oos.flush();

            final byte[] data = bos.toByteArray();
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
    }

    @Override
    @SneakyThrows
    @NonNull
    public <T> T fromMessage(@NonNull Message message, @NonNull Class<T> tokenType) {
        try (final ByteArrayInputStream bis = new ByteArrayInputStream(message.data());
             final ObjectInputStream ois = new ObjectInputStream(bis)) {
            @SuppressWarnings("unchecked")
            final T result = (T) ois.readObject();
            return result;
        }
    }

    @Override
    public boolean applied(String type) {
        return FORMAT.equals(type) || CONTENT_TYPE.equals(type);
    }
}
