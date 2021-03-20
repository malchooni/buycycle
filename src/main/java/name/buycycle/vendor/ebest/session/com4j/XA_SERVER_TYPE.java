package name.buycycle.vendor.ebest.session.com4j;

import com4j.ComEnum;

/**
 */
public enum XA_SERVER_TYPE implements ComEnum {
  /**
   * <p>
   * The value of this constant is -1
   * </p>
   */
  XA_NOSELECTED_SERVER(-1),
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  XA_REAL_SERVER(0),
  /**
   * <p>
   * The value of this constant is 1
   * </p>
   */
  XA_SIMUL_SERVER(1),
  ;

  private final int value;
  XA_SERVER_TYPE(int value) { this.value=value; }
  public int comEnumValue() { return value; }
}
