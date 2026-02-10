package com.ivpulse.etl.support;

import com.ivpulse.etl.job.EtlJobRun;
import com.ivpulse.etl.job.EtlJobStatus;
import com.ivpulse.etl.job.EtlJobType;
import com.ivpulse.repository.EtlJobRunRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class JobRunService {

	private final EtlJobRunRepository repo;

	public JobRunService(EtlJobRunRepository repo) {
		this.repo = repo;
	}

	@Transactional
	public EtlJobRun start(EtlJobType type, String message) {

		EtlJobRun run = new EtlJobRun();
		run.setJobType(type);
		run.setStatus(EtlJobStatus.RUNNING);
		run.setStartedAt(OffsetDateTime.now());
		run.setMessage(message);
		run.setProcessedCount(0);
		run.setErrorCount(0);

		return repo.save(run);
	}

	@Transactional
	public EtlJobRun success(EtlJobRun run, int processedCount, int errorCount, String message) {
		run.setStatus(EtlJobStatus.SUCCESS);
		run.setFinishedAt(OffsetDateTime.now());
		run.setProcessedCount(processedCount);
		run.setErrorCount(errorCount);
		run.setMessage(message);
		return repo.save(run);
	}

	@Transactional
	public EtlJobRun fail(EtlJobRun run, String message, int errorCount) {
		run.setStatus(EtlJobStatus.FAILED);
		run.setFinishedAt(OffsetDateTime.now());
		run.setErrorCount(errorCount);
		run.setMessage(message);
		return repo.save(run);
	}
}
