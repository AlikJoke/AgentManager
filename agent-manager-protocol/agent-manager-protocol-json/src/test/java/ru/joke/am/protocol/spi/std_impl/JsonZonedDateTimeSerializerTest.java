package ru.joke.am.protocol.spi.std_impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

import java.time.ZonedDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class JsonZonedDateTimeSerializerTest {

    private final JsonZonedDateTimeSerializer serializer = new JsonZonedDateTimeSerializer();

    @Mock
    private JsonGenerator generator;
    @Mock
    private SerializationContext context;

    @Test
    void testWhenValidDateTimeProvidedThenSerializationSuccess() {
        final String templateStr = "2026-08-20T23:42:11.629736201+03:00[Europe/Moscow]";
        final ZonedDateTime template = ZonedDateTime.parse(templateStr);

        serializer.serialize(template, generator, context);

        verify(generator).writeString(templateStr);
    }

    @Test
    void testWhenNullDateTimeProvidedThenNoOp() {
        serializer.serialize(null, generator, context);
        verifyNoInteractions(generator);
    }
}
