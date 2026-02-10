package com.ivpulse.controller;

import com.ivpulse.dto.common.DeltaSyncResult;
import com.ivpulse.dto.dashboard.ApiEnvelope;
import com.ivpulse.etl.delta.DeltaSyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/etl")
public class EtlDeltaController {

    private final DeltaSyncService deltaSyncService;

    public EtlDeltaController(DeltaSyncService deltaSyncService) {
        this.deltaSyncService = deltaSyncService;
    }

    @PostMapping("/delta-sync")
    public ResponseEntity<ApiEnvelope<DeltaSyncResult>> runDelta(
            @RequestParam(name = "employees", defaultValue = "true") boolean employees,
            @RequestParam(name = "currentMonth", defaultValue = "true") boolean currentMonth,
            @RequestParam(name = "lastMonth", defaultValue = "true") boolean lastMonth
    ) {
        var result = deltaSyncService.runAllDeltas(employees, currentMonth, lastMonth);
        var env = ApiEnvelope.of(result, false, OffsetDateTime.now(), "Delta sync complete");
        return ResponseEntity.ok(env);
    }
}
