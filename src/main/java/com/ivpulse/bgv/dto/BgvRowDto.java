
package com.ivpulse.bgv.dto;

import java.time.LocalDate;

public record BgvRowDto(
        String clientId,
        String clientName,
        String engagementId,
        String engagementName,
        String resourceId,
        String employeeCode,
        String employeeName,
        String roleId,
        String roleName,
        String location,
        String vendor,
        String status,
        LocalDate requestedOn,
        LocalDate verifiedOn,
        String remarks
) {}
