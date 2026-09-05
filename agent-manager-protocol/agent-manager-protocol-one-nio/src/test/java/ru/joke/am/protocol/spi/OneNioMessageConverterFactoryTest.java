package ru.joke.am.protocol.spi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static ru.joke.am.protocol.spi.OneNioMessageConverter.CONTENT_TYPE;
import static ru.joke.am.protocol.spi.OneNioMessageConverter.FORMAT;

class OneNioMessageConverterFactoryTest {

    @Test
    void testThatCreateConverterReturnsSameInstance() {
        final MessageConverterFactory messageConverterFactory = MessageConverterFactory.getInstance();
        final MessageConverter messageConverter1 = messageConverterFactory.createFor(CONTENT_TYPE);
        final MessageConverter messageConverter2 = messageConverterFactory.createFor(FORMAT);

        assertInstanceOf(OneNioMessageConverter.class, messageConverter1);
        assertEquals(messageConverter1, messageConverter2, "Converter must be same");
    }
}
