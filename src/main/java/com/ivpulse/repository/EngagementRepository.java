package com.ivpulse.repository;

import com.ivpulse.entity.Engagement;
import com.ivpulse.entity.enums.EngagementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.UUID;

@Repository
public interface EngagementRepository extends JpaRepository<Engagement, UUID> {
    Optional<Engagement> findByEngagementCode(String engagementCode);
    List<Engagement> findByClient_ClientId(UUID clientId);
    List<Engagement> findByEngagementStatus(EngagementStatus status);
}
