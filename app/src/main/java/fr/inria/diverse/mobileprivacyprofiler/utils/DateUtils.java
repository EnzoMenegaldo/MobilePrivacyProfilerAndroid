package fr.inria.diverse.mobileprivacyprofiler.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dvojtise on 29/01/18.
 */

public class DateUtils {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.US);

    public static String printDate(long millis) {
        return dateFormat.format(new Date(millis));
    }

    /**
     * parse the string as a date using our simple date format yyyy-MM-dd HH:mm:ss.SSSZ
     * If not working returns 0...
     * @param dateString
     * @return
     */
    public static long parseDateString(String dateString) {
        try {
            return dateFormat.parse(dateString).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static Calendar firstDayOfLastMonth(Calendar c)
    {
        c = (Calendar) c.clone();
        // last month
        c.add(Calendar.MONTH, -1);
        // first day
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    public static Calendar lastDayOfLastMonth(Calendar c)
    {
        c = (Calendar) c.clone();
        // first day of this month
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        // last day/milllis of previous month
        c.add(Calendar.MILLISECOND, -1);
        return c;
    }

    public static Calendar firstDayOfLastWeek(Calendar c)
    {
        c = (Calendar) c.clone();
        // last week
        c.add(Calendar.WEEK_OF_YEAR, -1);
        // first day
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    public static Calendar lastDayOfLastWeek(Calendar c)
    {
        c = (Calendar) c.clone();
        // first day of this week
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        // last day/milllis of previous week
        c.add(Calendar.MILLISECOND, -1);
        return c;
    }

    public static Calendar yesterdayStart(Calendar c)
    {
        c = (Calendar) c.clone();
        // yesterday
        c.add(Calendar.DAY_OF_WEEK, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    public static Calendar yesterdayEnd(Calendar c)
    {
        c = (Calendar) c.clone();
        // today
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        // last milllis of yesterday
        c.add(Calendar.MILLISECOND, -1);
        return c;
    }

}
