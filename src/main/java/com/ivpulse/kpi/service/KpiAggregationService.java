//
//package com.ivpulse.kpi.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.YearMonth;
//import java.util.List;
//
//import com.ivpulse.kpi.dto.KpiSummaryDto;
//import com.ivpulse.kpi.dto.UtilizationKpiDto;
//import com.ivpulse.kpi.dto.UnbilledKpiDto;
//import com.ivpulse.kpi.store.KpiRepository;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class KpiAggregationService {
//
//    private final KpiRepository kpiRepository;
//
//    /**
//     * Computes account-level utilization for the month of `asOf`.
//     * Utilization% = billable_hours / available_hours (across active engagement_resources)
//     */
//    public UtilizationKpiDto computeUtilization(LocalDate asOf) {
//        YearMonth ym = YearMonth.from(asOf);
//        double available = kpiRepository.fetchAvailableHours(ym);
//        double billable = kpiRepository.fetchBillableHours(ym);
//        double utilization = available > 0 ? (billable / available) * 100.0 : 0.0;
//        return new UtilizationKpiDto(ym, available, billable, utilization);
//    }
//
//    /**
//     * Unbilled = Approved or Submitted hours in timesheet_snapshot where billing_snapshot has no matching amount
//     * (or zero) for the same (resource, engagement, month). This is a simplified KPI geared for Phase 14 expansion.
//     */
//    public UnbilledKpiDto computeUnbilled(LocalDate asOf) {
//        YearMonth ym = YearMonth.from(asOf);
//        double unbilledHours = kpiRepository.fetchUnbilledHours(ym);
//        double blendedRate = kpiRepository.fetchBlendedRate(); // fallback average if no precise match
//        double unbilledAmount = unbilledHours * blendedRate;
//        return new UnbilledKpiDto(ym, unbilledHours, blendedRate, unbilledAmount);
//    }
//
//    /**
//     * Summary wrapper calling core KPIs.
//     */
//    public KpiSummaryDto computeSummary(LocalDate asOf) {
//        UtilizationKpiDto util = computeUtilization(asOf);
//        UnbilledKpiDto unbilled = computeUnbilled(asOf);
//        return new KpiSummaryDto(util, unbilled);
//    }
//}
