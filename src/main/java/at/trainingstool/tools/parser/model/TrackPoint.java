package at.trainingstool.tools.parser.model;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TrackPoint {

  private BigDecimal latitude;
  private BigDecimal longitude;
  private BigDecimal altitude;
  private DateTime time;

}
