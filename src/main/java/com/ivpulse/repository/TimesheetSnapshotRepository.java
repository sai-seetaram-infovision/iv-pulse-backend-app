package com.ivpulse.repository;

import com.ivpulse.entity.TimesheetSnapshot;
import com.ivpulse.entity.TimesheetSnapshotId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TimesheetSnapshotRepository extends JpaRepository<TimesheetSnapshot, TimesheetSnapshotId> {
    List<TimesheetSnapshot> findById_Period(String period);
}
