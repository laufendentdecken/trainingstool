package at.trainingstool.tools.period;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import at.trainingstool.Application;
import at.trainingstool.dao.TrainingDao;
import at.trainingstool.model.Training;
import at.trainingstool.model.User;
import at.trainingstool.model.Week;
import at.trainingstool.model.Weekday;

public abstract class AbstractPeriod {

  protected User user;

  protected int day;
  protected int month;
  protected int year;

  public abstract String getName();

  public List<Week> getValues() {
    Calendar cal = setDate();
    List<Week> period = new ArrayList<>();

    while (continueAddingAWeek(cal)) {
      List<Weekday> weekdays = new ArrayList<>();

      for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
        cal.set(Calendar.DAY_OF_WEEK, i);

        Weekday weekday = createWeekDay(cal);

        weekdays.add(weekday);
        Collections.sort(weekdays);
      }

      cal.add(Calendar.DATE, 7);
      period.add(new Week(weekdays));
    }

    if (period.size() > 0 && period.get(0).getWeekdays().size() > 0) {
      Date from = period.get(0).getWeekdays().get(0).getDate();
      Date to = period.get(period.size() - 1).getWeekdays().get(6).getDate();
      AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
      TrainingDao trainingsDAO = ctx.getBean(TrainingDao.class);

      List<Training> trainings = trainingsDAO.getTrainings(user, from, to);

      period = addTrainings(period, trainings);
    }
    return period;
  }

  private List<Week> addTrainings(List<Week> period, List<Training> trainings) {
    for (int j = 0; j < period.size(); j++) {
      for (int i = 0; i < period.get(j).getWeekdays().size(); i++) {
        for (Training training : trainings) {
          Calendar weekdayCal = Calendar.getInstance();
          weekdayCal.setTime(period.get(j).getWeekdays().get(i).getDate());

          Calendar scheduleDateCal = Calendar.getInstance();
          scheduleDateCal.setTime(training.getScheduleDate());

          if (weekdayCal.get(Calendar.DAY_OF_MONTH) == scheduleDateCal.get(Calendar.DAY_OF_MONTH) && weekdayCal.get(Calendar.MONTH) == scheduleDateCal.get(Calendar.MONTH) && weekdayCal.get(Calendar.YEAR) == scheduleDateCal.get(Calendar.YEAR)) {
            period.get(j).getWeekdays().get(i).setDescription(training.getDescription());
            period.get(j).getWeekdays().get(i).setType(training.getType());
            period.get(j).getWeekdays().get(i).setHasEvent(true);
          }
        }
      }
    }
    return period;

  }

  private Weekday createWeekDay(Calendar cal) {
    Weekday weekday = new Weekday();
    weekday.setDate(cal.getTime());
    weekday.setOtherMonth(month != cal.get(Calendar.MONTH));
    return weekday;
  }

  private Calendar setDate() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, month);
    cal.getTime();
    cal.set(Calendar.YEAR, year);
    cal.getTime();
    cal.set(Calendar.DAY_OF_MONTH, day);
    cal.getTime();
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    return cal;
  }

  protected abstract boolean continueAddingAWeek(Calendar cal);

  public abstract String next();

  public abstract String previous();

}
