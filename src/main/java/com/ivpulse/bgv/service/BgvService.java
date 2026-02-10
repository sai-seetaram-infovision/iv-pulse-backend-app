
package com.ivpulse.bgv.service;

import com.ivpulse.bgv.dto.*;
import com.ivpulse.bgv.store.BgvRepository;
import com.ivpulse.kpi.dto.KpiFilter;
import com.ivpulse.kpi.dto.PageEnvelope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BgvService {

    private final BgvRepository repo;

    public BgvService(BgvRepository repo) {
		super();
		this.repo = repo;
	}

	public BgvSummaryDto summary(KpiFilter filter) {
        long total = repo.countAll(filter);
        long clear = repo.countByBucket("CLEAR", filter);
        long inProgress = repo.countByBucket("IN_PROGRESS", filter);
        long holdFail = repo.countByBucket("HOLD_OR_FAIL", filter);
        long pending = Math.max(0, total - clear - inProgress - holdFail);
        return new BgvSummaryDto(total, clear, inProgress, pending, holdFail);
    }

    public PageEnvelope<BgvRowDto> breakdown(KpiFilter filter, int page, int size, String sort) {
        String sortCol = "status"; String sortDir = "ASC";
        if (sort != null && !sort.isBlank()) {
            String[] parts = sort.split(",");
            if (parts.length >= 1) sortCol = parts[0];
            if (parts.length >= 2) sortDir = parts[1];
        }
        long total = repo.countBreakdown(filter);
        var rows = repo.fetchBreakdown(filter, page, size, sortCol, sortDir);
        List<BgvRowDto> items = rows.stream().map(arr -> new BgvRowDto(
                (String)arr[0], (String)arr[1],
                (String)arr[2], (String)arr[3],
                (String)arr[4], (String)arr[5], (String)arr[6],
                (String)arr[7], (String)arr[8],
                (String)arr[9],
                (String)arr[10], (String)arr[11],
                (java.time.LocalDate)arr[12], (java.time.LocalDate)arr[13],
                (String)arr[14]
        )).collect(Collectors.toList());
        int totalPages = (int)Math.ceil(total / (double)size);
        return new PageEnvelope<>(items, page, size, total, totalPages, sortCol+","+sortDir);
    }
}
