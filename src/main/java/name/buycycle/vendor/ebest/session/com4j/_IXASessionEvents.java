package name.buycycle.vendor.ebest.session.com4j;

import com4j.DISPID;
import com4j.IID;

/**
 * _IXASessionEvents Interface
 */
@IID("{6D45238D-A5EB-4413-907A-9EA14D046FE5}")
public abstract class _IXASessionEvents {
  // Methods:
  /**
   * <p>
   * method OnLogIn
   * </p>
   * @param szCode Mandatory java.lang.String parameter.
   * @param szMsg Mandatory java.lang.String parameter.
   */

  @DISPID(1)
  public void login(
    String szCode,
    String szMsg) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * method OnLogOut
   * </p>
   */

  @DISPID(2)
  public void logout() {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * method OnDisConnect
   * </p>
   */

  @DISPID(3)
  public void disconnect() {
        throw new UnsupportedOperationException();
  }


  // Properties:
}
