package ru.joke.am.protocol.server;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DuplicationStartupPolicyTest {

    @ParameterizedTest
    @ValueSource(ints = { 0, 4, -1, 100, Integer.MAX_VALUE, Integer.MIN_VALUE })
    void testFromWithInvalidIds(int invalidId) {
        assertThrows(IllegalArgumentException.class, () -> DuplicationStartupPolicy.from(invalidId));
    }

    @ParameterizedTest
    @MethodSource("provideEnumIdMapping")
    void testIdMapping(DuplicationStartupPolicy status, int expectedId) {
        assertEquals(expectedId, status.id());
    }

    private static Stream<Arguments> provideEnumIdMapping() {
        return Stream.of(
                Arguments.of(DuplicationStartupPolicy.IGNORE, 1),
                Arguments.of(DuplicationStartupPolicy.INTERRUPT_EXISTING, 2),
                Arguments.of(DuplicationStartupPolicy.ALLOW, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidIds")
    void testFromWithValidIds(int id, DuplicationStartupPolicy expectedStatus) {
        assertSame(expectedStatus, DuplicationStartupPolicy.from(id));
    }

    private static Stream<Arguments> provideValidIds() {
        return Stream.of(
                Arguments.of(1, DuplicationStartupPolicy.IGNORE),
                Arguments.of(2, DuplicationStartupPolicy.INTERRUPT_EXISTING),
                Arguments.of(3, DuplicationStartupPolicy.ALLOW)
        );
    }
}
