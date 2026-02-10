
package com.ivpulse.kpi.dto;

import java.time.YearMonth;

public record UnbilledRowDto(
        YearMonth month,
        String clientId,
        String clientName,
        String engagementId,
        String engagementName,
        String resourceId,
        String resourceName,
        String roleId,
        String roleName,
        String location,
        double hours,
        double rate,
        double amount
) {}
