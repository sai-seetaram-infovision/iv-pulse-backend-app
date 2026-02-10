package com.ivpulse.etl.processor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivpulse.dto.productivv.BillingSnapshotDto;
import com.ivpulse.dto.productivv.CostCenterDto;
import com.ivpulse.dto.productivv.EmployeeDto;
import com.ivpulse.dto.productivv.HiringDto;
import com.ivpulse.dto.productivv.OnboardingDto;
import com.ivpulse.dto.productivv.ProjectDto;
import com.ivpulse.dto.productivv.ProjectRateDto;
import com.ivpulse.dto.productivv.ResourceAssignmentDto;
import com.ivpulse.dto.productivv.RoleRefDto;
import com.ivpulse.dto.productivv.TimesheetDto;
import com.ivpulse.entity.BillingSnapshot;
import com.ivpulse.entity.Client;
import com.ivpulse.entity.Engagement;
import com.ivpulse.entity.EngagementResource;
import com.ivpulse.entity.HiringStatus;
import com.ivpulse.entity.OnboardingStatus;
import com.ivpulse.entity.RateCard;
import com.ivpulse.entity.ResourceEntity;
import com.ivpulse.entity.RoleMaster;
import com.ivpulse.entity.TimesheetSnapshot;
import com.ivpulse.entity.TimesheetSnapshotId;
import com.ivpulse.etl.staging.StagingEntity;
import com.ivpulse.etl.staging.StagingRecord;
import com.ivpulse.etl.staging.StagingStatus;
import com.ivpulse.repository.BillingSnapshotRepository;
import com.ivpulse.repository.ClientRepository;
import com.ivpulse.repository.EngagementRepository;
import com.ivpulse.repository.EngagementResourceRepository;
import com.ivpulse.repository.HiringStatusRepository;
import com.ivpulse.repository.OnboardingStatusRepository;
import com.ivpulse.repository.RateCardRepository;
import com.ivpulse.repository.ResourceRepository;
import com.ivpulse.repository.RoleMasterRepository;
import com.ivpulse.repository.StagingRecordRepository;
import com.ivpulse.repository.TimesheetSnapshotRepository;
import com.ivpulse.util.ProductivvMapper;

@Service
public class StagingBatchProcessor {
	private static final Logger log = LoggerFactory.getLogger(StagingBatchProcessor.class);

	private final StagingRecordRepository stagingRepo;
	private final ClientRepository clientRepo;
	private final EngagementRepository engagementRepo;
	private final RoleMasterRepository roleRepo;
	private final RateCardRepository rateRepo;
	private final ResourceRepository resourceRepo;
	private final EngagementResourceRepository erRepo;
	private final HiringStatusRepository hiringRepo;
	private final OnboardingStatusRepository onboardingRepo;
	private final BillingSnapshotRepository billingRepo;
	private final TimesheetSnapshotRepository tsRepo;
	private final ObjectMapper mapper ;

	

	public StagingBatchProcessor(StagingRecordRepository stagingRepo, ClientRepository clientRepo,
			EngagementRepository engagementRepo, RoleMasterRepository roleRepo, RateCardRepository rateRepo,
			ResourceRepository resourceRepo, EngagementResourceRepository erRepo, HiringStatusRepository hiringRepo,
			OnboardingStatusRepository onboardingRepo, BillingSnapshotRepository billingRepo,
			TimesheetSnapshotRepository tsRepo, ObjectMapper mapper) {
		super();
		this.stagingRepo = stagingRepo;
		this.clientRepo = clientRepo;
		this.engagementRepo = engagementRepo;
		this.roleRepo = roleRepo;
		this.rateRepo = rateRepo;
		this.resourceRepo = resourceRepo;
		this.erRepo = erRepo;
		this.hiringRepo = hiringRepo;
		this.onboardingRepo = onboardingRepo;
		this.billingRepo = billingRepo;
		this.tsRepo = tsRepo;
		this.mapper = mapper;
	}

	/** Processes a single entity type in pages, returns processed count. */
	@Transactional
	public int process(StagingEntity entity, int pageSize, int maxPages) {
		int processed = 0;
		for (int page = 0; page < maxPages; page++) {
			var batch = stagingRepo.findForProcessing(entity, List.of(StagingStatus.NEW, StagingStatus.FAILED),
					LocalDate.now().minusDays(90), PageRequest.of(0, pageSize));
			if (batch.isEmpty())
				break;
			for (StagingRecord rec : batch) {
				try {
					switch (entity) {
					case CLIENT -> handleClient(rec);
					case ENGAGEMENT -> handleEngagement(rec);
					case ROLE_MASTER -> handleRole(rec);
					case RATE_CARD -> handleRate(rec);
					case RESOURCE -> handleResource(rec);
					case ENGAGEMENT_RESOURCE -> handleEngagementResource(rec);
					case HIRING_STATUS -> handleHiring(rec);
					case ONBOARDING_STATUS -> handleOnboarding(rec);
					case BILLING_SNAPSHOT -> handleBilling(rec);
					case TIMESHEET_SNAPSHOT -> handleTimesheet(rec);
					default -> {
						/* ignore */ }
					}
					rec.setStatus(StagingStatus.PROCESSED);
					rec.setProcessedAt(OffsetDateTime.now());
					rec.setErrorMessage(null);
					processed++;
				} catch (Exception ex) {
					log.error("Staging processing failed for id={} entity={}", rec.getId(), rec.getEntityName(), ex);
					rec.setStatus(StagingStatus.FAILED);
					rec.setProcessedAt(OffsetDateTime.now());
					rec.setErrorMessage(ex.getMessage());
				}
			}
			stagingRepo.saveAll(batch);
		}
		return processed;
	}

	private void handleClient(StagingRecord rec) throws Exception {
		CostCenterDto dto = mapper.readValue(rec.getPayload(), CostCenterDto.class);
		Client entity = ProductivvMapper.toClient(dto);
		var existing = clientRepo.findById(entity.getClientId()).orElse(null);
		if (existing == null)
			clientRepo.save(entity);
		else {
			existing.setClientCode(entity.getClientCode());
			existing.setClientName(entity.getClientName());
			existing.setRegion(entity.getRegion());
			existing.setIndustry(entity.getIndustry());
			existing.setStatus(entity.getStatus());
			clientRepo.save(existing);
		}
	}

	private void handleEngagement(StagingRecord rec) throws Exception {
		ProjectDto dto = mapper.readValue(rec.getPayload(), ProjectDto.class);
		UUID clientId = com.ivpulse.util.UuidV5.forName("client", String.valueOf(dto.getCostCenterId()));
		Client client = clientRepo.findById(clientId)
				.orElseThrow(() -> new IllegalStateException("Client not found:" + dto.getCostCenterId()));
		Engagement entity = ProductivvMapper.toEngagement(dto, client);
		var existing = engagementRepo.findById(entity.getEngagementId()).orElse(null);
		if (existing == null)
			engagementRepo.save(entity);
		else {
			existing.setClient(client);
			existing.setEngagementCode(entity.getEngagementCode());
			existing.setEngagementName(entity.getEngagementName());
			existing.setStartDate(entity.getStartDate());
			existing.setEndDatePlanned(entity.getEndDatePlanned());
			existing.setEngagementStatus(entity.getEngagementStatus());
			existing.setBillingCurrency(entity.getBillingCurrency());
			engagementRepo.save(existing);
		}
	}

	private void handleRole(StagingRecord rec) throws Exception {
		RoleRefDto dto = mapper.readValue(rec.getPayload(), RoleRefDto.class);
		RoleMaster entity = ProductivvMapper.toRole(dto);
		var existing = roleRepo.findById(entity.getRoleId()).orElse(null);
		if (existing == null)
			roleRepo.save(entity);
		else {
			existing.setRoleName(entity.getRoleName());
			existing.setRoleCategory(entity.getRoleCategory());
			existing.setActiveFlag(entity.getActiveFlag());
			roleRepo.save(existing);
		}
	}

	private void handleRate(StagingRecord rec) throws Exception {
		ProjectRateDto dto = mapper.readValue(rec.getPayload(), ProjectRateDto.class);
		UUID engId = com.ivpulse.util.UuidV5.forName("engagement", String.valueOf(dto.getProjectId()));
		Engagement engagement = engagementRepo.findById(engId)
				.orElseThrow(() -> new IllegalStateException("Engagement not found:" + dto.getProjectId()));
		RoleMaster role = null;
		if (dto.getTitle() != null) {
			// try to find role by name if exists
			role = roleRepo.findByRoleName(dto.getTitle()).orElse(null);
		}
		RateCard entity = ProductivvMapper.toRate(dto, engagement, role);
		var existing = rateRepo.findById(entity.getRateId()).orElse(null);
		if (existing == null)
			rateRepo.save(entity);
		else {
			existing.setEngagement(engagement);
			existing.setRole(role);
			existing.setRateAmount(entity.getRateAmount());
			existing.setRateType(entity.getRateType());
			existing.setEffectiveFrom(entity.getEffectiveFrom());
			existing.setEffectiveTo(entity.getEffectiveTo());
			existing.setReferenceOnly(entity.getReferenceOnly());
			rateRepo.save(existing);
		}
	}

	private void handleResource(StagingRecord rec) throws Exception {
		EmployeeDto dto = mapper.readValue(rec.getPayload(), EmployeeDto.class);
		ResourceEntity entity = ProductivvMapper.toResource(dto);
		var existing = resourceRepo.findById(entity.getResourceId()).orElse(null);
		if (existing == null)
			resourceRepo.save(entity);
		else {
			existing.setExternalEmployeeId(entity.getExternalEmployeeId());
			existing.setFullName(entity.getFullName());
			existing.setEmail(entity.getEmail());
			existing.setEmploymentType(entity.getEmploymentType());
			existing.setBaseLocation(entity.getBaseLocation());
			existing.setGradeLevel(entity.getGradeLevel());
			existing.setPrimarySkill(entity.getPrimarySkill());
			existing.setJoiningDate(entity.getJoiningDate());
			existing.setExitDate(entity.getExitDate());
			existing.setEmploymentStatus(entity.getEmploymentStatus());
			existing.setSourceSystem(entity.getSourceSystem());
			resourceRepo.save(existing);
		}
	}

	private void handleEngagementResource(StagingRecord rec) throws Exception {
		ResourceAssignmentDto dto = mapper.readValue(rec.getPayload(), ResourceAssignmentDto.class);
		UUID engId = com.ivpulse.util.UuidV5.forName("engagement", String.valueOf(dto.getProjectId()));
		UUID resId = com.ivpulse.util.UuidV5.forName("resource", String.valueOf(dto.getEmployeeId()));
		Engagement engagement = engagementRepo.findById(engId)
				.orElseThrow(() -> new IllegalStateException("Engagement not found:" + dto.getProjectId()));
		ResourceEntity resource = resourceRepo.findById(resId)
				.orElseThrow(() -> new IllegalStateException("Resource not found:" + dto.getEmployeeId()));
		RoleMaster role = null;
		if (dto.getRoleId() != null) {
			UUID roleId = com.ivpulse.util.UuidV5.forName("role", String.valueOf(dto.getRoleId()));
			role = roleRepo.findById(roleId).orElse(null);
		}
		EngagementResource entity = ProductivvMapper.toEngagementResource(dto, engagement, resource, role);
		var existing = erRepo.findById(entity.getEngagementResourceId()).orElse(null);
		if (existing == null)
			erRepo.save(entity);
		else {
			existing.setEngagement(engagement);
			existing.setResource(resource);
			existing.setRole(role);
			existing.setAllocationPercentage(entity.getAllocationPercentage());
			existing.setAllocationStartDate(entity.getAllocationStartDate());
			existing.setAllocationEndDate(entity.getAllocationEndDate());
			existing.setBillingStatus(entity.getBillingStatus());
			existing.setBillingStartDate(entity.getBillingStartDate());
			existing.setBillingEndDate(entity.getBillingEndDate());
			existing.setClientManager(entity.getClientManager());
			existing.setInternalManager(entity.getInternalManager());
			existing.setActiveFlag(entity.getActiveFlag());
			erRepo.save(existing);
		}
	}

	private void handleHiring(StagingRecord rec) throws Exception {
		HiringDto dto = mapper.readValue(rec.getPayload(), HiringDto.class);
		UUID resId = com.ivpulse.util.UuidV5.forName("resource", String.valueOf(dto.getEmployeeId()));
		ResourceEntity res = resourceRepo.findById(resId).orElse(null);
		UUID engId = dto.getProjectId() != null
				? com.ivpulse.util.UuidV5.forName("engagement", String.valueOf(dto.getProjectId()))
				: null;
		Engagement eng = engId != null ? engagementRepo.findById(engId).orElse(null) : null;
		if (res == null)
			throw new IllegalStateException("Resource not found:" + dto.getEmployeeId());
		HiringStatus entity = ProductivvMapper.toHiring(dto, res, eng);
		hiringRepo.save(entity); // Append as latest status row
	}

	private void handleOnboarding(StagingRecord rec) throws Exception {
		OnboardingDto dto = mapper.readValue(rec.getPayload(), OnboardingDto.class);
		UUID resId = com.ivpulse.util.UuidV5.forName("resource", String.valueOf(dto.getEmployeeId()));
		ResourceEntity res = resourceRepo.findById(resId).orElse(null);
		if (res == null)
			throw new IllegalStateException("Resource not found:" + dto.getEmployeeId());
		OnboardingStatus entity = ProductivvMapper.toOnboarding(dto, res);
		onboardingRepo.save(entity); // Append row
	}

	private void handleBilling(StagingRecord rec) throws Exception {
		BillingSnapshotDto dto = mapper.readValue(rec.getPayload(), BillingSnapshotDto.class);
		UUID erId = com.ivpulse.util.UuidV5.forName("engagement_resource",
				String.valueOf(dto.getEngagementResourceId()));
		EngagementResource er = erRepo.findById(erId)
				.orElseThrow(() -> new IllegalStateException("ER not found:" + dto.getEngagementResourceId()));
		BillingSnapshot entity = ProductivvMapper.toBillingSnapshot(dto, er);
		billingRepo.save(entity); // Append latest snapshot row
	}

	private void handleTimesheet(StagingRecord rec) throws Exception {
		TimesheetDto dto = mapper.readValue(rec.getPayload(), TimesheetDto.class);
		UUID resId = com.ivpulse.util.UuidV5.forName("resource", String.valueOf(dto.getEmployeeId()));
		UUID engId = com.ivpulse.util.UuidV5.forName("engagement", String.valueOf(dto.getProjectId()));
		// Ensure FKs exist (skip if missing to avoid FK-less records)
		if (!resourceRepo.existsById(resId))
			throw new IllegalStateException("Resource not ready:" + dto.getEmployeeId());
		if (!engagementRepo.existsById(engId))
			throw new IllegalStateException("Engagement not ready:" + dto.getProjectId());

		TimesheetSnapshot incoming = ProductivvMapper.toTimesheetSnapshot(dto, resId, engId);
		TimesheetSnapshotId id = incoming.getId();
		TimesheetSnapshot existing = tsRepo.findById(id).orElse(null);
		if (existing == null) {
			tsRepo.save(incoming);
		} else {
			boolean submitted = Boolean.TRUE.equals(existing.getSubmittedFlag())
					|| Boolean.TRUE.equals(incoming.getSubmittedFlag());
			boolean approved = Boolean.TRUE.equals(existing.getApprovedFlag())
					|| Boolean.TRUE.equals(incoming.getApprovedFlag());
			existing.setSubmittedFlag(submitted);
			existing.setApprovedFlag(approved);
			// Max approval date
			java.time.LocalDate e = existing.getApprovalDate();
			java.time.LocalDate i = incoming.getApprovalDate();
			if (i != null && (e == null || i.isAfter(e)))
				existing.setApprovalDate(i);
			tsRepo.save(existing);
		}
	}
}
