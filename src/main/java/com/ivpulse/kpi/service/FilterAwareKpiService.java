//
//package com.ivpulse.kpi.service;
//
//import com.ivpulse.kpi.dto.*;
//import com.ivpulse.kpi.store.FilterAwareKpiRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.YearMonth;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class FilterAwareKpiService implements KpiAggregationServiceExtensions {
//
//    private final FilterAwareKpiRepository repo;
//
//    @Override
//    public UtilizationKpiDto computeUtilization(LocalDate asOf) {
//        return computeUtilization(asOf, new KpiFilter(null,null,null,null));
//    }
//
//    @Override
//    public UnbilledKpiDto computeUnbilled(LocalDate asOf) {
//        return computeUnbilled(asOf, new KpiFilter(null,null,null,null));
//    }
//
//    @Override
//    public KpiSummaryDto computeSummary(LocalDate asOf) {
//        return computeSummary(asOf, new KpiFilter(null,null,null,null));
//    }
//
//    @Override
//    public UtilizationKpiDto computeUtilization(LocalDate asOf, KpiFilter filter) {
//        YearMonth ym = YearMonth.from(asOf);
//        double available = repo.fetchAvailableHours(ym, filter);
//        double billable  = repo.fetchBillableHours(ym, filter);
//        double utilization = available > 0 ? (billable / available) * 100.0 : 0.0;
//        return new UtilizationKpiDto(ym, available, billable, utilization);
//    }
//
//    @Override
//    public UnbilledKpiDto computeUnbilled(LocalDate asOf, KpiFilter filter) {
//        YearMonth ym = YearMonth.from(asOf);
//        double hours = repo.fetchUnbilledHours(ym, filter);
//        // Use global average rate from rate_card as a baseline for tiles; detailed rates in breakdown
//        double blendedRate = com.ivpulse.kpi.service.util.RateUtil.globalAvgRate();
//        double amount = hours * blendedRate;
//        return new UnbilledKpiDto(ym, hours, blendedRate, amount);
//    }
//
//    @Override
//    public KpiSummaryDto computeSummary(LocalDate asOf, KpiFilter filter) {
//        return new KpiSummaryDto(computeUtilization(asOf, filter), computeUnbilled(asOf, filter));
//    }
//
//    public PageEnvelope<UnbilledRowDto> unbilledBreakdown(LocalDate asOf, KpiFilter filter,
//                                                          int page, int size, String sort) {
//        YearMonth ym = YearMonth.from(asOf);
//        String sortCol = "amount"; String sortDir = "DESC";
//        if (sort != null && !sort.isBlank()) {
//            String[] parts = sort.split(",");
//            if (parts.length >= 1) sortCol = parts[0];
//            if (parts.length >= 2) sortDir = parts[1];
//        }
//        long total = repo.countUnbilledBreakdown(ym, filter);
//        var rows = repo.fetchUnbilledBreakdown(ym, filter, page, size, sortCol, sortDir);
//        List<UnbilledRowDto> items = rows.stream().map(arr -> new UnbilledRowDto(
//                YearMonth.parse((String)arr[0]),
//                (String)arr[1], (String)arr[2],
//                (String)arr[3], (String)arr[4],
//                (String)arr[5], (String)arr[6],
//                (String)arr[7], (String)arr[8],
//                (String)arr[9],
//                ((Number)arr[10]).doubleValue(),
//                ((Number)arr[11]).doubleValue(),
//                ((Number)arr[12]).doubleValue()
//        )).collect(Collectors.toList());
//        int totalPages = (int)Math.ceil(total / (double)size);
//        return new PageEnvelope<>(items, page, size, total, totalPages, sortCol+","+sortDir);
//    }
//}
