package com.ivpulse.etl.support;

import java.time.LocalDate;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ivpulse.etl.staging.StagingEntity;
import com.ivpulse.etl.staging.StagingRecord;
import com.ivpulse.etl.staging.StagingStatus;
import com.ivpulse.repository.StagingRecordRepository;
import com.ivpulse.util.HashUtils;
import com.ivpulse.util.JsonUtils;

@Service
public class StagingWriterService {

    private final StagingRecordRepository repo;

    public StagingWriterService(StagingRecordRepository repo) {
        this.repo = repo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public StagingRecord write(StagingEntity entity, String sourceId, String rawJson, LocalDate partitionDate) {
        String canonical = JsonUtils.canonicalize(rawJson);
        String hash = HashUtils.sha256(canonical);

        return repo.findByEntityNameAndSourceIdAndPayloadHash(entity, sourceId, hash).orElseGet(() -> {
            try {
                StagingRecord rec = new StagingRecord();
                rec.setEntityName(entity);
                rec.setSourceId(sourceId);
                rec.setPayload(canonical);
                rec.setPayloadHash(hash);
                rec.setStatus(StagingStatus.NEW);
                rec.setPartitionDate(partitionDate == null ? LocalDate.now() : partitionDate);
                return repo.save(rec);
            } catch (DataIntegrityViolationException e) {
                // unique constraint race — fetch existing
                return repo.findByEntityNameAndSourceIdAndPayloadHash(entity, sourceId, hash)
                           .orElseThrow(() -> e);
            }
        });
    }
}
