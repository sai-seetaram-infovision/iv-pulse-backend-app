package com.ivpulse.dto.common;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateRangeDto {
	private LocalDate from;
	private LocalDate to;
}
