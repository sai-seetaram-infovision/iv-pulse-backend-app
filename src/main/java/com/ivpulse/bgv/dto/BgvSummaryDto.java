
package com.ivpulse.bgv.dto;

public record BgvSummaryDto(
        long total,
        long clear,
        long inProgress,
        long pending,
        long holdOrFail
) {}
