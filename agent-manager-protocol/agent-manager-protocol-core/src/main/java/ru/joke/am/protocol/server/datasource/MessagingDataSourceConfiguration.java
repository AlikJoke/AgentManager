package ru.joke.am.protocol.server.datasource;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.joke.am.protocol.server.DataSourceConfiguration;

@Getter
@Accessors(fluent = true)
@ToString(callSuper = true)
@EqualsAndHashCode
public abstract class MessagingDataSourceConfiguration implements DataSourceConfiguration {

    @NonNull
    protected final String destination;
    protected final String messageSelector;
    protected final String subscriptionId;

    protected MessagingDataSourceConfiguration(
            @NonNull String destination,
            String messageSelector,
            String subscriptionId
    ) {
        if (destination.isEmpty()) {
            throw new IllegalArgumentException("Destination name must be not empty");
        }

        this.destination = destination;
        this.messageSelector = messageSelector;
        this.subscriptionId = subscriptionId;
    }
}