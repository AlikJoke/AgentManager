package ru.joke.am.protocol.server;

import lombok.Builder;
import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Builder(toBuilder = true)
public record AgentConfiguration(
        @NonNull String name,
        @NonNull String agentConfigId,
        @NonNull String agentId,
        int threads,
        DataSourceConfiguration dataSource,
        @NonNull Map<String, Object> parameters,
        int retries,
        DuplicationStartupPolicy duplicationStartupPolicy
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public AgentConfiguration {
        if (agentId.isEmpty()) {
            throw new IllegalArgumentException("Agent id must be not empty");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Config title must be not empty");
        }
        if (agentConfigId.isEmpty()) {
            throw new IllegalArgumentException("Agent config id must be not empty");
        }
        if (threads <= 0) {
            throw new IllegalArgumentException("Thread count must be positive");
        }
        if (retries < 0) {
            throw new IllegalArgumentException("Retries count must be non-negative");
        }
    }
}
