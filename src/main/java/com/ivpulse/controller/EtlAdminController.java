package com.ivpulse.controller;

import java.time.OffsetDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivpulse.dto.common.InitialLoadResult;
import com.ivpulse.dto.dashboard.ApiEnvelope;
import com.ivpulse.etl.initial.InitialLoadService;

@RestController
@RequestMapping("/api/etl")
public class EtlAdminController {

	private final InitialLoadService initialLoadService;

	public EtlAdminController(InitialLoadService initialLoadService) {
		this.initialLoadService = initialLoadService;
	}

	@PostMapping("/initial-load")
	public ResponseEntity<ApiEnvelope<InitialLoadResult>> initialLoad() {
		InitialLoadResult result = initialLoadService.runInitialLoad();
		var env = ApiEnvelope.of(result, result.getTotalWritten() == 0, OffsetDateTime.now(), "Initial load complete");
		return ResponseEntity.ok(env);
	}
}
