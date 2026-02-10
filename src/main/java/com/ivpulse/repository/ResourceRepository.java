package com.ivpulse.repository;

import com.ivpulse.entity.ResourceEntity;
import com.ivpulse.entity.enums.EmploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.UUID;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, UUID> {
    Optional<ResourceEntity> findByEmail(String email);
    Optional<ResourceEntity> findByExternalEmployeeId(String externalEmployeeId);
    List<ResourceEntity> findByEmploymentStatus(EmploymentStatus status);
}
