package com.ivpulse.dto.dashboard;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UnbilledRowDto {
    private String employeeCode;
    private String resourceName;
    private String email;
    private String engagementName;
    private String roleName;
    private BigDecimal expectedHours;
    private BigDecimal actualHours;
    private boolean billingReady;
    private String unbilledReason;
    private LocalDate snapshotDate;
}
