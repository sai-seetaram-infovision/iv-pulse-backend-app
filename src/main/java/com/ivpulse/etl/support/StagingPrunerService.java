package com.ivpulse.etl.support;

import com.ivpulse.repository.StagingRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class StagingPrunerService {
    private static final Logger log = LoggerFactory.getLogger(StagingPrunerService.class);

    private final StagingRecordRepository repo;

    public StagingPrunerService(StagingRecordRepository repo) {
        this.repo = repo;
    }

    /**
     * Deletes staging rows older than cutoff date (by partition_date).
     * Returns number of rows deleted.
     */
    @Transactional
    public long pruneBefore(LocalDate cutoff) {
        long deleted = repo.deleteByPartitionDateBefore(cutoff);
        log.info("Pruned {} staging rows older than {}", deleted, cutoff);
        return deleted;
    }
}
