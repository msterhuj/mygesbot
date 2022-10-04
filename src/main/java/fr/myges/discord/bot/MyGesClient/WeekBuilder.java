package fr.myges.discord.bot.MyGesClient;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

public class WeekBuilder {

    public static ArrayList<Long> getWeekRange() {
        return getWeekRange(0);
    }

    public static ArrayList<Long> getWeekRange(int weekOffset) {
        ArrayList<Long> dates = new ArrayList<>();

        // get actual week range
        LocalDate now = LocalDate.now();

        if (weekOffset != 0) {
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

}
