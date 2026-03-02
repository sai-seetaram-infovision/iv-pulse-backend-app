package com.ivpulse.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.UUID;

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
import com.ivpulse.entity.enums.BgvStatus;
import com.ivpulse.entity.enums.BillingStatus;
import com.ivpulse.entity.enums.ClientAccessStatus;
import com.ivpulse.entity.enums.ClientStatus;
import com.ivpulse.entity.enums.EmploymentStatus;
import com.ivpulse.entity.enums.EmploymentType;
import com.ivpulse.entity.enums.EngagementStatus;
import com.ivpulse.entity.enums.HiringStage;

public final class ProductivvMapper {

	private ProductivvMapper() {
	}

	// --- Clients ---
	public static Client toClient(CostCenterDto dto) {
		UUID id = UuidV5.forName("client", String.valueOf(dto.getId()));
		ClientStatus status = (dto.getIsActive() != null && dto.getIsActive()) ? ClientStatus.Active
				: ClientStatus.Inactive;

		Client client = new Client();
		client.setClientId(id);
		client.setClientCode(dto.getCustomerCode());
		client.setClientName(dto.getClientName() != null ? dto.getClientName() : dto.getCostCenterName());
		client.setRegion(dto.getRegionName());
		client.setIndustry(null);
		client.setStatus(status);

		return client;
	}

	// --- Engagements ---
	public static Engagement toEngagement(ProjectDto dto, Client client) {
		UUID id = UuidV5.forName("engagement", String.valueOf(dto.getId()));
		LocalDate start = DateUtils.toLocalDate(dto.getStartDate());
		LocalDate end = DateUtils.toLocalDate(dto.getEndDate());

		EngagementStatus st = (end == null || !end.isBefore(LocalDate.now())) ? EngagementStatus.Active
				: EngagementStatus.Closed;

		Engagement eng = new Engagement();
		eng.setEngagementId(id);
		eng.setClient(client);
		eng.setEngagementCode(dto.getProjectCode());
		eng.setEngagementName(dto.getProjectName());
		eng.setStartDate(start);
		eng.setEndDatePlanned(end);
		eng.setEngagementStatus(st);
		eng.setBillingCurrency(dto.getCurrencyCode());

		return eng;
	}

	// --- Role master ---
	public static RoleMaster toRole(RoleRefDto dto) {
//        UUID id = UuidV5.forName("role", String.valueOf(dto.getId()));

		RoleMaster role = new RoleMaster();
		role.setRoleId(UUID.randomUUID());
		role.setRoleName(dto.getRoleName());
		System.out.println("DTO roleName:" + dto.getRoleName());
//        log.info("DTO roleName: {}", dto.getRoleName());
		role.setRoleCategory(null);
		role.setActiveFlag(dto.getIsActive());

		return role;
	}

	// --- Rate card ---
	public static RateCard toRate(ProjectRateDto dto, Engagement engagement, RoleMaster role) {
		UUID id = UuidV5.forName("rate", String.valueOf(dto.getId()));
		BigDecimal amount = dto.getOnshoreRate() != null ? dto.getOnshoreRate() : dto.getOffshoreRate();

		RateCard rc = new RateCard();
		rc.setRateId(id);
		rc.setEngagement(engagement);
		rc.setRole(role);
		rc.setRateAmount(amount);
		rc.setRateType(null);
		rc.setEffectiveFrom(null);
		rc.setEffectiveTo(null);
		rc.setReferenceOnly(Boolean.TRUE);

		return rc;
	}

	// --- Resource (employee) ---
	public static ResourceEntity toResource(EmployeeDto dto) {
		UUID id = UuidV5.forName("resource", String.valueOf(dto.getId()));

		String fullName = String.join(" ", dto.getFirstName() == null ? "" : dto.getFirstName(),
				dto.getMiddleName() == null ? "" : dto.getMiddleName(),
				dto.getLastName() == null ? "" : dto.getLastName()).trim().replaceAll("\\s+", " ");

		EmploymentType et = null;
		if (dto.getRateType() != null) {
			String rt = dto.getRateType().toLowerCase(Locale.ROOT);
			if (rt.contains("fte") || rt.contains("perm")) {
				et = EmploymentType.FTE;
			} else if (rt.contains("contract")) {
				et = EmploymentType.Contract;
			}
		}

		LocalDate joining = DateUtils.toLocalDate(dto.getDateOfJoining());
		LocalDate exit = DateUtils.toLocalDate(dto.getDateOfResigning());

		EmploymentStatus es = (dto.getIsActive() != null && dto.getIsActive()) ? EmploymentStatus.Active
				: EmploymentStatus.Exited;

		ResourceEntity res = new ResourceEntity();
		res.setResourceId(id);
		res.setExternalEmployeeId(dto.getEmployeeCode());
		res.setFullName(fullName.isBlank() ? null : fullName);
		res.setEmail(dto.getEmailId());
		res.setEmploymentType(et);
		res.setBaseLocation(null);
		res.setGradeLevel(null);
		res.setPrimarySkill(null);
		res.setJoiningDate(joining);
		res.setExitDate(exit);
		res.setEmploymentStatus(es);
		res.setSourceSystem("PLT");
		res.setBillable(dto.getIsBillable());
		res.setActive(dto.getIsActive());
		res.setProjectId(dto.getProjectId());
		res.setEmployeeStatusId(dto.getEmployeeStatusId());

		return res;
	}

	// --- EngagementResource ---
	public static EngagementResource toEngagementResource(ResourceAssignmentDto dto, Engagement engagement,
			ResourceEntity resource, RoleMaster role) {

		UUID id = UuidV5.forName("engagement_resource", String.valueOf(dto.getId()));
		LocalDate start = DateUtils.toLocalDate(dto.getStartDate());
		LocalDate end = DateUtils.toLocalDate(dto.getEndDate());

		BillingStatus bs = null;
		if (dto.getBillingStatus() != null) {
			String v = dto.getBillingStatus().toLowerCase(Locale.ROOT).replace('-', '_');
			if (v.contains("billable"))
				bs = BillingStatus.Billable;
			else if (v.contains("non"))
				bs = BillingStatus.Non_Billable;
			else if (v.contains("bench"))
				bs = BillingStatus.Bench;
		}

		EngagementResource er = new EngagementResource();
		er.setEngagementResourceId(id);
		er.setEngagement(engagement);
		er.setResource(resource);
		er.setRole(role);
		er.setAllocationPercentage(dto.getAllocation());
		er.setAllocationStartDate(start);
		er.setAllocationEndDate(end);
		er.setBillingStatus(bs);
		er.setBillingStartDate(null);
		er.setBillingEndDate(null);
		er.setClientManager(dto.getClientManager());
		er.setInternalManager(null);
		er.setActiveFlag(dto.getIsActive());

		return er;
	}

	// --- Billing snapshot ---
	public static BillingSnapshot toBillingSnapshot(BillingSnapshotDto dto, EngagementResource er) {

		BillingSnapshot bs = new BillingSnapshot();
		bs.setEngagementResource(er);
		bs.setExpectedHours(dto.getExpectedHours());
		bs.setActualHours(dto.getActualHours());
		bs.setBillingReadyFlag(dto.getBillingReadyFlag());
		bs.setUnbilledReason(dto.getUnbilledReason());
		bs.setSnapshotDate(DateUtils.toLocalDate(dto.getSnapshotDate()));

		return bs;
	}

	// --- Onboarding status ---
	public static OnboardingStatus toOnboarding(OnboardingDto dto, ResourceEntity resource) {

		UUID id = UuidV5.forName("onboarding", dto.getEmployeeCode() + ":" + dto.getLastModifiedDate());

		OnboardingStatus entity = new OnboardingStatus();
		entity.setOnboardingStatusId(id);
		entity.setResource(resource);

// --- BGV STATUS ---
		if (dto.getBgvStatus() != null) {
			entity.setBgvStatus(dto.getBgvStatus());
		}

// --- CLIENT ACCESS ---
		if (dto.getClientAccess() != null && !dto.getClientAccess().isBlank()) {
			entity.setClientAccessStatus(dto.getClientAccess());
		} else {
			entity.setClientAccessStatus(dto.getClientAccess());
		}

// --- COMMENTS ---
		entity.setComments(dto.getComments());

// --- LAST MODIFIED ---
		if (dto.getLastModifiedDate() != null) {
			entity.setSourceLastModified(dto.getLastModifiedDate());
		}

		entity.setSourceSystem("PRODUCTIVV");

		return entity;
	}

	// --- Hiring status ---
	public static HiringStatus toHiring(HiringDto dto, ResourceEntity res, Engagement eng) {

		HiringStage hs = null;
		if (dto.getHiringStage() != null) {
			String s = dto.getHiringStage().toLowerCase(Locale.ROOT);
			if (s.contains("source"))
				hs = HiringStage.Sourced;
			else if (s.contains("interview"))
				hs = HiringStage.Interviewing;
			else if (s.contains("offer"))
				hs = HiringStage.Offered;
			else if (s.contains("join"))
				hs = HiringStage.Joined;
			else if (s.contains("drop"))
				hs = HiringStage.Dropped;
		}

		HiringStatus hsEntity = new HiringStatus();
		hsEntity.setResource(res);
		hsEntity.setEngagement(eng);
		hsEntity.setHiringStage(hs);
		hsEntity.setOfferApproved(dto.getOfferApproved());
		hsEntity.setExpectedJoiningDate(DateUtils.toLocalDate(dto.getExpectedJoiningDate()));
		hsEntity.setActualJoiningDate(DateUtils.toLocalDate(dto.getActualJoiningDate()));
		hsEntity.setLastUpdatedBy(dto.getLastUpdatedBy());
		hsEntity.setLastUpdatedAt(dto.getLastUpdatedAt());

		return hsEntity;
	}

	// --- Timesheet snapshot ---
	public static TimesheetSnapshot toTimesheetSnapshot(TimesheetDto dto, UUID resId, UUID engId) {

		String period = DateUtils.toYearMonth(dto.getWorkDate());

		TimesheetSnapshotId tsId = new TimesheetSnapshotId();
		tsId.setResourceId(resId);
		tsId.setEngagementId(engId);
		tsId.setPeriod(period);

		boolean submitted = dto.getSubmittedAt() != null
				|| (dto.getStatus() != null && dto.getStatus().toLowerCase(Locale.ROOT).contains("submit"));

		boolean approved = dto.getApprovedAt() != null
				|| (dto.getStatus() != null && dto.getStatus().toLowerCase(Locale.ROOT).contains("approve"));

		TimesheetSnapshot ts = new TimesheetSnapshot();
		ts.setId(tsId);
		ts.setSubmittedFlag(submitted);
		ts.setApprovedFlag(approved);
		ts.setApprovalDate(DateUtils.toLocalDate(dto.getApprovedAt()));
		ts.setSourceSystem("PLT");

		return ts;
	}
}
