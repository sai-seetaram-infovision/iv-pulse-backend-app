package com.ivpulse.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Produces a canonical JSON string (sorted keys, no extra whitespace)
 * so that hashes are stable.
 */
public final class JsonUtils {
    private static final ObjectMapper MAPPER = JsonMapper.builder()
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
            .build();

    private JsonUtils() {}

    public static String canonicalize(String json) {
        if (json == null || json.isBlank()) return json;
        try {
            var node = MAPPER.readTree(json);
            return MAPPER.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            // if not JSON, just return as-is
            return json;
        }
    }
}
