package com.ivpulse.repository;

import com.ivpulse.entity.OnboardingStatus;
import com.ivpulse.entity.enums.BgvStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.UUID;

@Repository
public interface OnboardingStatusRepository extends JpaRepository<OnboardingStatus, Long> {
    List<OnboardingStatus> findByResource_ResourceId(UUID resourceId);
    List<OnboardingStatus> findByBgvStatus(BgvStatus status);
    Optional<OnboardingStatus> findTopByResource_ResourceIdOrderByLastUpdatedAtDesc(UUID resourceId);
}
