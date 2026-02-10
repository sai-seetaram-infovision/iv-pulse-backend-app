package com.ivpulse.etl.initial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivpulse.client.ProductivvClient;
import com.ivpulse.dto.common.InitialLoadResult;
import com.ivpulse.dto.productivv.*;
import com.ivpulse.etl.job.EtlJobRun;
import com.ivpulse.etl.job.EtlJobType;
import com.ivpulse.etl.staging.StagingEntity;
import com.ivpulse.etl.support.JobRunService;
import com.ivpulse.etl.support.StagingWriterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class InitialLoadService {
	private static final Logger log = LoggerFactory.getLogger(InitialLoadService.class);

	private final ProductivvClient client;
	private final StagingWriterService stagingWriter;
	private final JobRunService jobRunService;
	private final ObjectMapper mapper;

	

	public InitialLoadService(ProductivvClient client, StagingWriterService stagingWriter, JobRunService jobRunService,
			ObjectMapper mapper) {
		super();
		this.client = client;
		this.stagingWriter = stagingWriter;
		this.jobRunService = jobRunService;
		this.mapper = mapper;
	}

	/** Executes the one-time initial dump to staging (no processing). */
	@Transactional
	public InitialLoadResult runInitialLoad() {
		long start = System.currentTimeMillis();
		EtlJobRun run = jobRunService.start(EtlJobType.INITIAL_LOAD, "Initial load to staging");

		int clients = 0, engagements = 0, roles = 0, rates = 0, resources = 0, ers = 0, hiring = 0, onboarding = 0,
				billing = 0, errors = 0;
		LocalDate partition = LocalDate.now();

		try {
			// 1) Clients (costcenters)
			List<CostCenterDto> costcenters = client.getCostCenters();
			for (CostCenterDto dto : costcenters) {
				try {
					write(StagingEntity.CLIENT, "client:" + dto.getId(), dto, partition);
					clients++;
				} catch (Exception e) {
					errors++;
					log.error("Client staging error", e);
				}
			}

			// 2) Projects (engagements)
			List<ProjectDto> projects = client.getProjects();
			for (ProjectDto dto : projects) {
				try {
					write(StagingEntity.ENGAGEMENT, "engagement:" + dto.getId(), dto, partition);
					engagements++;
				} catch (Exception e) {
					errors++;
					log.error("Engagement staging error", e);
				}
			}

			// 3) Roles reference
			List<RoleRefDto> rolesRef = client.getRolesReference();
//			for (RoleRefDto dto : rolesRef) {
//				try {
//					write(StagingEntity.ROLE_MASTER, "role:" + dto.getId(), dto, partition);
//					roles++;
//				} catch (Exception e) {
//					errors++;
//					log.error("Role staging error", e);
//				}
//			}

			// 4) Project rate
			List<ProjectRateDto> ratesRef = client.getProjectRates();
			for (ProjectRateDto dto : ratesRef) {
				try {
					write(StagingEntity.RATE_CARD, "rate:" + dto.getId(), dto, partition);
					rates++;
				} catch (Exception e) {
					errors++;
					log.error("Rate staging error", e);
				}
			}

			// 5) Employees (resources)
			List<EmployeeDto> employees = client.getEmployees();
			for (EmployeeDto dto : employees) {
				try {
					write(StagingEntity.RESOURCE, "resource:" + dto.getId(), dto, partition);
					resources++;
				} catch (Exception e) {
					errors++;
					log.error("Resource staging error", e);
				}
			}

			// 6) Resource assignments (engagement_resource)
			List<ResourceAssignmentDto> assigns = client.getResources();
			for (ResourceAssignmentDto dto : assigns) {
				try {
					write(StagingEntity.ENGAGEMENT_RESOURCE, "engagement_resource:" + dto.getId(), dto, partition);
					ers++;
				} catch (Exception e) {
					errors++;
					log.error("EngagementResource staging error", e);
				}
			}

			// 7) Hiring
			List<HiringDto> hiringList = client.getHiring();
			for (HiringDto dto : hiringList) {
				try {
					write(StagingEntity.HIRING_STATUS, "hiring:" + dto.getId(), dto, partition);
					hiring++;
				} catch (Exception e) {
					errors++;
					log.error("Hiring staging error", e);
				}
			}

			// 8) Onboarding
			List<OnboardingDto> onboardingList = client.getOnboarding();
			for (OnboardingDto dto : onboardingList) {
				try {
					write(StagingEntity.ONBOARDING_STATUS, "onboarding:" + dto.getId(), dto, partition);
					onboarding++;
				} catch (Exception e) {
					errors++;
					log.error("Onboarding staging error", e);
				}
			}

			// 9) Billing snapshot
			List<BillingSnapshotDto> billingList = client.getBillingSnapshots();
//			for (BillingSnapshotDto dto : billingList) {
//				String sourceId = "billing:" + dto.getEngagementResourceId();
//				try {
//					write(StagingEntity.BILLING_SNAPSHOT, sourceId, dto, partition);
//					billing++;
//				} catch (Exception e) {
//					errors++;
//					log.error("Billing staging error", e);
//				}
//			}

			int total = clients + engagements + roles + rates + resources + ers + hiring + onboarding + billing;
			long millis = System.currentTimeMillis() - start;
			jobRunService.success(run, total, errors, "Initial load completed");

			InitialLoadResult result = new InitialLoadResult();

			result.setClients(clients);
			result.setEngagements(engagements);
			result.setRoles(roles);
			result.setRates(rates);

			result.setResources(resources);
			result.setEngagementResources(ers);
			result.setHiring(hiring);
			result.setOnboarding(onboarding);

			result.setBillingSnapshots(billing);
			result.setTotalWritten(total);
			result.setErrors(errors);
			result.setMillis(millis);

			return result;

		} catch (Exception ex) {
			jobRunService.fail(run, ex.getMessage(), errors + 1);
			throw ex;
		}
	}

	private void write(StagingEntity entity, String sourceId, Object dto, LocalDate partition)
			throws JsonProcessingException {
		String json = mapper.writeValueAsString(dto);
		stagingWriter.write(entity, sourceId, json, partition);
	}
}
