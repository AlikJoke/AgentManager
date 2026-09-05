package ru.joke.am.protocol.spi.std_impl;

import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.joke.am.protocol.Message;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JNSMessageConverterTest {

    private final JNSMessageConverter converter = new JNSMessageConverter();

    @Nested
    @DisplayName("Tests for toMessage method")
    class ToMessageTests {

        @Test
        @DisplayName("Should convert Serializable object to Message")
        void testToMessageWithSerializableObject() {
            final String testObject = UUID.randomUUID().toString();
            final Message message = converter.toMessage(testObject);

            assertNotNull(message);
            assertNotNull(message.data());
            assertTrue(message.data().length > 0);
            assertEquals(JNSMessageConverter.CONTENT_TYPE, message.dataType());
        }

        @Test
        @DisplayName("Should convert custom Serializable object")
        void testToMessageWithCustomObject() {
            final TestData testData = new TestData(1, "test");
            final Message message = converter.toMessage(testData);

            assertNotNull(message);
            assertNotNull(message.data());
            assertTrue(message.data().length > 0);
            assertEquals(JNSMessageConverter.CONTENT_TYPE, message.dataType());
        }

        @Test
        @DisplayName("Should convert collection object")
        void testToMessageWithCollection() {
            final List<String> list = Arrays.asList("one", "two", "three");
            final Message message = converter.toMessage(list);

            assertNotNull(message);
            assertNotNull(message.data());
            assertEquals(JNSMessageConverter.CONTENT_TYPE, message.dataType());
        }

        @Test
        @DisplayName("Should throw exception for non-serializable object")
        void testToMessageWithNonSerializableObject() {
            final Object nonSerializable = new Object() {};

            assertThrows(Exception.class, () -> converter.toMessage(nonSerializable));
        }
    }

    @Nested
    @DisplayName("Tests for fromMessage method")
    class FromMessageTests {

        @Test
        @DisplayName("Should convert Message back to original object")
        void testFromMessageWithString() {
            final String original = "Test String";
            final Message message = converter.toMessage(original);

            final String result = converter.fromMessage(message, String.class);

            assertEquals(original, result);
        }

        @Test
        @DisplayName("Should convert Message back to custom object")
        void testFromMessageWithCustomObject() {
            final TestData original = new TestData(42, "answer");
            final Message message = converter.toMessage(original);

            final TestData result = converter.fromMessage(message, TestData.class);

            assertEquals(original, result);
        }

        @Test
        @DisplayName("Should throw exception for corrupted data")
        void testFromMessageWithCorruptedData() {
            final Message corruptedMessage = new Message() {
                @Override
                @NonNull
                public byte[] data() {
                    return new byte[]{1, 2, 3, 4, 5};
                }

                @Override
                @NonNull
                public String dataType() {
                    return JNSMessageConverter.CONTENT_TYPE;
                }
            };

            assertThrows(Exception.class, () -> converter.fromMessage(corruptedMessage, String.class));
        }

        @Test
        @DisplayName("Should throw exception for wrong type cast")
        void testFromMessageWithWrongType() {
            final String original = "Test";
            final Message message = converter.toMessage(original);

            assertThrows(ClassCastException.class, () -> {
                @SuppressWarnings("unused")
                final Integer result = converter.fromMessage(message, Integer.class);
            });
        }

        @Test
        @DisplayName("Should handle empty data array")
        void testFromMessageWithEmptyData() {
            final Message emptyMessage = new Message() {
                @Override
                @NonNull
                public byte[] data() {
                    return new byte[0];
                }

                @Override
                @NonNull
                public String dataType() {
                    return JNSMessageConverter.CONTENT_TYPE;
                }
            };

            assertThrows(Exception.class, () -> converter.fromMessage(emptyMessage, String.class));
        }
    }

    @Nested
    @DisplayName("Tests for applied method")
    class AppliedMethodTests {

        @Test
        @DisplayName("Should return true for FORMAT constant")
        void testAppliedWithFormat() {
            assertTrue(converter.applied(JNSMessageConverter.FORMAT));
        }

        @Test
        @DisplayName("Should return true for CONTENT_TYPE constant")
        void testAppliedWithContentType() {
            assertTrue(converter.applied(JNSMessageConverter.CONTENT_TYPE));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "json",
                "xml",
                "text/plain",
                "application/json",
                "native2",
                "NATIVE",
                "Native",
                "",
                " ",
                "null"
        })
        @DisplayName("Should return false for invalid types")
        void testAppliedWithInvalidTypes(String invalidType) {
            assertFalse(converter.applied(invalidType));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should return false for null and empty types")
        void testAppliedWithNullOrEmpty(String type) {
            assertFalse(converter.applied(type));
        }

        @Test
        @DisplayName("Should be case-sensitive")
        void testAppliedCaseSensitivity() {
            assertFalse(converter.applied("Native"));
            assertFalse(converter.applied("NATIVE"));
            assertTrue(converter.applied("native"));
        }
    }

    record TestData(
            int id,
            String name
    ) implements Serializable {}
}