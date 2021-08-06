package name.buycycle.control;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.manage.XARealSubscribeManager;
import org.bouncycastle.cert.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 웹소켓 json 메시지 요청, 응답
 */
@Component
public class XARealWebSocketHandler extends TextWebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(XARealWebSocketHandler.class);
    private XARealSubscribeManager xaRealSubscribeManager = XARealSubscribeManager.getInstance();

    private Map<String, List<Request>> sessionRequestMap;

    private ObjectMapper objectMapper;

    {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        sessionRequestMap = new ConcurrentHashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if(logger.isInfoEnabled())
            logger.info("ConnectionEstablished : {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if(logger.isInfoEnabled())
            logger.info("ConnectionClosed : {}", session.getId());
    }

    /**
     * 요청 받은 후 수신 이벤트 응답 스레드 생성
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String requestJsonMessage = message.getPayload();

        if(logger.isDebugEnabled())
            logger.debug(" => request message \n---\n{}\n---", requestJsonMessage);

        Request request = objectMapper.readValue(requestJsonMessage, Request.class);
        String requestTrName = request.getBody().getTrName();
        String requestQuery = request.getBody().getQuery().get(0);

        if (this.checkAlreadyRequest(session, requestTrName, requestQuery)){
            if(logger.isInfoEnabled())
                logger.info(
                        "already requested : [{}] [{}] [{}]",
                        session.getId(),
                        request.getBody().getTrName(),
                        request.getBody().getQuery().get(0)
                );
            return;
        } else {
            List<Request> requestList = sessionRequestMap.get(session.getId());
            if( requestList == null ){
                requestList = new ArrayList<>();
                sessionRequestMap.put(session.getId(), requestList);
            }
            requestList.add(request);
        }

        xaRealSubscribeManager.realTrRequest(response -> {
            String responseStr = objectMapper.writeValueAsString(response);
            if(logger.isDebugEnabled())
                logger.debug(" <= response message \n---\n{}\n---", responseStr);

            if(session.isOpen()){
                session.sendMessage(new TextMessage(responseStr));
            }else{
                session.close();
            }


        }, request);
    }

    private boolean checkAlreadyRequest(WebSocketSession session, String requestTrName, String requestQuery){
        List<Request> requestList = sessionRequestMap.get(session.getId());
        if(requestList == null || requestList.size() < 1){
            return false;
        }

        for(Request requestItem : requestList){
            if(requestTrName.equals(requestItem.getBody().getTrName()) && requestItem.getBody().getQuery().containsValue(requestQuery)){
                return true;
            }
        }
        return false;
    }
}
