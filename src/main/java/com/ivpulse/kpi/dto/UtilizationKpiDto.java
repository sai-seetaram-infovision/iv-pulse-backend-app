
package com.ivpulse.kpi.dto;

import java.time.YearMonth;

public record UtilizationKpiDto(
        YearMonth month,
        double availableHours,
        double billableHours,
        double utilizationPercent
) {}
