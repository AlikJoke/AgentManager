package ru.joke.am.protocol.spi;

import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.joke.am.protocol.Message;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OneNioMessageConverterTest {

    private final OneNioMessageConverter converter = new OneNioMessageConverter();

    @Nested
    @DisplayName("Conversion Tests")
    class ConversionTests {

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
        @DisplayName("Should throw exception for invalid data bytes")
        void testFromMessageWithInvalidData() {
            final Message invalidMessage = new Message() {
                @Override
                @NonNull
                public byte[] data() {
                    return new byte[] { 1, 2, 3, 4};
                }

                @Override
                @NonNull
                public String dataType() {
                    return OneNioMessageConverter.CONTENT_TYPE;
                }
            };

            assertThrows(RuntimeException.class, () -> converter.fromMessage(invalidMessage, String.class));
        }

        @Test
        @DisplayName("Should throw exception for wrong type")
        void testFromMessageWithWrongType() {
            final String original = "not a number";
            final Message message = converter.toMessage(original);

            assertThrows(ClassCastException.class, () -> {
                @SuppressWarnings("unused")
                final Integer result = converter.fromMessage(message, Integer.class);
            });
        }

        @Test
        @DisplayName("Should throw exception for wrong type")
        void testFromMessageWithNonSerializableType() {
            final Object obj = new Object() {};
            assertThrows(NotSerializableException.class, () -> converter.toMessage(obj));
        }
    }

    @Nested
    @DisplayName("applied Method Tests")
    class AppliedMethodTests {

        @Test
        @DisplayName("Should return true for FORMAT")
        void testAppliedWithFormat() {
            assertTrue(converter.applied(OneNioMessageConverter.FORMAT));
        }

        @Test
        @DisplayName("Should return true for CONTENT_TYPE")
        void testAppliedWithContentType() {
            assertTrue(converter.applied(OneNioMessageConverter.CONTENT_TYPE));
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
            assertFalse(converter.applied("One-nio"));
            assertFalse(converter.applied("ONE-NIO"));
            assertTrue(converter.applied("one-nio"));
            assertTrue(converter.applied("application/octet-stream;type=one-nio"));
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

            assertEquals(OneNioMessageConverter.CONTENT_TYPE, message.dataType());
            assertTrue(converter.applied(message.dataType()));
        }
    }

    record TestData(
            int id,
            String name,
            double value
    ) implements Serializable {}
}
