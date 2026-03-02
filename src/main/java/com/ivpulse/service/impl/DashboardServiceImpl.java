
package com.ivpulse.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ivpulse.dto.dashboard.BgvItemDto;
import com.ivpulse.dto.dashboard.CountsDto;
import com.ivpulse.dto.dashboard.UnbilledResourceDto;
import com.ivpulse.repository.BgvCaseRepository;
import com.ivpulse.repository.LeaveHistoryRepository;
import com.ivpulse.repository.ResourceHistoryRepository;
import com.ivpulse.repository.ResourceRepository;
import com.ivpulse.service.DashboardService;


@Service
public class DashboardServiceImpl implements DashboardService {

	private final ResourceRepository resourceRepository;
	private final ResourceHistoryRepository resourceHistoryRepository;
	private final BgvCaseRepository bgvCaseRepository;
	private final LeaveHistoryRepository leaveHistoryRepository;

	public DashboardServiceImpl(ResourceRepository resourceRepository,
			ResourceHistoryRepository resourceHistoryRepository, BgvCaseRepository bgvCaseRepository,
			LeaveHistoryRepository leaveHistoryRepository) {
		super();
		this.resourceRepository = resourceRepository;
		this.resourceHistoryRepository = resourceHistoryRepository;
		this.bgvCaseRepository = bgvCaseRepository;
		this.leaveHistoryRepository = leaveHistoryRepository;
	}

	@Override
	public CountsDto getCounts(LocalDate asOn) {
		// As per requirement, snapshot counts; AsOn kept for forward compatibility
		long total = resourceRepository.count();
		long billable = resourceRepository.countByIsBillableTrue();
		long nonBillable = resourceRepository.countByIsBillableFalse();
		long enabling = resourceRepository.countEnablingUnit();
		long bench = resourceRepository.countBenchByProjectId(56L);
		long notice = resourceRepository.countOnNotice();
		// maternity via leave year - for now try current FY by ly.ext_id or current
		// year id; fallback 0
//		long maternity = leaveHistoryRepository.findApprovedMaternityByLeaveYear(String.valueOf(asOn.getYear()))
//				.stream().count();

		CountsDto dto = new CountsDto();
		dto.setTotalHc(total);
		dto.setTotalBillableHc(billable);
		dto.setTotalNonBillableHc(nonBillable);
		dto.setTotalEnablingHc(enabling);
		dto.setTotalBenchHc(bench);
		dto.setTotalOnNoticeHc(notice);
//		dto.setTotalOnMaternityHc(maternity);

		return dto;

	}

	@Override
	public Page<UnbilledResourceDto> getUnbilled(LocalDate asOn, Pageable pageable) {
		Page<Object[]> raw = resourceHistoryRepository.findUnbilledTransitions(pageable);
		List<UnbilledResourceDto> list = raw.stream().map(o -> new UnbilledResourceDto((String) o[0], (String) o[1],
				(String) o[2], o[3] == null ? 0L : ((Number) o[3]).longValue(), (String) o[4]))
				.collect(Collectors.toList());
		return new PageImpl<>(list, pageable, raw.getTotalElements());
	}

	@Override
	public Page<BgvItemDto> getBgvInProgress(LocalDate asOn, Pageable pageable) {
		Page<Object[]> raw = bgvCaseRepository.findInProgress(pageable);
		List<BgvItemDto> list = raw.stream().map(o -> {
			String name = (String) o[0];
			String project = (String) o[1];
			String status = (String) o[2];
			java.sql.Date started = (java.sql.Date) o[3];
			LocalDate startedOn = started == null ? null : started.toLocalDate();
			long aging = startedOn == null ? 0 : ChronoUnit.DAYS.between(startedOn, asOn);
			return new BgvItemDto(name, project, status, startedOn, aging);
		}).collect(Collectors.toList());
		return new PageImpl<>(list, pageable, raw.getTotalElements());
	}

}
