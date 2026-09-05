package ru.joke.am.protocol.spi;

import lombok.NonNull;
import ru.joke.am.protocol.Message;

public final class TestMessageConverter implements MessageConverter {

    @Override
    @NonNull
    public <T> Message toMessage(@NonNull T obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    @NonNull
    public <T> T fromMessage(@NonNull Message message, @NonNull Class<T> tokenType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean applied(String type) {
        return "test".equals(type);
    }
}
