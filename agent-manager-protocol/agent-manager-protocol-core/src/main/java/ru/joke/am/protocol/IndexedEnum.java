package ru.joke.am.protocol;

import lombok.NonNull;

public interface IndexedEnum {

    @NonNull
    String name();

    int id();

    @NonNull
    static <T extends IndexedEnum> T from(@NonNull T[] values, int id) {
        for (T elem : values) {
            if (elem.id() == id) {
                return elem;
            }
        }

        throw new IllegalArgumentException("Unknown id: " + id);
    }
}
