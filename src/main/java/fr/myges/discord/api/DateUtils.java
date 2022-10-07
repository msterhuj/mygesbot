package fr.myges.discord.api;

import java.sql.Date;
import java.util.Calendar;

public class DateUtils {
    public static int getDayNumberFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
