package ru.joke.am.protocol.server;

import lombok.Getter;
import lombok.experimental.Accessors;
import ru.joke.am.protocol.IndexedEnum;

public enum DuplicationStartupPolicy implements IndexedEnum {

    IGNORE(1),

    INTERRUPT_EXISTING(2),

    ALLOW(3);

    @Getter
    @Accessors(fluent = true)
    private final int id;

    DuplicationStartupPolicy(int id) {
        this.id = id;
    }

    public static DuplicationStartupPolicy from(int id) {
        return IndexedEnum.from(values(), id);
    }
}
