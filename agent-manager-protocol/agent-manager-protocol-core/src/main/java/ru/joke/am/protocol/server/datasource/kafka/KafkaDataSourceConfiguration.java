package ru.joke.am.protocol.server.datasource.kafka;

import lombok.*;
import lombok.experimental.Accessors;
import ru.joke.am.protocol.server.datasource.MessagingDataSourceConfiguration;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

@Getter
@Accessors(fluent = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class KafkaDataSourceConfiguration extends MessagingDataSourceConfiguration {

    private final Map<String, Object> consumerProperties;

    @Builder(toBuilder = true)
    public KafkaDataSourceConfiguration(
            @NonNull Map<String, Object> consumerProperties,
            @NonNull String destination,
            String messageSelector,
            @NonNull String subscriptionId
    ) {
        if (subscriptionId.isEmpty()) {
            throw new IllegalArgumentException("Subscription id must be not empty");
        }

        super(destination, messageSelector, subscriptionId);
        this.consumerProperties = consumerProperties;
    }

    @Override
    @NonNull
    public String subscriptionId() {
        return subscriptionId;
    }

    @Serial
    private Object writeReplace() throws ObjectStreamException {
        return new SerializationProxy(this);
    }

    private static final class SerializationProxy implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private final String destination;
        private final String messageSelector;
        private final String subscriptionId;
        private final Map<String, Object> consumerProperties;

        private SerializationProxy(@NonNull KafkaDataSourceConfiguration config) {
            this.consumerProperties = config.consumerProperties;
            this.destination = config.destination;
            this.messageSelector = config.messageSelector;
            this.subscriptionId = config.subscriptionId;
        }

        @Serial
        private Object readResolve() throws ObjectStreamException {
            return new KafkaDataSourceConfiguration(
                    Objects.requireNonNull(consumerProperties),
                    Objects.requireNonNull(destination),
                    messageSelector,
                    Objects.requireNonNull(subscriptionId)
            );
        }
    }
}
