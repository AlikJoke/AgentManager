package ru.joke.am.protocol.client;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ClientStatusTest {

    @ParameterizedTest
    @ValueSource(ints = { 0, 4, -1, 100, Integer.MAX_VALUE, Integer.MIN_VALUE })
    void testFromWithInvalidIds(int invalidId) {
        assertThrows(IllegalArgumentException.class, () -> ClientStatus.from(invalidId));
    }

    @ParameterizedTest
    @MethodSource("provideEnumIdMapping")
    void testIdMapping(ClientStatus status, int expectedId) {
        assertEquals(expectedId, status.id());
    }

    private static Stream<Arguments> provideEnumIdMapping() {
        return Stream.of(
                Arguments.of(ClientStatus.STARTED, 1),
                Arguments.of(ClientStatus.ALIVE, 2),
                Arguments.of(ClientStatus.STOPPED, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidIds")
    void testFromWithValidIds(int id, ClientStatus expectedStatus) {
        assertSame(expectedStatus, ClientStatus.from(id));
    }

    private static Stream<Arguments> provideValidIds() {
        return Stream.of(
                Arguments.of(1, ClientStatus.STARTED),
                Arguments.of(2, ClientStatus.ALIVE),
                Arguments.of(3, ClientStatus.STOPPED)
        );
    }
}
