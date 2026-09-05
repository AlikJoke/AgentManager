package ru.joke.am.protocol.client;

import com.google.protobuf.Timestamp;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.joke.am.protocol.ConcreteProtoMessageConverter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class AgentExecutionStatusMessageConcreteProtoConverter implements ConcreteProtoMessageConverter<AgentExecutionStatusMessage> {

    @Override
    @SneakyThrows
    @NonNull
    public AgentExecutionStatusMessage toObject(@NonNull byte[] data) {
        final AgentExecutionStatusMessageProto proto = AgentExecutionStatusMessageProto.parseFrom(data);
        final Timestamp messageTimestamp = proto.getMessageDateTime();

        final ZonedDateTime messageDateTime = ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(messageTimestamp.getSeconds(), messageTimestamp.getNanos()),
                ZoneId.of(proto.getMessageTimeZone())
        );

        return AgentExecutionStatusMessage.builder()
                    .host(proto.getHost())
                    .application(proto.getAppName())
                    .status(AgentExecutionStatus.from(proto.getStatusValue()))
                    .messageDateTime(messageDateTime)
                    .port(proto.getPort())
                    .executionId(proto.getExecutionId())
                    .agentConfigId(proto.getAgentConfigId())
                    .triggerId(proto.getTriggerId())
                    .info(proto.getInfo())
                .build();
    }

    @Override
    @NonNull
    public byte[] toBytes(@NonNull AgentExecutionStatusMessage obj) {
        final AgentExecutionStatusProto protoStatus = AgentExecutionStatusProto.forNumber(obj.status().id());
        final ZonedDateTime messageDateTime = obj.messageDateTime();
        final Timestamp messageTimestamp =
                Timestamp.newBuilder()
                            .setSeconds(messageDateTime.toEpochSecond())
                            .setNanos(messageDateTime.getNano())
                        .build();

        final AgentExecutionStatusMessageProto proto =
                AgentExecutionStatusMessageProto.newBuilder()
                            .setStatus(protoStatus)
                            .setMessageDateTime(messageTimestamp)
                            .setMessageTimeZone(messageDateTime.getZone().getId())
                            .setPort(obj.port())
                            .setHost(obj.host())
                            .setAppName(obj.application())
                            .setInfo(obj.info())
                            .setExecutionId(obj.executionId())
                            .setTriggerId(obj.triggerId())
                            .setAgentConfigId(obj.agentConfigId())
                        .build();
        return proto.toByteArray();
    }
}
