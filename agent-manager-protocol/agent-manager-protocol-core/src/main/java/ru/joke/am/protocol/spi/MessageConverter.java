package ru.joke.am.protocol.spi;

import lombok.NonNull;
import ru.joke.am.protocol.Message;

public interface MessageConverter {

    @NonNull
    <T> Message toMessage(@NonNull T obj);

    @NonNull
    <T> T fromMessage(@NonNull Message data, @NonNull Class<T> tokenType);

    boolean applied(String type);
}
