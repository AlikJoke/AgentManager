package ru.joke.am.protocol.server;

import com.google.protobuf.ListValue;
import com.google.protobuf.NullValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import ru.joke.am.protocol.ConcreteProtoMessageConverter;
import ru.joke.am.protocol.server.datasource.jms.DestinationType;
import ru.joke.am.protocol.server.datasource.jms.JmsDataSourceConfiguration;
import ru.joke.am.protocol.server.datasource.kafka.KafkaDataSourceConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AnyTriggerMessageConcreteConverterSupport<T extends TriggerMessage> implements ConcreteProtoMessageConverter<T> {

    AgentConfiguration toPlainAgentConfig(AgentConfigurationProto protoAgentConfig) {
        DataSourceConfiguration dataSource = null;
        if (protoAgentConfig.hasJmsDataSource()) {
            dataSource = toPlainJmsDataSource(protoAgentConfig.getJmsDataSource());
        } else if (protoAgentConfig.hasKafkaDataSource()) {
            dataSource = toPlainKafkaDataSource(protoAgentConfig.getKafkaDataSource());
        }

        return AgentConfiguration.builder()
                    .agentId(protoAgentConfig.getAgentId())
                    .agentConfigId(protoAgentConfig.getAgentConfigId())
                    .retries(protoAgentConfig.getRetries())
                    .name(protoAgentConfig.getName())
                    .duplicationStartupPolicy(DuplicationStartupPolicy.from(protoAgentConfig.getDuplicationStartupPolicyValue()))
                    .threads(protoAgentConfig.getThreads())
                    .parameters(parseStructValue(protoAgentConfig.getParameters()))
                    .dataSource(dataSource)
                .build();
    }

    List<Partition> toPlainPartitions(List<PartitionProto> protoPartitions) {
        return protoPartitions
                .stream()
                .map(this::toPlainPartition)
                .toList();
    }

    AgentConfigurationProto toProtoAgentConfig(AgentConfiguration plainAgentConfig) {

        final AgentConfigurationProto.Builder builder = AgentConfigurationProto.newBuilder();
        switch (plainAgentConfig.dataSource()) {
            case JmsDataSourceConfiguration jms -> builder.setJmsDataSource(toProtoJmsDataSource(jms));
            case KafkaDataSourceConfiguration kafka -> builder.setKafkaDataSource(toProtoKafkaDataSource(kafka));
            case null, default -> {}
        }

        return builder
                .setAgentConfigId(plainAgentConfig.agentConfigId())
                .setAgentId(plainAgentConfig.agentId())
                .setName(plainAgentConfig.name())
                .setDuplicationStartupPolicyValue(plainAgentConfig.duplicationStartupPolicy().id())
                .setRetries(plainAgentConfig.retries())
                .setThreads(plainAgentConfig.threads())
                .setParameters(toProtoStructMap(plainAgentConfig.parameters()))
                .build();
    }

    List<PartitionProto> toProtoPartitions(List<Partition> plainPartitions) {
        return plainPartitions
                .stream()
                .map(this::toProtoPartition)
                .toList();
    }

    private PartitionProto toProtoPartition(Partition partition) {
        return PartitionProto.newBuilder()
                    .setHost(partition.host())
                    .setIndex(partition.partitionIndex())
                .build();
    }

    private Partition toPlainPartition(PartitionProto proto) {
        return new Partition(proto.getHost(), proto.getIndex());
    }

    private JmsDataSourceConfiguration toPlainJmsDataSource(JmsDataSourceConfigurationProto proto) {
        return JmsDataSourceConfiguration.builder()
                    .connectionFactory(proto.getConnectionFactory())
                    .messageSelector(proto.hasMessageSelector() ? proto.getMessageSelector() : null)
                    .destination(proto.getDestination())
                    .destinationType(DestinationType.from(proto.getDestinationTypeValue()))
                    .subscriptionId(proto.hasSubscriptionId() ? proto.getSubscriptionId() : null)
                    .isDurable(proto.getIsDurable())
                .build();
    }

    private KafkaDataSourceConfiguration toPlainKafkaDataSource(KafkaDataSourceConfigurationProto proto) {
        return KafkaDataSourceConfiguration.builder()
                .messageSelector(proto.hasMessageSelector() ? proto.getMessageSelector() : null)
                .destination(proto.getDestination())
                .subscriptionId(proto.getSubscriptionId())
                .consumerProperties(parseStructValue(proto.getConsumerProperties()))
                .build();
    }

    private KafkaDataSourceConfigurationProto toProtoKafkaDataSource(KafkaDataSourceConfiguration kafkaDataSource) {
        final KafkaDataSourceConfigurationProto.Builder builder = KafkaDataSourceConfigurationProto.newBuilder();
        if (kafkaDataSource.messageSelector() != null) {
            builder.setMessageSelector(kafkaDataSource.messageSelector());
        }

        return builder
                    .setSubscriptionId(kafkaDataSource.subscriptionId())
                    .setDestination(kafkaDataSource.destination())
                    .setConsumerProperties(toProtoStructMap(kafkaDataSource.consumerProperties()))
                .build();
    }

    private JmsDataSourceConfigurationProto toProtoJmsDataSource(JmsDataSourceConfiguration jmsDataSource) {
        final JmsDataSourceConfigurationProto.Builder builder = JmsDataSourceConfigurationProto.newBuilder();
        if (jmsDataSource.subscriptionId() != null) {
            builder.setSubscriptionId(jmsDataSource.subscriptionId());
        }
        if (jmsDataSource.messageSelector() != null) {
            builder.setMessageSelector(jmsDataSource.messageSelector());
        }

        return builder
                    .setConnectionFactory(jmsDataSource.connectionFactory())
                    .setDestination(jmsDataSource.destination())
                    .setDestinationTypeValue(jmsDataSource.destinationType().id())
                    .setIsDurable(jmsDataSource.isDurable())
                .build();
    }

    private Map<String, Object> parseStructValue(Struct struct) {
        final Map<String, Object> result = new HashMap<>();
        struct.getFieldsMap().forEach((k, v) -> {
            final Object value = toObjectValue(v);
            if (value != null) {
                result.put(k, value);
            }
        });

        return result;
    }

    private Object toObjectValue(Value value) {
        return switch (value) {
            case Value v when v.hasStringValue() -> v.getStringValue();
            case Value v when v.hasBoolValue() -> v.getBoolValue();
            case Value v when v.hasNumberValue() -> v.getNumberValue();
            case Value v when v.hasStructValue() -> parseStructValue(v.getStructValue());
            case Value v when v.hasNullValue() -> null;
            case Value v when v.hasListValue() -> v.getListValue().getValuesList()
                                                                    .stream()
                                                                    .map(this::toObjectValue)
                                                                    .toList();
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    private Struct toProtoStructMap(Map<String, Object> map) {
        Struct.Builder builder = Struct.newBuilder();
        map.forEach((k, v) -> builder.putFields(k, toProtoValue(v)));

        return builder.build();
    }

    private Value toProtoValue(Object obj) {
        final Value.Builder builder = Value.newBuilder();

        switch (obj) {
            case Boolean b -> builder.setBoolValue(b);
            case Number number -> builder.setNumberValue(number.doubleValue());
            case String s -> builder.setStringValue(s);
            case Map<?, ?> map -> {
                @SuppressWarnings("unchecked")
                final Map<String, Object> nestedMap = (Map<String, Object>) map;
                builder.setStructValue(toProtoStructMap(nestedMap));
            }
            case List<?> list -> {
                final ListValue.Builder listBuilder = ListValue.newBuilder();
                list.forEach(item -> listBuilder.addValues(toProtoValue(item)));

                builder.setListValue(listBuilder.build());
            }
            case null -> builder.setNullValue(NullValue.NULL_VALUE);
            default -> builder.setStringValue(obj.toString());
        }

        return builder.build();
    }
}
