package name.buycycle.vendor.ebest.event.xaobject.vo;

import com4j.EventCookie;
import name.buycycle.vendor.ebest.event.IXAType;

/**
 * xing 인터페이스 객체
 *
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class XAObject {

  private IXAType ixaType;
  private EventCookie eventCookie;

  public <T extends IXAType> T getIxaType() {
    return (T) ixaType;
  }

  public void setIxaType(IXAType ixaType) {
    this.ixaType = ixaType;
  }

  public EventCookie getEventCookie() {
    return eventCookie;
  }

  public void setEventCookie(EventCookie eventCookie) {
    this.eventCookie = eventCookie;
  }
}
