package com.ivpulse.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Deterministic UUID based on a namespace string and a name.
 * Uses UUID.nameUUIDFromBytes (MD5) for simplicity; stable across runs.
 */
public final class UuidV5 {
    private UuidV5() {}
    public static UUID forName(String namespace, String name) {
        String combined = namespace + ":" + name;
        return UUID.nameUUIDFromBytes(combined.getBytes(StandardCharsets.UTF_8));
    }
}
