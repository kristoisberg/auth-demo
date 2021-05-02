package xyz.kristo.projectx.auth.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateConverter {
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    private DateConverter() {
        throw new IllegalAccessError("Utility class can not be instantiated");
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZONE_ID).toInstant());
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZONE_ID).toLocalDateTime();
    }
}
