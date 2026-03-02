package com.ivpulse.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivpulse.entity.OnboardingStatus;
import com.ivpulse.entity.enums.BgvStatus;

@Repository
public interface OnboardingStatusRepository extends JpaRepository<OnboardingStatus, UUID> {
	List<OnboardingStatus> findByResource_ResourceId(UUID resourceId);

	List<OnboardingStatus> findByBgvStatus(BgvStatus status);

	Optional<OnboardingStatus> findTopByResource_ResourceIdOrderBySourceLastModifiedDesc(UUID resourceId);
}
