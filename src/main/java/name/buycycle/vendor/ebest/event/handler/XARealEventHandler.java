package name.buycycle.vendor.ebest.event.handler;

import name.buycycle.vendor.ebest.event.com4j._IXARealEvents;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.manage.XASessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xa real 이벤트 핸들러
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class XARealEventHandler extends _IXARealEvents {

    private final Logger logger = LoggerFactory.getLogger(XARealEventHandler.class);
    private Response response;

    private final Object monitor;
    private final String requestUUID;

    public XARealEventHandler(Object monitor, String uuid) {
        this.monitor = monitor;
        this.requestUUID = uuid;
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public void receiveRealData(String szTrCode) {

        if(logger.isDebugEnabled())
            logger.debug("RequestUUID : {}, szTrCode : {}", this.requestUUID, szTrCode);

        this.response = new Response(this.requestUUID);
        this.response.putHeader("szTrCode", szTrCode);

        XASessionManager.getInstance().touch();

        synchronized (this.monitor){
            this.monitor.notifyAll();
        }
    }

    @Override
    public void recieveLinkData(String szLinkName, String szData, String szFiller) {

        if(logger.isDebugEnabled())
            logger.debug("RequestUUID : {}, szLinkName : {}, szData : {}, szFiller : {}", this.requestUUID, szLinkName, szData, szFiller);

        this.response = new Response(this.requestUUID);
        this.response.putHeader("szLinkName", szLinkName);
        this.response.putHeader("szData", szData);
        this.response.putHeader("szFiller", szFiller);
        synchronized (this.monitor){
            this.monitor.notifyAll();
        }
    }
}
