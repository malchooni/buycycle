package name.buycycle.control;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.manage.XARealSubscribeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Iterator;
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

    private Map<Request, List<WebSocketSession>> requestSessionMap;

    private ObjectMapper objectMapper;

    {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        requestSessionMap = new ConcurrentHashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if(logger.isInfoEnabled())
            logger.info("ConnectionEstablished : {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        String targetSessionId = session.getId();
        Iterator<Request> requestIterator = requestSessionMap.keySet().iterator();
        while(requestIterator.hasNext()){
            Request key = requestIterator.next();

            List<WebSocketSession> sessionList = requestSessionMap.get(key);
            for(int i = 0; i < sessionList.size(); i++){
                WebSocketSession getSession = sessionList.get(i);
                if(targetSessionId.equals(getSession.getId())){
                    sessionList.remove(i);
                    break;
                }
            }

            if(sessionList.size() == 0){
                xaRealSubscribeManager.realTrStop(key);
                requestIterator.remove();
            }
        }

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

        if (this.checkAlreadyRequest(session, request)){
            if(logger.isInfoEnabled())
                logger.info(
                        "already requested : [{}] [{}] [{}]",
                        session.getId(),
                        request.getBody().getTrName(),
                        request.getBody().getQuery().get(0)
                );
            return;
        } else {
            List<WebSocketSession> sessionList = requestSessionMap.get(request);
            if( sessionList == null ){
                sessionList = new ArrayList<>();
                requestSessionMap.put(request, sessionList);
            }
            sessionList.add(session);
        }

        xaRealSubscribeManager.realTrRequest((receiveRequest, response) -> {
            List<WebSocketSession> sessionList = requestSessionMap.get(receiveRequest);
            if(sessionList != null && sessionList.size() > 0 ){
                String responseStr = objectMapper.writeValueAsString(response);
                for(WebSocketSession responseSession : sessionList){
                    if(logger.isDebugEnabled())
                        logger.debug(" <= response message \n---\n{}\n---", responseStr);

                    if(responseSession.isOpen())
                        responseSession.sendMessage(new TextMessage(responseStr));
                    else
                        responseSession.close();
                }
            }
        }, request);
    }

    /**
     * 이미 요청된 tr 인지 확인
     * @param searchSession
     * @param request
     * @return
     */
    private boolean checkAlreadyRequest(WebSocketSession searchSession, Request request){
        String searchSessionId = searchSession.getId();
        List<WebSocketSession> sessionIdList = requestSessionMap.get(request);
        for(WebSocketSession session : sessionIdList){
            if(searchSessionId.equals(session.getId()))
                return true;
        }
        return false;
    }
}
