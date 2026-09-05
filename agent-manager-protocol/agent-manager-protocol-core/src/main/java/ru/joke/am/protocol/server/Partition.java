package ru.joke.am.protocol.server;

import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;

public record Partition(
        @NonNull String host,
        int partitionIndex
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public Partition {
        if (partitionIndex < 0) {
            throw new IllegalArgumentException("Partition index must be non-negative");
        }

        if (host.isEmpty()) {
            throw new IllegalArgumentException("Host name must be not empty");
        }
    }
}