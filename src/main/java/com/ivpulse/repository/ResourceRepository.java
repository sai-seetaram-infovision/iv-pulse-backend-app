package com.ivpulse.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ivpulse.entity.ResourceEntity;
import com.ivpulse.entity.enums.EmploymentStatus;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, UUID> {
    Optional<ResourceEntity> findByEmail(String email);
    Optional<ResourceEntity> findByExternalEmployeeId(String externalEmployeeId);
    List<ResourceEntity> findByEmploymentStatus(EmploymentStatus status);
    
    long countByIsBillableTrue();
    long countByIsBillableFalse();

    @Query(value = "select count(*) from resource where project_id = ?1", nativeQuery = true)
    long countBenchByProjectId(Long projectId);

    @Query(value = "select count(*) from resource where employee_status_id = 9 and is_active = false", nativeQuery = true)
    long countOnNotice();

    @Query(value = "select count(*) from resource where enabling_unit_id is not null", nativeQuery = true)
    long countEnablingUnit();
}
