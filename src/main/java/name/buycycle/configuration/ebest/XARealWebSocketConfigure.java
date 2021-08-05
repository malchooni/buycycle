package name.buycycle.configuration.ebest;

import name.buycycle.control.XARealWebSocketHandler;
import name.buycycle.control.XASessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 실시간 요청 응답 웹소켓 환경설정
 */
@Configuration
@EnableWebSocket
public class XARealWebSocketConfigure implements WebSocketConfigurer {

    @Autowired
    private XASessionChecker xaSessionChecker;

    @Autowired
    private XARealWebSocketHandler xaRealWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(xaRealWebSocketHandler, "/ebest/realtime")
                .addInterceptors(xaSessionChecker)
                .setAllowedOrigins("*");
    }
}
