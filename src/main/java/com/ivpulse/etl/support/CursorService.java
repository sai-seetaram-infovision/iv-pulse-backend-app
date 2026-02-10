package com.ivpulse.etl.support;

import com.ivpulse.etl.kv.SourceSyncState;
import com.ivpulse.etl.kv.SourceType;
import com.ivpulse.repository.SourceSyncStateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class CursorService {

    private final SourceSyncStateRepository repo;

    public CursorService(SourceSyncStateRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Optional<SourceSyncState> get(String key) {
        return repo.findById(key);
    }

    @Transactional
    public SourceSyncState upsert(
            String key,
            SourceType sourceType,
            LocalDate from,
            LocalDate to,
            String notes) {

        SourceSyncState state = repo.findById(key).orElseGet(() -> {
            SourceSyncState s = new SourceSyncState();
            s.setStateKey(key);
            s.setSourceType(sourceType);
            return s;
        });

        state.setLastFrom(from);
        state.setLastTo(to);
        state.setLastSyncedAt(OffsetDateTime.now());
        state.setNotes(notes);

        return repo.save(state);
    }

}
