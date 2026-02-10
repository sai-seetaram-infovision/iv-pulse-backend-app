package com.ivpulse.repository;

import com.ivpulse.etl.job.EtlJobRun;
import com.ivpulse.etl.job.EtlJobStatus;
import com.ivpulse.etl.job.EtlJobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtlJobRunRepository extends JpaRepository<EtlJobRun, Long> {
    List<EtlJobRun> findTop10ByOrderByStartedAtDesc();
    List<EtlJobRun> findTop10ByJobTypeOrderByStartedAtDesc(EtlJobType jobType);
    List<EtlJobRun> findTop10ByStatusOrderByStartedAtDesc(EtlJobStatus status);
}
