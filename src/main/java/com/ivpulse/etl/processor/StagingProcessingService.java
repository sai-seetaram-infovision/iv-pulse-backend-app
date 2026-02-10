package com.ivpulse.etl.processor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivpulse.dto.common.ProcessingSummaryDto;
import com.ivpulse.etl.staging.StagingEntity;

@Service
public class StagingProcessingService {

	private final StagingBatchProcessor processor;

	public StagingProcessingService(StagingBatchProcessor processor) {
		this.processor = processor;
	}

	/** Processes all entities in dependency order */
	public ProcessingSummaryDto processAll(int pageSize, int maxPagesPerEntity) {
		long start = System.currentTimeMillis();
		int processed = 0;
		int failed = 0; // failed is captured on records; we return 0 here as a coarse summary

		processed += processor.process(StagingEntity.CLIENT, pageSize, maxPagesPerEntity);
		processed += processor.process(StagingEntity.ENGAGEMENT, pageSize, maxPagesPerEntity);
		processed += processor.process(StagingEntity.ROLE_MASTER, pageSize, maxPagesPerEntity);
		processed += processor.process(StagingEntity.RATE_CARD, pageSize, maxPagesPerEntity);
		processed += processor.process(StagingEntity.RESOURCE, pageSize, maxPagesPerEntity);
		processed += processor.process(StagingEntity.ENGAGEMENT_RESOURCE, pageSize, maxPagesPerEntity);
		processed += processor.process(StagingEntity.HIRING_STATUS, pageSize, maxPagesPerEntity);
		processed += processor.process(StagingEntity.ONBOARDING_STATUS, pageSize, maxPagesPerEntity);
		processed += processor.process(StagingEntity.BILLING_SNAPSHOT, pageSize, maxPagesPerEntity);
		processed += processor.process(StagingEntity.TIMESHEET_SNAPSHOT, pageSize, maxPagesPerEntity);

		long millis = System.currentTimeMillis() - start;
		return new ProcessingSummaryDto(processed, failed, millis);
	}

	@Transactional
	public ProcessingSummaryDto processAllEntities(int pageSize, int maxPagesPerEntity) {
		return processAll(pageSize, maxPagesPerEntity);
	}

}
