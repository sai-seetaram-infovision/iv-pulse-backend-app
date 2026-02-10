
package com.ivpulse.kpi.service;

import com.ivpulse.kpi.dto.*;

import java.time.LocalDate;

/**
 * Extension methods to keep backward compatibility with Phase 12 service while enabling filters.
 * Current implementation delegates to existing computations and ignores filters.
 * Phase 14 will wire filter-aware SQL paths.
 */
public interface KpiAggregationServiceExtensions {

    default UtilizationKpiDto computeUtilization(LocalDate asOf, KpiFilter filter) {
        return computeUtilization(asOf); // TODO: apply filters in Phase 14
    }

    default UnbilledKpiDto computeUnbilled(LocalDate asOf, KpiFilter filter) {
        return computeUnbilled(asOf); // TODO: apply filters in Phase 14
    }

    default KpiSummaryDto computeSummary(LocalDate asOf, KpiFilter filter) {
        return computeSummary(asOf); // TODO: apply filters in Phase 14
    }

    UtilizationKpiDto computeUtilization(LocalDate asOf);
    UnbilledKpiDto computeUnbilled(LocalDate asOf);
    KpiSummaryDto computeSummary(LocalDate asOf);
}
