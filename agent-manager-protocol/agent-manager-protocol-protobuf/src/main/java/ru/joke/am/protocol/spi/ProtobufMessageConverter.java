package ru.joke.am.protocol.spi;

import lombok.NonNull;
import ru.joke.am.protocol.ConcreteProtoMessageConverter;
import ru.joke.am.protocol.Message;
import ru.joke.am.protocol.client.AgentExecutionStatusMessage;
import ru.joke.am.protocol.client.AgentExecutionStatusMessageConcreteProtoConverter;
import ru.joke.am.protocol.client.ClientStatusMessage;
import ru.joke.am.protocol.client.ClientStatusMessageConcreteProtoConverter;
import ru.joke.am.protocol.server.*;

import java.util.Map;

public final class ProtobufMessageConverter implements MessageConverter {

    public static final String FORMAT = "protobuf";
    public static final String CONTENT_TYPE = "application/" + FORMAT;

    private final Map<Class<?>, ConcreteProtoMessageConverter<?>> objectClass2ConverterMap = Map.of(
            ClientStatusMessage.class, new ClientStatusMessageConcreteProtoConverter(),
            AgentExecutionStatusMessage.class, new AgentExecutionStatusMessageConcreteProtoConverter(),
            StopTriggerMessage.class, new StopTriggerMessageConcreteProtoConverter(),
            ContinuousTriggerMessage.class, new ContinuousTriggerMessageConcreteConverter(),
            ScheduledTriggerMessage.class, new ScheduledTriggerMessageConcreteConverter()
    );

    @Override
    @NonNull
    public <T> Message toMessage(@NonNull T obj) {
        @SuppressWarnings("unchecked")
        final ConcreteProtoMessageConverter<T> converter = (ConcreteProtoMessageConverter<T>) this.objectClass2ConverterMap.get(obj.getClass());
        if (converter == null) {
            throw new IllegalArgumentException("Unknown type: " + obj.getClass());
        }

        final byte[] data = converter.toBytes(obj);
        return new Message() {
            @Override
            @NonNull
            public byte[] data() {
                return data;
            }

            @Override
            @NonNull
            public String dataType() {
                return CONTENT_TYPE;
            }
        };
    }

    @Override
    @NonNull
    public <T> T fromMessage(@NonNull Message message, @NonNull Class<T> tokenType) {
        @SuppressWarnings("unchecked")
        final ConcreteProtoMessageConverter<T> converter = (ConcreteProtoMessageConverter<T>) this.objectClass2ConverterMap.get(tokenType);
        if (converter == null) {
            throw new IllegalArgumentException("Unknown type: " + tokenType);
        }

        return converter.toObject(message.data());
    }

    @Override
    public boolean applied(String type) {
        return FORMAT.equals(type) || CONTENT_TYPE.equals(type);
    }
}
