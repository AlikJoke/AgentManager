package ru.joke.am.protocol.server.datasource.jms;

import lombok.*;
import lombok.experimental.Accessors;
import ru.joke.am.protocol.server.datasource.MessagingDataSourceConfiguration;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Accessors(fluent = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class JmsDataSourceConfiguration extends MessagingDataSourceConfiguration {

    @NonNull
    private final String connectionFactory;
    @NonNull
    private final DestinationType destinationType;
    private final boolean isDurable;

    @Builder(toBuilder = true)
    public JmsDataSourceConfiguration(
            @NonNull String connectionFactory,
            @NonNull DestinationType destinationType,
            boolean isDurable,
            @NonNull String destination,
            String messageSelector,
            String subscriptionId
    ) {
        if (connectionFactory.isEmpty()) {
            throw new IllegalArgumentException("Connection factory must be not empty");
        }

        super(destination, messageSelector, subscriptionId);
        this.connectionFactory = connectionFactory;
        this.destinationType = destinationType;
        this.isDurable = isDurable;
    }

    @Serial
    private Object writeReplace() throws ObjectStreamException {
        return new SerializationProxy(this);
    }

    private static final class SerializationProxy implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private final String connectionFactory;
        private final DestinationType destinationType;
        private final boolean isDurable;
        private final String destination;
        private final String messageSelector;
        private final String subscriptionId;

        private SerializationProxy(@NonNull JmsDataSourceConfiguration config) {
            this.connectionFactory = config.connectionFactory;
            this.destinationType = config.destinationType;
            this.isDurable = config.isDurable;
            this.destination = config.destination;
            this.messageSelector = config.messageSelector;
            this.subscriptionId = config.subscriptionId;
        }

        @Serial
        private Object readResolve() throws ObjectStreamException {
            return new JmsDataSourceConfiguration(
                    Objects.requireNonNull(connectionFactory),
                    Objects.requireNonNull(destinationType),
                    isDurable,
                    Objects.requireNonNull(destination),
                    messageSelector,
                    subscriptionId
            );
        }
    }
}
