package at.trainingstool.tools.period.monthly;

import java.util.Calendar;
import java.util.Locale;

import at.trainingstool.model.User;
import at.trainingstool.tools.period.AbstractPeriod;

public class MonthlyPeriod extends AbstractPeriod {

  public MonthlyPeriod(User user, String periodParam) {
    this.user = user;
    day = 1;
    if (periodParam == null) {
      Calendar cal = Calendar.getInstance();
      month = cal.get(Calendar.MONTH);
      year = cal.get(Calendar.YEAR);
      periodParam = month + "." + year;
    } else {
      month = Integer.parseInt(periodParam.split("\\.")[0]);
      year = Integer.parseInt(periodParam.split("\\.")[1]);
    }
  }

  @Override
  public String getName() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, month);
    cal.getTime();
    cal.set(Calendar.YEAR, year);

    return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + cal.get(Calendar.YEAR);
  }

  @Override
  protected boolean continueAddingAWeek(Calendar cal) {
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    if (cal.get(Calendar.MONTH) == month) {
      return true;
    }

    while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
      cal.add(Calendar.DATE, -1);
      if (cal.get(Calendar.MONTH) == month) {
        return true;
      }
    }

    return false;
  }

  @Override
  public String next() {
    if (month == 11) {
      return "0." + (year + 1);
    } else {
      return (month + 1) + "." + year;
    }

  }

  @Override
  public String previous() {
    if (month == 0) {
      return "11." + (year - 1);
    } else {
      return (month - 1) + "." + year;
    }
  }
}
