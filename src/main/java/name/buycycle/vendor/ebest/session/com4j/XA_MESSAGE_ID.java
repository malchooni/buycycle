package name.buycycle.vendor.ebest.session.com4j;

import com4j.ComEnum;

/**
 *
 */
public enum XA_MESSAGE_ID implements ComEnum {
  /**
   * <p>
   * The value of this constant is -1
   * </p>
   */
  XA_FAILED(-1),
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  XA_SUCCESS(0),
  ;

  private final int value;

  XA_MESSAGE_ID(int value) {
    this.value = value;
  }

  public int comEnumValue() {
    return value;
  }
}
