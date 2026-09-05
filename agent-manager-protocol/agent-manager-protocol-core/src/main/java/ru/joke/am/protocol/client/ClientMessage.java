package ru.joke.am.protocol.client;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
abstract class ClientMessage implements Serializable {

    @NonNull
    protected final String host;
    @NonNull
    protected final String application;
    @NonNull
    protected final ZonedDateTime messageDateTime;
    protected final int port;

    ClientMessage(
            @NonNull String host,
            int port,
            @NonNull String application,
            @NonNull ZonedDateTime messageDateTime
    ) {
        if (port < -1) {
            throw new IllegalArgumentException("Port cannot be negative: " + port);
        }
        if (application.isEmpty()) {
            throw new IllegalArgumentException("Application name must be not empty");
        }
        if (host.isEmpty()) {
            throw new IllegalArgumentException("Host name must be not empty");
        }

        this.host = host;
        this.port = port;
        this.application = application;
        this.messageDateTime = messageDateTime;
    }
}