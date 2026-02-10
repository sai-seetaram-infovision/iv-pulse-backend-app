
package com.ivpulse.logging;

import org.slf4j.MDC;
import java.util.UUID;

public class CorrelationIdMdcUtil {

    public static final String CORRELATION_ID = "correlationId";

    public static void set(String id) {
        MDC.put(CORRELATION_ID, id);
    }

    public static void clear() {
        MDC.remove(CORRELATION_ID);
    }

    public static String getOrCreate() {
        String id = MDC.get(CORRELATION_ID);
        if (id == null) {
            id = UUID.randomUUID().toString();
            MDC.put(CORRELATION_ID, id);
        }
        return id;
    }
}
