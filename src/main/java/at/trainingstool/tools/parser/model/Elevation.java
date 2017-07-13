package at.trainingstool.tools.parser.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Elevation {

  private BigDecimal pos = new BigDecimal(0);;
  private BigDecimal min = new BigDecimal(0);;
}
