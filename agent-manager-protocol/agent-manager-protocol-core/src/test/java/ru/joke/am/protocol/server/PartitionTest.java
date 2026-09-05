package ru.joke.am.protocol.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartitionTest {

    @Test
    void testCreationWithValidState() {
        final Partition partition = new Partition("h1", 1);

        assertNotNull(partition, "Object to check must be not null");
        assertEquals("h1", partition.host(), "Host name must be equal");
        assertEquals(1, partition.partitionIndex(), "Partition index must be equal");
    }

    @Test
    void testCreationWithInvalidState() {
        assertThrows(IllegalArgumentException.class, () -> new Partition("h1", -1));
        assertThrows(IllegalArgumentException.class, () -> new Partition("", 1));
    }
}
