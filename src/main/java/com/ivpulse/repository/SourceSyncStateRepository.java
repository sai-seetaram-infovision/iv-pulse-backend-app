package com.ivpulse.repository;

import com.ivpulse.etl.kv.SourceSyncState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceSyncStateRepository extends JpaRepository<SourceSyncState, String> {
}
