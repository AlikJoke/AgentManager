package ru.joke.am.protocol.spi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MessageMessageConverterFactoryTest {

    @Test
    void testThatGetInstanceReturnsSameInstance() {
        final MessageConverterFactory factory1 = MessageConverterFactory.getInstance();
        final MessageConverterFactory factory2 = MessageConverterFactory.getInstance();

        assertNotNull(factory1, "Converter factory must be not null");
        assertEquals(factory1, factory2, "Converter factory must be same");
    }

    @Test
    void testThatCreateReturnsLoadedConverter() {
        final MessageConverterFactory factory = MessageConverterFactory.getInstance();

        final MessageConverter messageConverter1 = factory.createFor("test");
        final MessageConverter messageConverter2 = factory.createFor("test");

        assertNotNull(messageConverter1, "Converter must be not null");
        assertEquals(messageConverter1, messageConverter2, "Converters must be equals");
    }
}
