package xyz.kristo.projectx.auth.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateConverter {
    private static final ZoneId zoneId = ZoneId.systemDefault();

    private DateConverter() {}

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(zoneId).toLocalDateTime();
    }
}
