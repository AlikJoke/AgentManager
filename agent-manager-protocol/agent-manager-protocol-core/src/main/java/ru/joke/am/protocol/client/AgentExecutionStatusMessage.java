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
public final class AgentExecutionStatusMessage extends ClientMessage {

    @NonNull
    private final AgentExecutionStatus status;
    @NonNull
    private final String executionId;
    @NonNull
    private final String triggerId;
    @NonNull
    private final String agentConfigId;
    private final String info;

    @Builder(toBuilder = true)
    public AgentExecutionStatusMessage(
            @NonNull String host,
            int port,
            @NonNull String application,
            @NonNull ZonedDateTime messageDateTime,
            @NonNull AgentExecutionStatus status,
            @NonNull String executionId,
            @NonNull String triggerId,
            @NonNull String agentConfigId,
            String info
    ) {
        if (executionId.isEmpty()) {
            throw new IllegalArgumentException("Execution id must be not empty");
        }
        if (triggerId.isEmpty()) {
            throw new IllegalArgumentException("Trigger id must be not empty");
        }
        if (agentConfigId.isEmpty()) {
            throw new IllegalArgumentException("Agent config id must be not empty");
        }

        super(host, port, application, messageDateTime);
        this.status = status;
        this.executionId = executionId;
        this.triggerId = triggerId;
        this.agentConfigId = agentConfigId;
        this.info = info;
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
        private final AgentExecutionStatus status;
        private final String executionId;
        private final String triggerId;
        private final String agentConfigId;
        private final String info;

         private SerializationProxy(@NonNull AgentExecutionStatusMessage message) {
             this.host = message.host;
             this.agentConfigId = message.agentConfigId;
             this.executionId = message.executionId;
             this.status = message.status;
             this.application = message.application;
             this.info = message.info;
             this.triggerId = message.triggerId;
             this.port = message.port;
             this.messageDateTime = message.messageDateTime;
         }

        @Serial
        private Object readResolve() throws ObjectStreamException {
            return new AgentExecutionStatusMessage(
                    Objects.requireNonNull(host),
                    port,
                    Objects.requireNonNull(application),
                    Objects.requireNonNull(messageDateTime),
                    Objects.requireNonNull(status),
                    Objects.requireNonNull(executionId),
                    Objects.requireNonNull(triggerId),
                    Objects.requireNonNull(agentConfigId),
                    info
            );
        }
    }
}
