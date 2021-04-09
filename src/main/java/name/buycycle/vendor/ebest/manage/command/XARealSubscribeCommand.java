package name.buycycle.vendor.ebest.manage.command;

import name.buycycle.vendor.ebest.config.vo.EBestConfig;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import org.springframework.web.socket.WebSocketSession;

/**
 * XARealSubscribeManager 제어를 위한 명령어 오브젝트
 */
public class XARealSubscribeCommand {

    private String command;
    private WebSocketSession session;
    private EBestConfig eBestConfig;
    private Request request;

    public XARealSubscribeCommand(String command) {
        this.command = command;
    }

    public Request getRequest() {
        return request;
    }

    public XARealSubscribeCommand setRequest(Request request) {
        this.request = request;
        return this;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public XARealSubscribeCommand setSession(WebSocketSession session) {
        this.session = session;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public EBestConfig getEBestConfig() {
        return eBestConfig;
    }

    public XARealSubscribeCommand setEBestConfig(EBestConfig eBestConfig) {
        this.eBestConfig = eBestConfig;
        return this;
    }

    @Override
    public String toString() {
        return this.command;
    }
}
