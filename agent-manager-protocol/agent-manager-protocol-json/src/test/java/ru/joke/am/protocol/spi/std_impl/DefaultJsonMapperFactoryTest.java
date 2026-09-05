package ru.joke.am.protocol.spi.std_impl;

import org.junit.jupiter.api.Test;
import ru.joke.am.protocol.spi.JsonMapperFactory;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectReader;
import tools.jackson.databind.ObjectWriter;

import static org.junit.jupiter.api.Assertions.*;

class DefaultJsonMapperFactoryTest {

    private final JsonMapperFactory factory = new DefaultJsonMapperFactory();

    @Test
    void testThatCreateMapperReturnsConfiguredMapper() {
        final ObjectMapper mapper1 = factory.createMapper();
        final ObjectMapper mapper2 = factory.createMapper();

        assertNotNull(mapper1, "Mapper must be not null");
        assertSame(mapper1, mapper2, "Mappers must be equals");
    }

    @Test
    void testThatCreateWriterForReturnsCachedWriter() {
        final ObjectWriter writer1 = factory.createWriterFor(TestModel.class);
        final ObjectWriter writer2 = factory.createWriterFor(TestModel.class);

        assertNotNull(writer1, "Writer must be not null");
        assertSame(writer1, writer2, "Writers must be equals");
    }

    @Test
    void testThatCreateReaderForReturnsCachedReader() {
        final ObjectReader reader1 = factory.createReaderFor(TestModel.class);
        final ObjectReader reader2 = factory.createReaderFor(TestModel.class);

        assertNotNull(reader1, "Reader must be not null");
        assertSame(reader1, reader2, "Readers must be equals");
    }

    @Test
    void testThatCreateWriterForThrowsExceptionForNullClass() {
        assertThrows(NullPointerException.class, () -> factory.createWriterFor(null));
    }

    @Test
    void testThatCreateReaderForThrowsExceptionForNullClass() {
        assertThrows(NullPointerException.class, () -> factory.createReaderFor(null));
    }

    static class TestModel {
    }
}
