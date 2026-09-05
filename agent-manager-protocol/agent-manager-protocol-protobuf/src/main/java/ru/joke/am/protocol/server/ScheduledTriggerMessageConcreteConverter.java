package ru.joke.am.protocol.server;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.List;

public final class ScheduledTriggerMessageConcreteConverter extends AnyTriggerMessageConcreteConverterSupport<ScheduledTriggerMessage> {

    @Override
    @SneakyThrows
    @NonNull
    public ScheduledTriggerMessage toObject(@NonNull byte[] data) {
        final ScheduledTriggerMessageProto proto = ScheduledTriggerMessageProto.parseFrom(data);
        final List<AgentConfiguration> agentConfigs =
                proto.getAgentsList()
                        .stream()
                        .map(this::toPlainAgentConfig)
                        .toList();

        final List<Partition> partitions = toPlainPartitions(proto.getPartitionsList());

        return ScheduledTriggerMessage.builder()
                    .triggerId(proto.getTriggerId())
                    .executionId(proto.getExecutionId())
                    .maxConsecutiveExceptions(proto.getMaxConsecutiveExceptions())
                    .agents(agentConfigs)
                    .partitions(partitions)
                    .maxExecutionTime(proto.getMaxExecutionTime())
                .build();
    }

    @Override
    @NonNull
    public byte[] toBytes(@NonNull ScheduledTriggerMessage obj) {
        final List<AgentConfigurationProto> agentConfigs =
                obj.agents()
                        .stream()
                        .map(this::toProtoAgentConfig)
                        .toList();
        final List<PartitionProto> partitions = toProtoPartitions(obj.partitions());

        final ScheduledTriggerMessageProto proto =
                ScheduledTriggerMessageProto.newBuilder()
                            .setTriggerId(obj.triggerId())
                            .setExecutionId(obj.executionId())
                            .setMaxConsecutiveExceptions(obj.maxConsecutiveExceptions())
                            .addAllAgents(agentConfigs)
                            .addAllPartitions(partitions)
                            .setMaxExecutionTime(obj.maxExecutionTime())
                        .build();
        return proto.toByteArray();
    }
}
