package at.trainingstool.tools.parser.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = { "points" })
public class Lap {
  private List<TrackPoint> points = new ArrayList<>();

  private Duration duration = new Duration(0);
  private Elevation elevation = new Elevation();
  private BigDecimal distance = new BigDecimal(0);
}
