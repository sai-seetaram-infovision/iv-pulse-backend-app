package com.ivpulse.repository;

import com.ivpulse.entity.HiringStatus;
import com.ivpulse.entity.enums.HiringStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.UUID;

@Repository
public interface HiringStatusRepository extends JpaRepository<HiringStatus, Long> {
    List<HiringStatus> findByResource_ResourceId(UUID resourceId);
    List<HiringStatus> findByEngagement_EngagementId(UUID engagementId);
    List<HiringStatus> findByHiringStage(HiringStage stage);
}
