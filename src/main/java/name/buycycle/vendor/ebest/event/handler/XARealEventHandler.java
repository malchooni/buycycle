package name.buycycle.vendor.ebest.event.handler;

import name.buycycle.vendor.ebest.event.com4j._IXARealEvents;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XARealEventHandler extends _IXARealEvents {

    private Logger logger = LoggerFactory.getLogger(XARealEventHandler.class);
    private Response response;

    private String requestUUID;

    public XARealEventHandler(String uuid) {
        this.requestUUID = uuid;
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public void receiveRealData(String szTrCode) {
        logger.info(this.requestUUID + " szTrCode : " + szTrCode);
        this.response = new Response(this.requestUUID);
        this.response.putHeader("szTrCode", szTrCode);
        synchronized (this){
            this.notify();
        }
    }

    @Override
    public void recieveLinkData(String szLinkName, String szData, String szFiller) {
        logger.info(this.requestUUID + " szLinkName : " + szLinkName + " szData : " + szData + " szFiller : " + szFiller );
        this.response = new Response(this.requestUUID);
        this.response.putHeader("szLinkName", szLinkName);
        this.response.putHeader("szData", szData);
        this.response.putHeader("szFiller", szFiller);
        synchronized (this){
            this.notify();
        }
    }
}
