package ru.joke.am.protocol.server;

import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;

public record StopTriggerMessage(@NonNull String triggerId) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public StopTriggerMessage {
        if (triggerId.isEmpty()) {
            throw new IllegalArgumentException("Trigger id must be not empty");
        }
    }
}