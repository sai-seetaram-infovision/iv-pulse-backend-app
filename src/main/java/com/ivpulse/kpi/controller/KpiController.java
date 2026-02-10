//
//package com.ivpulse.kpi.controller;
//
//import com.ivpulse.kpi.dto.*;
//import com.ivpulse.kpi.service.KpiAggregationService;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/kpi")
//@RequiredArgsConstructor
//public class KpiController {
//
//    private final KpiAggregationService kpiService;
//
//    public KpiController(KpiAggregationService kpiService) {
//		super();
//		this.kpiService = kpiService;
//	}
//
//	@GetMapping("/summary")
//    @Cacheable(value = "kpiSummary", key = "T(java.util.Objects).hash(#asOf, #clientId, #engagementId, #roleId, #location)")
//    public ResponseEnvelope<KpiSummaryDto> summary(
//            @RequestParam(name = "asOf", required = false)
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate asOf,
//            @RequestParam(required = false) String clientId,
//            @RequestParam(required = false) String engagementId,
//            @RequestParam(required = false) String roleId,
//            @RequestParam(required = false) String location
//    ) {
//        LocalDate date = Optional.ofNullable(asOf).orElse(LocalDate.now());
//        var filter = new KpiFilter(clientId, engagementId, roleId, location);
//        var dto = kpiService.computeSummary(date, filter);
//        return ResponseEnvelope.ok(dto, "KPI summary computed");
//    }
//
//    @GetMapping("/utilization")
//    @Cacheable(value = "kpiUtilization", key = "T(java.util.Objects).hash(#asOf, #clientId, #engagementId, #roleId, #location)")
//    public ResponseEnvelope<UtilizationKpiDto> utilization(
//            @RequestParam(name = "asOf", required = false)
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate asOf,
//            @RequestParam(required = false) String clientId,
//            @RequestParam(required = false) String engagementId,
//            @RequestParam(required = false) String roleId,
//            @RequestParam(required = false) String location
//    ) {
//        LocalDate date = Optional.ofNullable(asOf).orElse(LocalDate.now());
//        var filter = new KpiFilter(clientId, engagementId, roleId, location);
//        var dto = kpiService.computeUtilization(date, filter);
//        return ResponseEnvelope.ok(dto, "Utilization computed");
//    }
//
//    @GetMapping("/unbilled")
//    @Cacheable(value = "kpiUnbilled", key = "T(java.util.Objects).hash(#asOf, #clientId, #engagementId, #roleId, #location)")
//    public ResponseEnvelope<UnbilledKpiDto> unbilled(
//            @RequestParam(name = "asOf", required = false)
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate asOf,
//            @RequestParam(required = false) String clientId,
//            @RequestParam(required = false) String engagementId,
//            @RequestParam(required = false) String roleId,
//            @RequestParam(required = false) String location
//    ) {
//        LocalDate date = Optional.ofNullable(asOf).orElse(LocalDate.now());
//        var filter = new KpiFilter(clientId, engagementId, roleId, location);
//        var dto = kpiService.computeUnbilled(date, filter);
//        return ResponseEnvelope.ok(dto, "Unbilled computed");
//    }
//}
