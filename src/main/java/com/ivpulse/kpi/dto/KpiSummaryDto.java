
package com.ivpulse.kpi.dto;

public record KpiSummaryDto(
        UtilizationKpiDto utilization,
        UnbilledKpiDto unbilled
) {}
