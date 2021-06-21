package name.buycycle.service.ebest.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.manage.XASessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 이베스트 세션 확인
 * @author : ijyoon
 * @date : 2021/03/24
 */
@Component
public class XASessionChecker implements HandlerInterceptor, HandshakeInterceptor {

    private Logger logger = LoggerFactory.getLogger(XASessionChecker.class);

    private XASessionManager xaSessionManager = XASessionManager.getInstance();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(xaSessionManager.isSucceedLogin())
            return true;

        Response xaConResponse = xaSessionManager.login();
        if(xaSessionManager.isSucceedLogin()){
            return true;
        }else{
            if(xaConResponse == null) {
                xaConResponse = new Response(null);
                xaConResponse.putHeader("buyCycleErrMsg", "login response is null.");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(xaConResponse));
            return false;
        }
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {

        if(xaSessionManager.isSucceedLogin())
            return true;

        Response xaConResponse = xaSessionManager.login();
        if(xaSessionManager.isSucceedLogin()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    }
}
