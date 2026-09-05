package ru.joke.am.protocol.server;

import lombok.NonNull;
import lombok.SneakyThrows;
import ru.joke.am.protocol.ConcreteProtoMessageConverter;

public final class StopTriggerMessageConcreteProtoConverter implements ConcreteProtoMessageConverter<StopTriggerMessage> {

    @Override
    @SneakyThrows
    @NonNull
    public StopTriggerMessage toObject(@NonNull byte[] data) {
        final StopTriggerMessageProto proto = StopTriggerMessageProto.parseFrom(data);
        return new StopTriggerMessage(proto.getTriggerId());
    }

    @Override
    @NonNull
    public byte[] toBytes(@NonNull StopTriggerMessage obj) {
        final StopTriggerMessageProto proto =
                StopTriggerMessageProto.newBuilder()
                            .setTriggerId(obj.triggerId())
                        .build();
        return proto.toByteArray();
    }
}
