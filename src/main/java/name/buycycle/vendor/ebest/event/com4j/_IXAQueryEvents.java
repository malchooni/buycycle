package name.buycycle.vendor.ebest.event.com4j;

import com4j.DISPID;
import com4j.IID;

/**
 * _IXAQueryEvents Interface
 */
@IID("{AAF89E20-1F84-4B1F-B6EE-617B6F2C9CD4}")
public abstract class _IXAQueryEvents {
  // Methods:
  /**
   * <p>
   * method ReceiveData
   * </p>
   * @param szTrCode Mandatory java.lang.String parameter.
   */

  @DISPID(1)
  public void receiveData(
    String szTrCode) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * method ReceiveMessage
   * </p>
   * @param bIsSystemError Mandatory java.lang.String parameter.
   * @param nMessageCode Mandatory java.lang.String parameter.
   * @param szMessage Mandatory java.lang.String parameter.
   */

  @DISPID(2)
  public void receiveMessage(
    String bIsSystemError,
    String nMessageCode,
    String szMessage) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * method ReceiveChartRealData
   * </p>
   * @param szTrCode Mandatory java.lang.String parameter.
   */

  @DISPID(3)
  public void receiveChartRealData(
    String szTrCode) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * method ReceiveSearchRealData
   * </p>
   * @param szTrCode Mandatory java.lang.String parameter.
   */

  @DISPID(4)
  public void receiveSearchRealData(
    String szTrCode) {
        throw new UnsupportedOperationException();
  }


  // Properties:
}
