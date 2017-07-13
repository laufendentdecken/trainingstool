package at.trainingstool.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarUtils {

  public static List<String> getWeekDays() {
    Calendar cal = Calendar.getInstance();

    List<String> weekdays = new ArrayList<>();
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    weekdays.add(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));

    cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
    weekdays.add(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));

    cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
    weekdays.add(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));

    cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
    weekdays.add(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));

    cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
    weekdays.add(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));

    cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
    weekdays.add(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));

    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    weekdays.add(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));

    return weekdays;
  }

}
