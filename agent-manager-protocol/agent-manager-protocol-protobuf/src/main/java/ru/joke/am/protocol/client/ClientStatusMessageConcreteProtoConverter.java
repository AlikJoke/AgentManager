package ru.joke.am.protocol.client;

import com.google.protobuf.Timestamp;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.joke.am.protocol.ConcreteProtoMessageConverter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class ClientStatusMessageConcreteProtoConverter implements ConcreteProtoMessageConverter<ClientStatusMessage> {

    @Override
    @SneakyThrows
    @NonNull
    public ClientStatusMessage toObject(@NonNull byte[] data) {
        final ClientStatusMessageProto proto = ClientStatusMessageProto.parseFrom(data);
        final Timestamp messageTimestamp = proto.getMessageDateTime();

        final ZonedDateTime messageDateTime = ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(messageTimestamp.getSeconds(), messageTimestamp.getNanos()),
                ZoneId.of(proto.getMessageTimeZone())
        );

        return ClientStatusMessage.builder()
                    .host(proto.getHost())
                    .application(proto.getAppName())
                    .status(ClientStatus.from(proto.getStatusValue()))
                    .messageDateTime(messageDateTime)
                    .port(proto.getPort())
                .build();
    }

    @Override
    @NonNull
    public byte[] toBytes(@NonNull ClientStatusMessage obj) {
        final ClientStatusProto protoStatus = ClientStatusProto.forNumber(obj.status().id());
        final ZonedDateTime messageDateTime = obj.messageDateTime();
        final Timestamp messageTimestamp =
                Timestamp.newBuilder()
                            .setSeconds(messageDateTime.toEpochSecond())
                            .setNanos(messageDateTime.getNano())
                        .build();

        final ClientStatusMessageProto proto =
                ClientStatusMessageProto.newBuilder()
                            .setStatus(protoStatus)
                            .setMessageDateTime(messageTimestamp)
                            .setMessageTimeZone(messageDateTime.getZone().getId())
                            .setPort(obj.port())
                            .setHost(obj.host())
                            .setAppName(obj.application())
                        .build();
        return proto.toByteArray();
    }
}
