package ru.joke.am.protocol.spi.std_impl;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

final class JsonZonedDateTimeDeserializer extends ValueDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser jp, DeserializationContext ctx) {
        final JsonNode node = ctx.readTree(jp);
        if (node == null) {
            return null;
        }

        return ZonedDateTime.parse(node.asString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
}