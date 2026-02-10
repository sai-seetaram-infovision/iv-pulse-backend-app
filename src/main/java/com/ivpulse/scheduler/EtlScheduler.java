package com.ivpulse.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ivpulse.etl.delta.DeltaSyncService;
import com.ivpulse.etl.processor.StagingProcessingService;

@Component
public class EtlScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(EtlScheduler.class);

    private final DeltaSyncService deltaSyncService;
    private final StagingProcessingService stagingProcessingService;

    public EtlScheduler(
            DeltaSyncService deltaSyncService,
            StagingProcessingService stagingProcessingService) {

        this.deltaSyncService = deltaSyncService;
        this.stagingProcessingService = stagingProcessingService;
    }

    // Daily ETL pipeline – runs at 2:00 AM
    @Scheduled(cron = "0 0 2 * * *")
    public void runDailyPipeline() {
        log.info("[Scheduler] Starting daily ETL pipeline...");

        try {
            deltaSyncService.runDeltaSync(true, true, true);
            log.info("[Scheduler] Delta Sync complete");

            stagingProcessingService.processAllEntities(500, 20);
            log.info("[Scheduler] Staging processing complete");

        } catch (Exception ex) {
            log.error("[Scheduler] Error during ETL pipeline", ex);
        }
    }
}
