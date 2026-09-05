package ru.joke.am.protocol.server.datasource.jms;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DestinationTypeTest {

    @ParameterizedTest
    @ValueSource(ints = { 0, 3, -1, 100, Integer.MAX_VALUE, Integer.MIN_VALUE })
    void testFromWithInvalidIds(int invalidId) {
        assertThrows(IllegalArgumentException.class, () -> DestinationType.from(invalidId));
    }

    @ParameterizedTest
    @MethodSource("provideEnumIdMapping")
    void testIdMapping(DestinationType status, int expectedId) {
        assertEquals(expectedId, status.id());
    }

    private static Stream<Arguments> provideEnumIdMapping() {
        return Stream.of(
                Arguments.of(DestinationType.QUEUE, 1),
                Arguments.of(DestinationType.TOPIC, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidIds")
    void testFromWithValidIds(int id, DestinationType expectedStatus) {
        assertSame(expectedStatus, DestinationType.from(id));
    }

    private static Stream<Arguments> provideValidIds() {
        return Stream.of(
                Arguments.of(1, DestinationType.QUEUE),
                Arguments.of(2, DestinationType.TOPIC)
        );
    }
}
