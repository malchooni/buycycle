package name.buycycle.vendor.ebest.event;

import name.buycycle.vendor.ebest.event.vo.res.Response;

import java.io.IOException;

public interface XARealResponseEvent {

    void responseEvent(Response response) throws IOException;
}
