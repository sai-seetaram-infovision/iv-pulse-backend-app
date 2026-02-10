
package com.ivpulse.kpi.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KpiCacheConfigExtension {
    @Bean
    public CacheManager kpiBreakdownCacheManager() {
        // If a CacheManager already exists (Phase 13), Spring will prefer a primary bean.
        // This bean ensures the new cache name exists if using a shared manager.
        return new ConcurrentMapCacheManager("kpiUnbilledBreakdown");
    }
}
