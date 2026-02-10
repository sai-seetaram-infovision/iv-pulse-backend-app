//
//package com.ivpulse.kpi.controller;
//
//import com.ivpulse.kpi.dto.*;
//import com.ivpulse.kpi.service.FilterAwareKpiService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/kpi/unbilled")
//@RequiredArgsConstructor
//public class UnbilledBreakdownController {
//
//    private final FilterAwareKpiService service;
//
//    @GetMapping("/breakdown")
//    @Cacheable(value = "kpiUnbilledBreakdown", key = "T(java.util.Objects).hash(#asOf,#clientId,#engagementId,#roleId,#location,#page,#size,#sort)")
//    public ResponseEnvelope<PageEnvelope<UnbilledRowDto>> breakdown(
//            @RequestParam(name = "asOf", required = false)
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate asOf,
//            @RequestParam(required = false) String clientId,
//            @RequestParam(required = false) String engagementId,
//            @RequestParam(required = false) String roleId,
//            @RequestParam(required = false) String location,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "25") int size,
//            @RequestParam(required = false) String sort
//    ) {
//        LocalDate date = Optional.ofNullable(asOf).orElse(LocalDate.now());
//        var filter = new KpiFilter(clientId, engagementId, roleId, location);
//        var pageDto = service.unbilledBreakdown(date, filter, page, size, sort);
//        return ResponseEnvelope.ok(pageDto, "Unbilled breakdown computed");
//    }
//}
