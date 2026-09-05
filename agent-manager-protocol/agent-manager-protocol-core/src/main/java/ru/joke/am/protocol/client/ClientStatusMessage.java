package ru.joke.am.protocol.client;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Accessors(fluent = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ClientStatusMessage extends ClientMessage {

    @NonNull
    private final ClientStatus status;

    @Builder(toBuilder = true)
    public ClientStatusMessage(
            @NonNull String host,
            int port,
            @NonNull String application,
            @NonNull ClientStatus status,
            @NonNull ZonedDateTime messageDateTime
    ) {
        super(host, port, application, messageDateTime);
        this.status = status;
    }

    @Serial
    private Object writeReplace() throws ObjectStreamException {
        return new SerializationProxy(this);
    }

    private static final class SerializationProxy implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private final String host;
        private final int port;
        private final String application;
        private final ZonedDateTime messageDateTime;
        private final ClientStatus status;

        private SerializationProxy(@NonNull ClientStatusMessage message) {
            this.host = message.host;
            this.status = message.status;
            this.application = message.application;
            this.port = message.port;
            this.messageDateTime = message.messageDateTime;
        }

        @Serial
        private Object readResolve() throws ObjectStreamException {
            return new ClientStatusMessage(
                    Objects.requireNonNull(host),
                    port,
                    Objects.requireNonNull(application),
                    Objects.requireNonNull(status),
                    Objects.requireNonNull(messageDateTime)
            );
        }
    }
}
