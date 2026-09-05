package ru.joke.am.protocol;

import lombok.NonNull;

public interface Message {

    @NonNull
    byte[] data();

    @NonNull
    String dataType();
}
