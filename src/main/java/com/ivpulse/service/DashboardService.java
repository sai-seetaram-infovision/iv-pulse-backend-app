
package com.ivpulse.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivpulse.dto.dashboard.BgvItemDto;
import com.ivpulse.dto.dashboard.CountsDto;
import com.ivpulse.dto.dashboard.UnbilledResourceDto;

public interface DashboardService {
    CountsDto getCounts(LocalDate asOn);
    Page<UnbilledResourceDto> getUnbilled(LocalDate asOn, Pageable pageable);
    Page<BgvItemDto> getBgvInProgress(LocalDate asOn, Pageable pageable);
}
