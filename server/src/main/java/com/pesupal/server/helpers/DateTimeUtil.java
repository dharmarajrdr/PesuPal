package com.pesupal.server.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    /**
     * Formats a LocalDateTime to a short string representation.
     *
     * @param timestamp
     * @return
     */
    public static String formatShort(LocalDateTime timestamp) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = timestamp.toLocalDate();
        LocalDate today = now.toLocalDate();

        if (date.isEqual(today)) {
            // If today: return time like "10:20 AM"
            return timestamp.format(DateTimeFormatter.ofPattern("hh:mm a"));
        } else if (date.isAfter(today.minusDays(7))) {
            // If within the past 7 days: return day of week like "Tue"
            return timestamp.format(DateTimeFormatter.ofPattern("EEE"));
        } else {
            // Else: return date like "20/02/2025"
            return timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
    }

    /**
     * Converts a LocalDateTime to epoch milliseconds.
     *
     * @param timestamp
     * @return
     */
    public static Long toEpochMilli(LocalDateTime timestamp) {

        return timestamp.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
