package at.trainingstool.tools.parser.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Duration extends BigDecimal {
  private static final long serialVersionUID = -427562708642656023L;

  public Duration(int val) {
    super(val);
  }

  public Duration(double val) {
    super(val);
  }

  public Duration(BigDecimal val) {
    super(val.toString());
  }

  public String print() {
    long hours = (long) this.doubleValue() / 3600;
    long minutes = (long) (this.doubleValue() % 3600) / 60;
    long seconds = (long) this.doubleValue() % 60;

    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }

  public Duration setScale(int newScale, RoundingMode roundingMode) {
    return new Duration(super.setScale(newScale, roundingMode));
  }

  public Duration add(BigDecimal augend) {
    return new Duration(super.add(augend));
  }

}
