package com.ivpulse.util;

import java.time.LocalDate;
import java.time.YearMonth;

public final class DateWindowUtils {
    private DateWindowUtils() {}

    public static LocalDate firstDayOfCurrentMonth() {
        var ym = YearMonth.now();
        return ym.atDay(1);
    }

    public static LocalDate lastDayOfCurrentMonth() {
        var ym = YearMonth.now();
        return ym.atEndOfMonth();
    }

    public static LocalDate firstDayOfPreviousMonth() {
        var ym = YearMonth.now().minusMonths(1);
        return ym.atDay(1);
    }

    public static LocalDate lastDayOfPreviousMonth() {
        var ym = YearMonth.now().minusMonths(1);
        return ym.atEndOfMonth();
    }
}
