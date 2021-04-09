package name.buycycle.vendor.ebest.invoke;

import com.fasterxml.jackson.databind.ObjectMapper;
import com4j.COM4J;
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
import name.buycycle.vendor.ebest.manage.command.XARealSubscribeCommand;
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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * xa real 이벤트 수신 스레드
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class XARealSubscribeHelper extends Thread {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private MessageHelper messageHelper = MessageHelper.getInstance();

    private boolean running = true;

    private WebSocketSession webSocketSession;
    private EBestConfig eBestConfig;
    private Request request;

    private ObjectMapper objectMapper;
    private XARealEventHandler xaRealEventHandler;

    private static final AtomicInteger atomicInteger = new AtomicInteger();

    public XARealSubscribeHelper(XARealSubscribeCommand command) {
        super("XARealSubscribeHelper-" + atomicInteger.incrementAndGet());
        this.webSocketSession = command.getSession();
        this.eBestConfig = command.getEBestConfig();
        this.request = command.getRequest();
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
            xaRealEventHandler = new XARealEventHandler(request.getHeader().getUuid());
            xaObject  = XAObjectHelper.createXAObject(eBestConfig.getResRootPath(), resFileData, _IXARealEvents.class, xaRealEventHandler);
            ixaReal = xaObject.getIxaType();

            XAObjectHelper.readyForRequest(ixaReal, resFileData, requestBody.getQuery());
            ixaReal.adviseRealData();

            while(isRunning()){
                synchronized (xaRealEventHandler){
                    try{
                        xaRealEventHandler.wait(eBestConfig.getConnect().getRequestReadTimeOut());
                    }catch (InterruptedException ie){
                        continue;
                    }
                }

                response = xaRealEventHandler.getResponse();
                if(response == null) continue;

                setResponseData(ixaReal, resFileData.getResponseColumnMap(), response);

                String responseStr = objectMapper.writeValueAsString(response);
                if(logger.isDebugEnabled())
                    logger.debug(" <= response message \n---\n{}\n---", responseStr);

                webSocketSession.sendMessage(new TextMessage(responseStr));
            }

            logger.info("XARealSubscribeHelper process end.");
        }catch (Exception e){
            if(logger.isErrorEnabled())
                logger.error(e.getMessage(), e);
        }finally {
            if(ixaReal != null)
                ixaReal.unadviseRealData();

            if(xaObject != null){
                EventCookie eventCookie = xaObject.getEventCookie();
                if(eventCookie != null)
                    eventCookie.close();

                COM4J.cleanUp();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void shutdown(){
        this.running = false;
        synchronized (xaRealEventHandler){
            this.xaRealEventHandler.notify();
        }
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
