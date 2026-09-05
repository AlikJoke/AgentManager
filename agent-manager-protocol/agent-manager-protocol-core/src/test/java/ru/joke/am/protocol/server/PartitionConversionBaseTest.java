package ru.joke.am.protocol.server;

import org.junit.jupiter.api.Test;
import ru.joke.am.protocol.BaseProtocolObjectConversionTest;

import java.util.UUID;

abstract class PartitionConversionBaseTest extends BaseProtocolObjectConversionTest<Partition> {

    @Test
    void testMessageConversion() {
        final Partition data = new Partition(UUID.randomUUID().toString(), 1);
        makeObjectConversionCheck(data);
    }
}
