package name.buycycle.vendor.ebest.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.session.XASession;
import name.buycycle.vendor.ebest.config.vo.EBestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class XASessionChecker implements HandlerInterceptor, HandshakeInterceptor {

    /**
     * xaSession 연결 상태를 확인한다.
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */

    @Autowired
    private EBestConfig eBestConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        XASession xaSession = XASession.getInstance();
        if(xaSession.isConnected())
            return true;

        Response xaConResponse = null;
        try {
            xaConResponse = xaSession.loginRequest(eBestConfig);
            if(xaConResponse.getHeader("szCode").equals("0000"))
                return true;
        } catch (Exception e) {
            if(xaConResponse == null)
                xaConResponse = new Response(null);
            xaConResponse.putHeader("buyCycleErrMsg", e.getMessage());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(xaConResponse));
        xaSession.close();
        return false;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        XASession xaSession = XASession.getInstance();
        if(xaSession.isConnected())
            return true;

        Response xaConResponse = null;
        try {
            xaConResponse = xaSession.loginRequest(eBestConfig);
            if(xaConResponse.getHeader("szCode").equals("0000"))
                return true;
        } catch (Exception e) {
            if(xaConResponse == null)
                xaConResponse = new Response(null);
            xaConResponse.putHeader("buyCycleErrMsg", e.getMessage());
        }

//        ObjectMapper objectMapper = new ObjectMapper();

        xaSession.close();
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
