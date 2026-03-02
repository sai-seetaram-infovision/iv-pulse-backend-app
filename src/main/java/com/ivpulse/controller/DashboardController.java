
package com.ivpulse.controller;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivpulse.dto.dashboard.BgvItemDto;
import com.ivpulse.dto.dashboard.CountsDto;
import com.ivpulse.dto.dashboard.UnbilledResourceDto;
import com.ivpulse.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;

	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@GetMapping("/counts")
	public CountsDto counts(
			@RequestParam(value = "asOn", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate asOn) {
		if (asOn == null)
			asOn = LocalDate.now();
		return dashboardService.getCounts(asOn);
	}

	@GetMapping("/unbilled")
	public Page<UnbilledResourceDto> unbilled(
			@RequestParam(value = "asOn", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate asOn,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
		if (asOn == null)
			asOn = LocalDate.now();
		Pageable pageable = PageRequest.of(page, Math.min(size, 200));
		return dashboardService.getUnbilled(asOn, pageable);
	}

	@GetMapping("/bgv-in-progress")
	public Page<BgvItemDto> bgvInProgress(
			@RequestParam(value = "asOn", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate asOn,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
		if (asOn == null)
			asOn = LocalDate.now();
		Pageable pageable = PageRequest.of(page, Math.min(size, 200));
		return dashboardService.getBgvInProgress(asOn, pageable);
	}
}
