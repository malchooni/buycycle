package name.buycycle.vendor.ebest.handler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.buycycle.vendor.ebest.config.vo.EBestConfig;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.invoke.XARealSubscribeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class XARealWebSocketHandler extends TextWebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(XARealWebSocketHandler.class);
    private ObjectMapper objectMapper;

    @Autowired
    private EBestConfig eBestConfig;

    {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("ConnectionEstablished : " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("ConnectionClosed : " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String requestJsonMessage = message.getPayload();
        Request request = objectMapper.readValue(requestJsonMessage, Request.class);

        new XARealSubscribeHelper(eBestConfig, session, request).start();
    }
}
