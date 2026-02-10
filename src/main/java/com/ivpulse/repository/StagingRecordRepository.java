package com.ivpulse.repository;

import com.ivpulse.etl.staging.StagingEntity;
import com.ivpulse.etl.staging.StagingRecord;
import com.ivpulse.etl.staging.StagingStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StagingRecordRepository extends JpaRepository<StagingRecord, Long> {

	List<StagingRecord> findByEntityNameAndStatusOrderBySyncedAtAsc(StagingEntity entityName, StagingStatus status,
			Pageable page);

	@Query("""
			    select s from StagingRecord s
			     where s.entityName = :entity
			       and s.status in (:statuses)
			       and (s.partitionDate is null or s.partitionDate >= :keepFrom)
			     order by s.syncedAt asc
			""")
	List<StagingRecord> findForProcessing(@Param("entity") StagingEntity entity,
			@Param("statuses") List<StagingStatus> statuses, @Param("keepFrom") LocalDate keepFrom, Pageable page);

	Optional<StagingRecord> findByEntityNameAndSourceIdAndPayloadHash(StagingEntity entityName, String sourceId,
			String payloadHash);

	long deleteByPartitionDateBefore(LocalDate cutoffDate);
}
