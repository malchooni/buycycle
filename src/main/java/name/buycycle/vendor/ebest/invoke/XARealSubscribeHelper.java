package name.buycycle.vendor.ebest.invoke;

import com.fasterxml.jackson.databind.ObjectMapper;
import com4j.EventCookie;
import name.buycycle.vendor.ebest.config.vo.EBestConfig;
import name.buycycle.vendor.ebest.event.com4j.IXAReal;
import name.buycycle.vendor.ebest.event.com4j._IXARealEvents;
import name.buycycle.vendor.ebest.event.handler.XARealEventHandler;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.event.vo.req.RequestBody;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.event.xaobject.XAObjectHelper;
import name.buycycle.vendor.ebest.event.xaobject.vo.XAObject;
import name.buycycle.vendor.ebest.message.MessageHelper;
import name.buycycle.vendor.ebest.message.ResFileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * xa real 이벤트 수신 스레드
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class XARealSubscribeHelper extends Thread {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private MessageHelper messageHelper = MessageHelper.getInstance();

    private EBestConfig eBestConfig;
    private WebSocketSession webSocketSession;
    private Request request;
    private boolean running = true;
    private ObjectMapper objectMapper;

    public XARealSubscribeHelper(EBestConfig eBestConfig, WebSocketSession webSocketSession, Request request) {
        this.eBestConfig = eBestConfig;
        this.webSocketSession = webSocketSession;
        this.request = request;
        this.objectMapper = new ObjectMapper();
        this.setDaemon(true);
    }

    @Override
    public void run() {

        RequestBody requestBody = request.getBody();
        XAObject xaObject = null;
        IXAReal ixaReal = null;
        Response response;

        try{
            ResFileData resFileData = messageHelper.getResFileData(ResFileData.REAL, requestBody.getTrName());
            XARealEventHandler xaRealEventHandler = new XARealEventHandler(request.getHeader().getUuid());
            xaObject  = XAObjectHelper.createXAObject(eBestConfig.getResRootPath(), resFileData, _IXARealEvents.class, xaRealEventHandler);
            ixaReal = xaObject.getIxaType();

            XAObjectHelper.readyForRequest(ixaReal, resFileData, requestBody.getQuery());
            ixaReal.adviseRealData();

            while(isRunning()){
                synchronized (xaRealEventHandler){
                    xaRealEventHandler.wait(eBestConfig.getConnect().getRequestReadTimeOut());
                }

                response = xaRealEventHandler.getResponse();
                if(response == null) continue;

                setResponseData(ixaReal, resFileData.getResponseColumnMap(), response);

                String responseStr = objectMapper.writeValueAsString(response);
                if(logger.isDebugEnabled())
                    logger.debug(" <= response message \n---\n{}\n---", responseStr);

                webSocketSession.sendMessage(new TextMessage(responseStr));
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            if(ixaReal != null)
                ixaReal.unadviseRealData();

            if(xaObject != null){
                EventCookie eventCookie = xaObject.getEventCookie();
                if(eventCookie != null)
                    eventCookie.close();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * 수신 ixaReal -> value object
     * @param ixaReal
     * @param resSchemaMap
     * @param response
     */
    private void setResponseData(IXAReal ixaReal, Map<String, List<String>> resSchemaMap, Response response){
        Set<String> blocks = resSchemaMap.keySet();

        for(String blockName : blocks){
            List<String> columns = resSchemaMap.get(blockName);
            Map<String,String> row = new HashMap<>();

            for(String columnName : columns){
                row.put(columnName, ixaReal.getFieldData(blockName, columnName));
            }
            response.putBody(blockName, row);
        }
    }
}
