package ru.joke.am.protocol.client;

import lombok.Getter;
import lombok.experimental.Accessors;
import ru.joke.am.protocol.IndexedEnum;

public enum AgentExecutionStatus implements IndexedEnum {

    STARTED(1),

    FINISHED(2),

    STOPPED(3),

    ERROR(4);

    @Getter
    @Accessors(fluent = true)
    private final int id;

    AgentExecutionStatus(int id) {
        this.id = id;
    }

    public static AgentExecutionStatus from(int id) {
        return IndexedEnum.from(values(), id);
    }
}
