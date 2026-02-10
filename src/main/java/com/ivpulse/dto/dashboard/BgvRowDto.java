package com.ivpulse.dto.dashboard;

import lombok.*;
import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BgvRowDto {
    private String employeeCode;
    private String resourceName;
    private String email;
    private String engagementName;
    private String bgvStatus;
    private OffsetDateTime lastUpdatedAt;
    private long daysInStatus;
}
