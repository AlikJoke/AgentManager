package ru.joke.am.protocol.client;

import lombok.Getter;
import lombok.experimental.Accessors;
import ru.joke.am.protocol.IndexedEnum;

public enum ClientStatus implements IndexedEnum {

    STARTED(1),

    ALIVE(2),

    STOPPED(3);

    @Getter
    @Accessors(fluent = true)
    private final int id;

    ClientStatus(int id) {
        this.id = id;
    }

    public static ClientStatus from(int id) {
        return IndexedEnum.from(values(), id);
    }
}
