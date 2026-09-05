package ru.joke.am.protocol.spi.std_impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonZonedDateTimeDeserializerTest {

    private final JsonZonedDateTimeDeserializer deserializer = new JsonZonedDateTimeDeserializer();

    @Mock
    private JsonParser parser;
    @Mock
    private DeserializationContext context;
    @Mock
    private JsonNode node;

    @Test
    void testWhenDateTimeFieldIsNull() {
        when(context.readTree(parser)).thenReturn(null);

        final ZonedDateTime result = deserializer.deserialize(parser, context);
        assertNull(result, "Date-time must be null");
    }

    @Test
    void testWhenDateTimeFieldContainInvalidString() {
        when(context.readTree(parser)).thenReturn(node);
        when(node.asString()).thenReturn("2026-01-01T12:12:12");

        assertThrows(DateTimeParseException.class, () -> deserializer.deserialize(parser, context));
    }

    @Test
    void testWhenDateTimeFieldContainValidString() {
        when(context.readTree(parser)).thenReturn(node);
        final String templateStr = "2026-08-20T23:42:11.629736201+03:00[Europe/Moscow]";
        when(node.asString()).thenReturn(templateStr);

        final ZonedDateTime template = ZonedDateTime.parse(templateStr);
        final ZonedDateTime result = deserializer.deserialize(parser, context);

        assertNotNull(result, "Date-time must be not null");
        assertEquals(template, result, "Date-time must be equal");
    }
}
