package ru.joke.am.protocol.server;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Accessors(fluent = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ScheduledTriggerMessage extends TriggerMessage {

    @NonNull
    private final List<AgentConfiguration> agents;
    private final int maxExecutionTime;

    @Builder(toBuilder = true)
    public ScheduledTriggerMessage(
            @NonNull String triggerId,
            @NonNull String executionId,
            int maxConsecutiveExceptions,
            int maxExecutionTime,
            @NonNull List<Partition> partitions,
            @NonNull List<AgentConfiguration> agents
    ) {
        if (agents.isEmpty()) {
            throw new IllegalArgumentException("Expected at least one agent config");
        }
        if (maxExecutionTime < 0) {
            throw new IllegalArgumentException("Max execution time must be non-negative");
        }

        super(triggerId, executionId, maxConsecutiveExceptions, partitions);
        this.agents = agents;
        this.maxExecutionTime = maxExecutionTime;
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
        private final int maxExecutionTime;
        private final List<Partition> partitions;
        private final List<AgentConfiguration> agents;

        private SerializationProxy(@NonNull ScheduledTriggerMessage message) {
            this.triggerId = message.triggerId;
            this.executionId = message.executionId;
            this.maxConsecutiveExceptions = message.maxConsecutiveExceptions;
            this.maxExecutionTime = message.maxExecutionTime;
            this.partitions = message.partitions;
            this.agents = message.agents;
        }

        @Serial
        private Object readResolve() throws ObjectStreamException {
            return new ScheduledTriggerMessage(
                    Objects.requireNonNull(triggerId),
                    Objects.requireNonNull(executionId),
                    maxConsecutiveExceptions,
                    maxExecutionTime,
                    Objects.requireNonNull(partitions),
                    Objects.requireNonNull(agents)
            );
        }
    }
}