package at.trainingstool.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Training {
  private int id;
  private String type;
  private String description;
  private Date scheduleDate;

}
