package com.ivpulse.repository;

import com.ivpulse.entity.EngagementResource;
import com.ivpulse.entity.enums.BillingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.UUID;

@Repository
public interface EngagementResourceRepository extends JpaRepository<EngagementResource, UUID> {
    List<EngagementResource> findByEngagement_EngagementId(UUID engagementId);
    List<EngagementResource> findByResource_ResourceId(UUID resourceId);
    List<EngagementResource> findByBillingStatus(BillingStatus status);

    @Query("""
        select er from EngagementResource er
        where er.resource.resourceId = :resourceId
          and (er.allocationStartDate is null or er.allocationStartDate <= :asOn)
          and (er.allocationEndDate   is null or er.allocationEndDate   >= :asOn)
    """)
    List<EngagementResource> findActiveAllocationsForResource(@Param("resourceId") UUID resourceId,
                                                              @Param("asOn") LocalDate asOn);
}
