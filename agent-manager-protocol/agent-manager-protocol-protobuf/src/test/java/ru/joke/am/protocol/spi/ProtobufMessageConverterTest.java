package ru.joke.am.protocol.spi;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.joke.am.protocol.Message;
import ru.joke.am.protocol.server.AgentConfiguration;
import ru.joke.am.protocol.server.StopTriggerMessage;

import static org.junit.jupiter.api.Assertions.*;

class ProtobufMessageConverterTest {

    private final ProtobufMessageConverter converter = new ProtobufMessageConverter();

    @Nested
    @DisplayName("Invalid data conversion Tests")
    class InvalidDataTests {

        @Test
        @DisplayName("toMessage should throw exception on unsupported type")
        void testToMessageShouldThrowExceptionOnUnsupportedType() {
            assertThrows(IllegalArgumentException.class, () -> converter.toMessage(AgentConfiguration.class));
        }

        @Test
        @DisplayName("fromMessage should throw exception on unsupported type")
        void testFromMessageShouldThrowExceptionOnUnsupportedType() {
            final Message message = new Message() {
                @Override
                @NonNull
                public byte[] data() {
                    return new byte[0];
                }

                @Override
                @NonNull
                public String dataType() {
                    return ProtobufMessageConverter.CONTENT_TYPE;
                }
            };
            assertThrows(IllegalArgumentException.class, () -> converter.fromMessage(message, AgentConfiguration.class));
        }

        @Test
        @DisplayName("fromMessage should throw exception on invalid data")
        void testFromMessageShouldThrowExceptionOnInvalidData() {
            final Message message = new Message() {
                @Override
                @NonNull
                public byte[] data() {
                    return new byte[] { 1, 2, 3 };
                }

                @Override
                @NonNull
                public String dataType() {
                    return ProtobufMessageConverter.CONTENT_TYPE;
                }
            };
            assertThrows(InvalidProtocolBufferException.class, () -> converter.fromMessage(message, StopTriggerMessage.class));
        }
    }

    @Nested
    @DisplayName("applied Method Tests")
    class AppliedMethodTests {

        @Test
        @DisplayName("Should return true for FORMAT")
        void testAppliedWithFormat() {
            assertTrue(converter.applied(ProtobufMessageConverter.FORMAT));
        }

        @Test
        @DisplayName("Should return true for CONTENT_TYPE")
        void testAppliedWithContentType() {
            assertTrue(converter.applied(ProtobufMessageConverter.CONTENT_TYPE));
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
            assertFalse(converter.applied("proto"));
            assertFalse(converter.applied("Protobuf"));
            assertFalse(converter.applied("PROTOBUF"));
            assertTrue(converter.applied("protobuf"));
            assertTrue(converter.applied("application/protobuf"));
        }
    }
}
