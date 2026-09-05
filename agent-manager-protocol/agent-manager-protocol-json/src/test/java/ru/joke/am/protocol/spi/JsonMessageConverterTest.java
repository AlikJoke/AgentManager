package ru.joke.am.protocol.spi;

import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.joke.am.protocol.Message;
import tools.jackson.core.JacksonException;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ru.joke.am.protocol.spi.JsonMessageConverter.CONTENT_TYPE;

class JsonMessageConverterTest {

    private final JsonMessageConverter converter = new JsonMessageConverter();

    @Nested
    @DisplayName("Conversion Tests")
    class ConversionTests {

        @Test
        @DisplayName("Should convert ZonedDateTime to Message")
        void testToMessageWithZonedDateTime() {
            final ZonedDateTime zdt = ZonedDateTime.now();
            final Message message = converter.toMessage(zdt);

            assertNotNull(message, "Serialized data must be not null");
            assertEquals(CONTENT_TYPE, message.dataType(), "Data type must be equal");
            assertTrue(message.data().length > 0, "Serialized data size must be greater than 0");

            final ZonedDateTime zdt2 = converter.fromMessage(message, ZonedDateTime.class);

            assertEquals(zdt, zdt2, "Date-time must be equal");
        }

        @Test
        @DisplayName("Should convert Message to String")
        void testFromMessageToString() {
            final String original = "Test String";
            final Message message = converter.toMessage(original);

            final String result = converter.fromMessage(message, String.class);

            assertEquals(original, result);
        }

        @Test
        @DisplayName("Should convert Message to POJO")
        void testFromMessageToPojo() {
            final TestData original = new TestData(42, "answer", 2.718);
            Message message = converter.toMessage(original);

            final TestData result = converter.fromMessage(message, TestData.class);

            assertEquals(original, result);
        }

        @Test
        @DisplayName("Should throw exception for invalid JSON")
        void testFromMessageWithInvalidJson() {
            final Message invalidMessage = new Message() {
                @Override
                @NonNull
                public byte[] data() {
                    return "{\"a\":1".getBytes();
                }

                @Override
                @NonNull
                public String dataType() {
                    return JsonMessageConverter.CONTENT_TYPE;
                }
            };

            assertThrows(JacksonException.class, () -> converter.fromMessage(invalidMessage, String.class));
        }

        @Test
        @DisplayName("Should throw exception for wrong type")
        void testFromMessageWithWrongType() {
            String original = "not a number";
            Message message = converter.toMessage(original);

            assertThrows(JacksonException.class, () -> converter.fromMessage(message, Integer.class));
        }
    }

    @Nested
    @DisplayName("applied Method Tests")
    class AppliedMethodTests {

        @Test
        @DisplayName("Should return true for FORMAT")
        void testAppliedWithFormat() {
            assertTrue(converter.applied(JsonMessageConverter.FORMAT));
        }

        @Test
        @DisplayName("Should return true for CONTENT_TYPE")
        void testAppliedWithContentType() {
            assertTrue(converter.applied(JsonMessageConverter.CONTENT_TYPE));
        }

        @ParameterizedTest
        @ValueSource(strings = { "xml", "native", "text/plain", "application/xml", "json2", "JSON", "Json", "", " ", "null" })
        @DisplayName("Should return false for invalid types")
        void testAppliedWithInvalidTypes(String invalidType) {
            assertFalse(converter.applied(invalidType));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should return false for null and empty")
        void testAppliedWithNullOrEmpty(String type) {
            assertFalse(converter.applied(type));
        }

        @Test
        @DisplayName("Should be case-sensitive")
        void testAppliedCaseSensitivity() {
            assertFalse(converter.applied("Json"));
            assertFalse(converter.applied("JSON"));
            assertTrue(converter.applied("json"));
            assertTrue(converter.applied("application/json"));
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should round-trip various data types")
        void testRoundTrip() {
            final Object[] testObjects = {
                    "String value",
                    42,
                    3.14,
                    true,
                    Arrays.asList(1, 2, 3),
                    new TestData(1, "test", 1.5),
                    Map.of("key", "value")
            };

            for (Object original : testObjects) {
                Message message = converter.toMessage(original);
                Object result = converter.fromMessage(message, original.getClass());

                assertEquals(original, result);
            }
        }

        @Test
        @DisplayName("Should verify data type consistency")
        void testDataTypeConsistency() {
            final String testObject = "test";
            final Message message = converter.toMessage(testObject);

            assertEquals(JsonMessageConverter.CONTENT_TYPE, message.dataType());
            assertTrue(converter.applied(message.dataType()));
        }
    }

    record TestData(
            int id,
            String name,
            double value
    ) {}
}
