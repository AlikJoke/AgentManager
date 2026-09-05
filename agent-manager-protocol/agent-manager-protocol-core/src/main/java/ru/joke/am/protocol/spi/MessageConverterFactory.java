package ru.joke.am.protocol.spi;

import lombok.Getter;
import lombok.NonNull;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

public final class MessageConverterFactory {

    @Getter
    private static final MessageConverterFactory instance = new MessageConverterFactory();

    private final Set<MessageConverter> converters;

    private MessageConverterFactory() {
        final var loader = ServiceLoader.load(MessageConverter.class, MessageConverter.class.getClassLoader());
        this.converters = loader.stream().map(ServiceLoader.Provider::get).collect(Collectors.toSet());
        if (this.converters.isEmpty()) {
            throw new NoSuchElementException("No any %s implementation found".formatted(MessageConverter.class.getCanonicalName()));
        }
    }

    @NonNull
    public MessageConverter createFor(String type) {
        return this.converters
                    .stream()
                    .filter(c -> c.applied(type))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("No converter found for " + type));
    }
}
