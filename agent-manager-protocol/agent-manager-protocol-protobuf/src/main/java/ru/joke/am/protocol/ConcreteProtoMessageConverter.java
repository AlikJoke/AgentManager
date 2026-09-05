package ru.joke.am.protocol;

import lombok.NonNull;

public interface ConcreteProtoMessageConverter<T> {

    @NonNull
    T toObject(@NonNull byte[] data);

    @NonNull
    byte[] toBytes(@NonNull T obj);
}
