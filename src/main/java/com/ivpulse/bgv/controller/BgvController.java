
package com.ivpulse.bgv.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivpulse.bgv.dto.BgvRowDto;
import com.ivpulse.bgv.dto.BgvSummaryDto;
import com.ivpulse.bgv.service.BgvService;
import com.ivpulse.kpi.dto.KpiFilter;
import com.ivpulse.kpi.dto.PageEnvelope;
import com.ivpulse.kpi.dto.ResponseEnvelope;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bgv")
public class BgvController {

    private final BgvService service;

    public BgvController(BgvService service) {
		super();
		this.service = service;
	}

	@GetMapping("/summary")
    @Cacheable(value = "bgvSummary", key = "T(java.util.Objects).hash(#clientId,#engagementId,#roleId,#location)")
    public ResponseEnvelope<BgvSummaryDto> summary(
            @RequestParam(required = false) String clientId,
            @RequestParam(required = false) String engagementId,
            @RequestParam(required = false) String roleId,
            @RequestParam(required = false) String location
    ) {
        var filter = new KpiFilter(clientId, engagementId, roleId, location);
        return ResponseEnvelope.ok(service.summary(filter), "BGV summary computed");
    }

    @GetMapping("/breakdown")
    @Cacheable(value = "bgvBreakdown", key = "T(java.util.Objects).hash(#clientId,#engagementId,#roleId,#location,#page,#size,#sort)")
    public ResponseEnvelope<PageEnvelope<BgvRowDto>> breakdown(
            @RequestParam(required = false) String clientId,
            @RequestParam(required = false) String engagementId,
            @RequestParam(required = false) String roleId,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(required = false) String sort
    ) {
        var filter = new KpiFilter(clientId, engagementId, roleId, location);
        return ResponseEnvelope.ok(service.breakdown(filter, page, size, sort), "BGV breakdown computed");
    }
}
