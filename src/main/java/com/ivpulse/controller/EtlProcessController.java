package com.ivpulse.controller;

import java.time.OffsetDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivpulse.dto.common.ProcessingSummaryDto;
import com.ivpulse.dto.dashboard.ApiEnvelope;
import com.ivpulse.etl.processor.StagingProcessingService;

@RestController
@RequestMapping("/api/etl")
public class EtlProcessController {

	private final StagingProcessingService service;

	public EtlProcessController(StagingProcessingService service) {
		this.service = service;
	}

	@PostMapping("/process-staging")
	public ResponseEntity<ApiEnvelope<ProcessingSummaryDto>> process(
			@RequestParam(defaultValue = "500") Integer pageSize, @RequestParam(defaultValue = "20") Integer maxPages) {
		var result = service.processAll(pageSize, maxPages);
		var env = ApiEnvelope.of(result, result.getProcessed() == 0, OffsetDateTime.now(),
				"Staging processing complete");
		return ResponseEntity.ok(env);
	}

}
