package ru.joke.am.protocol.server;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.List;

public final class ContinuousTriggerMessageConcreteConverter extends AnyTriggerMessageConcreteConverterSupport<ContinuousTriggerMessage> {

    @Override
    @SneakyThrows
    @NonNull
    public ContinuousTriggerMessage toObject(@NonNull byte[] data) {
        final ContinuousTriggerMessageProto proto = ContinuousTriggerMessageProto.parseFrom(data);
        final List<Partition> partitions = toPlainPartitions(proto.getPartitionsList());
        final AgentConfiguration agentConfig = toPlainAgentConfig(proto.getAgent());

        return ContinuousTriggerMessage.builder()
                    .triggerId(proto.getTriggerId())
                    .executionId(proto.getExecutionId())
                    .maxConsecutiveExceptions(proto.getMaxConsecutiveExceptions())
                    .agent(agentConfig)
                    .partitions(partitions)
                .build();
    }

    @Override
    @NonNull
    public byte[] toBytes(@NonNull ContinuousTriggerMessage obj) {
        final List<PartitionProto> partitions = toProtoPartitions(obj.partitions());
        final AgentConfigurationProto agentConfig = toProtoAgentConfig(obj.agent());

        final ContinuousTriggerMessageProto proto =
                ContinuousTriggerMessageProto.newBuilder()
                            .setTriggerId(obj.triggerId())
                            .setExecutionId(obj.executionId())
                            .setMaxConsecutiveExceptions(obj.maxConsecutiveExceptions())
                            .setAgent(agentConfig)
                            .addAllPartitions(partitions)
                        .build();
        return proto.toByteArray();
    }
}
