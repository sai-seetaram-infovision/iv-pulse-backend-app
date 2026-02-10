package com.ivpulse.repository;

import com.ivpulse.entity.BillingSnapshot;
import com.ivpulse.entity.EngagementResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface BillingSnapshotRepository extends JpaRepository<BillingSnapshot, Long> {
    Optional<BillingSnapshot> findTopByEngagementResourceOrderBySnapshotDateDesc(EngagementResource er);
    List<BillingSnapshot> findByEngagementResourceAndSnapshotDateBetween(EngagementResource er, LocalDate from, LocalDate to);
}
