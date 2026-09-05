package ru.joke.am.protocol.server;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Accessors(fluent = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ContinuousTriggerMessage extends TriggerMessage {

    private final AgentConfiguration agent;

    @Builder(toBuilder = true)
    public ContinuousTriggerMessage(
            @NonNull String triggerId,
            @NonNull String executionId,
            int maxConsecutiveExceptions,
            @NonNull List<Partition> partitions,
            @NonNull AgentConfiguration agent
    ) {
        super(triggerId, executionId, maxConsecutiveExceptions, partitions);
        this.agent = agent;
    }

    @Override
    @NonNull
    public List<AgentConfiguration> agents() {
        return Collections.singletonList(agent);
    }

    @Serial
    private Object writeReplace() throws ObjectStreamException {
        return new SerializationProxy(this);
    }

    private static final class SerializationProxy implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private final String triggerId;
        private final String executionId;
        private final int maxConsecutiveExceptions;
        private final List<Partition> partitions;
        private final AgentConfiguration agent;

        private SerializationProxy(@NonNull ContinuousTriggerMessage message) {
            this.triggerId = message.triggerId;
            this.executionId = message.executionId;
            this.maxConsecutiveExceptions = message.maxConsecutiveExceptions;
            this.partitions = message.partitions;
            this.agent = message.agent;
        }

        @Serial
        private Object readResolve() throws ObjectStreamException {
            return new ContinuousTriggerMessage(
                    Objects.requireNonNull(triggerId),
                    Objects.requireNonNull(executionId),
                    maxConsecutiveExceptions,
                    Objects.requireNonNull(partitions),
                    Objects.requireNonNull(agent)
            );
        }
    }
}