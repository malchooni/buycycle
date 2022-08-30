package name.buycycle.vendor.ebest.event;

import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.event.vo.res.Response;

import java.io.IOException;

public interface XARealResponseEvent {

  void responseEvent(Request receiveRequest, Response response) throws IOException;
}
