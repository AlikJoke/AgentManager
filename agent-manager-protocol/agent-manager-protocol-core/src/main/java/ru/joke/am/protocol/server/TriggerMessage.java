package ru.joke.am.protocol.server;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Getter
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
public abstract class TriggerMessage implements Serializable {

    @NonNull
    protected final String triggerId;
    @NonNull
    protected final String executionId;
    @NonNull
    protected final List<Partition> partitions;
    protected final int maxConsecutiveExceptions;

    TriggerMessage(
            @NonNull String triggerId,
            @NonNull String executionId,
            int maxConsecutiveExceptions,
            @NonNull List<Partition> partitions
    ) {
        if (triggerId.isEmpty()) {
            throw new IllegalArgumentException("Trigger id must be not empty");
        }
        if (executionId.isEmpty()) {
            throw new IllegalArgumentException("Execution id must be not empty");
        }
        if (partitions.isEmpty()) {
            throw new IllegalArgumentException("Partitions must be not empty");
        }

        this.triggerId = triggerId;
        this.executionId = executionId;
        this.maxConsecutiveExceptions = maxConsecutiveExceptions;
        this.partitions = partitions;
    }

    @NonNull
    public abstract List<? extends AgentConfiguration> agents();
}