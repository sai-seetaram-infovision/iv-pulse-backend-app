package com.ivpulse.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtils {

    private DateUtils() {}

    // === Existing (example) ===
    public static LocalDate toLocalDate(OffsetDateTime odt) {
        return odt == null ? null : odt.toLocalDate();
    }

    // === New overload for LocalDateTime ===
    public static LocalDate toLocalDate(LocalDateTime ldt) {
        return ldt == null ? null : ldt.toLocalDate();
    }

    // Optional: overload for LocalDate (pass-through)
    public static LocalDate toLocalDate(LocalDate d) {
        return d;
    }

    // Optional: parse from String if some DTOs carry String dates
    public static LocalDate toLocalDate(String text) {
        if (text == null || text.isBlank()) return null;
        // Try common ISO patterns (with or without time)
        try {
            // yyyy-MM-dd
            return LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ignore) {}

        try {
            // yyyy-MM-ddTHH:mm:ss(.fraction)
            LocalDateTime ldt = LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return ldt.toLocalDate();
        } catch (DateTimeParseException ignore) {}

        try {
            // yyyy-MM-ddTHH:mm:ss(.fraction)Z / with offset
            OffsetDateTime odt = OffsetDateTime.parse(text, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return odt.toLocalDate();
        } catch (DateTimeParseException ignore) {}

        // You can add a custom formatter fallback if needed.
        throw new IllegalArgumentException("Unsupported date format: " + text);
    }

    // Convert various date-time values to yyyy-MM (for your timesheet period)
    public static String toYearMonth(OffsetDateTime odt) {
        if (odt == null) return null;
        return odt.getYear() + "-" + String.format("%02d", odt.getMonthValue());
    }

    public static String toYearMonth(LocalDateTime ldt) {
        if (ldt == null) return null;
        return ldt.getYear() + "-" + String.format("%02d", ldt.getMonthValue());
    }

    public static String toYearMonth(LocalDate d) {
        if (d == null) return null;
        return d.getYear() + "-" + String.format("%02d", d.getMonthValue());
    }

    public static String toYearMonth(String text) {
        LocalDate d = toLocalDate(text);
        return toYearMonth(d);
    }
}