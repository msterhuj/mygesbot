package fr.myges.discord.api;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

public class TimeBuilder {

    public static ArrayList<Long> getWeekRangeTimestamp(@Nullable Integer weekOffset) {
        ArrayList<Long> dates = new ArrayList<>();

        // get actual week range
        LocalDate now = LocalDate.now();

        if (weekOffset != null) {
            now = now.plusWeeks(weekOffset);
        }

        LocalDate monday = now.with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1L);
        LocalDate sunday = now.with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 7L);

        // to timestamp
        long mondayTimestamp = Timestamp.valueOf(monday.atStartOfDay()).getTime();
        long sundayTimestamp = Timestamp.valueOf(sunday.atStartOfDay()).getTime();

        dates.add(mondayTimestamp);
        dates.add(sundayTimestamp);

        return dates;
    }

    public static ArrayList<Long> getDateTimestampStartEnd(@Nullable Integer dayOffset) {
        ArrayList<Long> dates = new ArrayList<>();

        LocalDate date = LocalDate.now();

        if (dayOffset != null)
            date = date.plusDays(dayOffset);

        dates.add(Timestamp.valueOf(date.atStartOfDay()).getTime());
        dates.add(Timestamp.valueOf(date.plusDays(1).atStartOfDay()).getTime());

        return dates;
    }

}
