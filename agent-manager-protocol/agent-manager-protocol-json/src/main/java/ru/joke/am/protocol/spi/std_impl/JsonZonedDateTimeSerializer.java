package ru.joke.am.protocol.spi.std_impl;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

final class JsonZonedDateTimeSerializer extends ValueSerializer<ZonedDateTime> {

    @Override
    public void serialize(ZonedDateTime date, JsonGenerator generator, SerializationContext ctx) {
        if (date != null) {
            final String dateString = date.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
            generator.writeString(dateString);
        }
    }
}
