package ru.joke.am.protocol.server.datasource.jms;

import lombok.Getter;
import lombok.experimental.Accessors;
import ru.joke.am.protocol.IndexedEnum;

public enum DestinationType implements IndexedEnum {

    QUEUE(1),

    TOPIC(2);

    @Getter
    @Accessors(fluent = true)
    private final int id;

    DestinationType(int id) {
        this.id = id;
    }

    public static DestinationType from(int id) {
        return IndexedEnum.from(values(), id);
    }
}
