package name.buycycle.vendor.ebest.event.handler;

import name.buycycle.vendor.ebest.event.com4j._IXAQueryEvents;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.manage.XASessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xa query 이벤트 핸들러
 *
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class XAQueryEventHandler extends _IXAQueryEvents {

  private Logger logger = LoggerFactory.getLogger(XAQueryEventHandler.class);
  private Response response;

  private static final String SZ_TR_CODE = "szTrCode";

  public XAQueryEventHandler(String uuid) {
    this.response = new Response(uuid);
  }

  public Response getResponse() {
    return response;
  }

  /**
   * 수신 데이터
   *
   * @param szTrCode Mandatory java.lang.String parameter.
   */
  @Override
  public void receiveData(String szTrCode) {
    if (logger.isInfoEnabled()) {
      logger.info("receiveData szTrCode : {}", szTrCode);
    }

    this.response.putHeader(SZ_TR_CODE, szTrCode);
    XASessionManager.getInstance().touch();
    synchronized (this) {
      this.notifyAll();
    }
  }

  /**
   * 수신 메시지
   *
   * @param bIsSystemError Mandatory java.lang.String parameter.
   * @param nMessageCode   Mandatory java.lang.String parameter.
   * @param szMessage      Mandatory java.lang.String parameter.
   */
  @Override
  public void receiveMessage(String bIsSystemError, String nMessageCode, String szMessage) {
    if (logger.isInfoEnabled()) {
      logger.info("receiveMessage bIsSystemError : {}, nMessageCode : {}, szMessage: {}",
          bIsSystemError, nMessageCode, szMessage);
    }

    this.response.putHeader("bIsSystemError", bIsSystemError);
    this.response.putHeader("nMessageCode", nMessageCode);
    this.response.putHeader("szMessage", szMessage);

    if (!bIsSystemError.equals("0")) {
      synchronized (this) {
        this.notifyAll();
      }
    }
  }

  @Override
  public void receiveChartRealData(String szTrCode) {
    if (logger.isInfoEnabled()) {
      logger.info("receiveChartRealData szTrCode : {}", szTrCode);
    }

    this.response.putHeader(SZ_TR_CODE, szTrCode);
    synchronized (this) {
      this.notifyAll();
    }
  }

  @Override
  public void receiveSearchRealData(String szTrCode) {
    if (logger.isInfoEnabled()) {
      logger.info("receiveSearchRealData szTrCode : {}", szTrCode);
    }

    this.response.putHeader(SZ_TR_CODE, szTrCode);
    synchronized (this) {
      this.notifyAll();
    }
  }
}
