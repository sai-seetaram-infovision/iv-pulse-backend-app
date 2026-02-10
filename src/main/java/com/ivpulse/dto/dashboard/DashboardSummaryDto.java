package com.ivpulse.dto.dashboard;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardSummaryDto {
    private LocalDate asOnDate;
    private int totalHeadcount;
    private int totalBillable;
    private int totalNonBillable;
    private int totalBench;
    private int totalEnabling;
    private int totalOnNotice;
    private int totalMaternity;
}
