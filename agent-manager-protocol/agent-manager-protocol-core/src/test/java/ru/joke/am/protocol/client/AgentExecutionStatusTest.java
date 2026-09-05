package ru.joke.am.protocol.client;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AgentExecutionStatusTest {

    @ParameterizedTest
    @ValueSource(ints = { 0, 5, -1, 100, Integer.MAX_VALUE, Integer.MIN_VALUE })
    void testFromWithInvalidIds(int invalidId) {
        assertThrows(IllegalArgumentException.class, () -> AgentExecutionStatus.from(invalidId));
    }

    @ParameterizedTest
    @MethodSource("provideEnumIdMapping")
    void testIdMapping(AgentExecutionStatus status, int expectedId) {
        assertEquals(expectedId, status.id());
    }

    private static Stream<Arguments> provideEnumIdMapping() {
        return Stream.of(
                Arguments.of(AgentExecutionStatus.STARTED, 1),
                Arguments.of(AgentExecutionStatus.FINISHED, 2),
                Arguments.of(AgentExecutionStatus.STOPPED, 3),
                Arguments.of(AgentExecutionStatus.ERROR, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidIds")
    void testFromWithValidIds(int id, AgentExecutionStatus expectedStatus) {
        assertSame(expectedStatus, AgentExecutionStatus.from(id));
    }

    private static Stream<Arguments> provideValidIds() {
        return Stream.of(
                Arguments.of(1, AgentExecutionStatus.STARTED),
                Arguments.of(2, AgentExecutionStatus.FINISHED),
                Arguments.of(3, AgentExecutionStatus.STOPPED),
                Arguments.of(4, AgentExecutionStatus.ERROR)
        );
    }
}
