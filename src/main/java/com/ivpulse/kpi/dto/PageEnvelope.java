
package com.ivpulse.kpi.dto;

import java.util.List;

public record PageEnvelope<T>(
        List<T> items,
        int page,
        int size,
        long totalItems,
        int totalPages,
        String sort
) {}
