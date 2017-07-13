package at.trainingstool.model;

import java.util.Calendar;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Weekday implements Comparable<Weekday> {

  private Date date;
  private boolean otherMonth;

  private String type;
  private String description;
  private boolean hasEvent;

  @Override
  public int compareTo(Weekday o) {
    return o.getDate().compareTo(date) * -1;
  }

  public void setDate(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MINUTE, 0);

    this.date = cal.getTime();
  }

  public String getDay() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
  }

}
