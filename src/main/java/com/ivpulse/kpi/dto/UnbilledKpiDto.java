
package com.ivpulse.kpi.dto;

import java.time.YearMonth;

public record UnbilledKpiDto(YearMonth month, double unbilledHours, double blendedRate, double unbilledAmount) {
}
