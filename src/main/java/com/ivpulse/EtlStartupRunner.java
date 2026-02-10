//package com.ivpulse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import com.ivpulse.etl.delta.DeltaSyncService;
//import com.ivpulse.etl.processor.StagingProcessingService;
//
//@Component
//public class EtlStartupRunner implements ApplicationRunner {
//
//    private static final Logger log =
//            LoggerFactory.getLogger(EtlStartupRunner.class);
//
//    private final DeltaSyncService deltaSyncService;
//    private final StagingProcessingService stagingProcessingService;
//
//    @Value("${etl.run-on-startup:false}")
//    private boolean runOnStartup;
//
//    @Value("${etl.startup.page-size:500}")
//    private int pageSize;
//
//    @Value("${etl.startup.max-pages:20}")
//    private int maxPages;
//
//    public EtlStartupRunner(
//            DeltaSyncService deltaSyncService,
//            StagingProcessingService stagingProcessingService) {
//
//        this.deltaSyncService = deltaSyncService;
//        this.stagingProcessingService = stagingProcessingService;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) {
//
//        if (!runOnStartup) {
//            log.info("[Startup ETL] Skipped (etl.run-on-startup=false)");
//            return;
//        }
//
//        log.info("[Startup ETL] Starting initial ETL run");
//
//        try {
//            // Phase 1: Source → Staging
//            deltaSyncService.runDeltaSync(true, true, true);
//            log.info("[Startup ETL] Delta sync completed");
//
//            // Phase 2: Staging → Core
//            var summary =
//                    stagingProcessingService.processAllEntities(pageSize, maxPages);
//
//            log.info("[Startup ETL] Staging processing completed. " +
//                     "Processed={}, Failed={}, Time={}ms",
//                     summary.getProcessed(),
//                     summary.getFailed(),
//                     summary.getMillis());
//
//        } catch (Exception ex) {
//            log.error("[Startup ETL] Failed during startup ETL", ex);
//        }
//    }
//}
