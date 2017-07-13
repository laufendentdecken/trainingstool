package at.trainingstool.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Week {

  private List<Weekday> weekdays;
}
