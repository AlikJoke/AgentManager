package ru.joke.am.protocol;

import ru.joke.am.protocol.spi.MessageConverter;
import ru.joke.am.protocol.spi.MessageConverterFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseProtocolObjectConversionTest<T> {

    protected void makeObjectConversionCheck(final T config) {
        final MessageConverter converter = MessageConverterFactory.getInstance().createFor(targetDataType());

        final Message message = converter.toMessage(config);

        assertEquals(targetDataType(), message.dataType(), "Content type must be equal");
        assertTrue(message.data().length > 0, "Data bytes must be not empty");

        @SuppressWarnings("unchecked")
        final Class<T> tokenType = (Class<T>) config.getClass();
        final T deserializedConfig = converter.fromMessage(message, tokenType);

        assertEquals(config, deserializedConfig, "Configs after conversions must be equals");
    }

    protected abstract String targetDataType();
}
